package co_2.suggest_project.Test;

import co_2.suggest_project.Entity.UserEntity;
import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Repository.UserRepository;
import co_2.suggest_project.Service.EmailService;
import co_2.suggest_project.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
// Mockito의 모든 초기화와 관련된 작업을 수행

public class UserServiceTest {

    @Mock
    // UserRepository, EmailService, PasswordEncoder와 같은 의존성을 모의 객체로 만듬
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    // UserService에 모의 객체를 주입
    private UserService userService;

    @Test
    void testInsertUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test.example.com");
        userDTO.setPassword("password");

        // 비밀번호 암호화 모킹
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // UserEntity 변환 예상
        UserEntity expectedUserEntity = UserEntity.fromDTO(userDTO) ;
        expectedUserEntity.setPassword("encodedPassword"); // 암호화된 비밀번호

        // 메서드 호출
        userService.insertUser(userDTO);

        // 결과 확인 : userRepository.save가 expectedUserEntity로 호출되었는지 검증
        verify(userRepository, times(1)).save(eq(expectedUserEntity));

    }



    @Test
    // changePassword 메서드 테스트
    void testChangePassword_userNotFound() {
        // 이메일이 존재하지 않는 경우를 검증
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertFalse(userService.updatePassword("test@example.com", "oldPassword", "newPassword", "newPassword"));
    }

    @Test
    void testChangePassword_wrongCurrentPassword() {
        // 현재 비밀번호가 일치하지 않는 경우를 검증

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("encodedOldPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertFalse(userService.updatePassword("test@example.com", "wrongOldPassword", "newPassword", "newPassword"));
    }

    @Test
    void testChangePassword_newPasswordsDoNotMatch() {
        // 새로운 비밀번호와 확인용 비밀번호가 일치하지 않는 경우를 검증
        assertFalse(userService.updatePassword("test@example.com", "oldPassword", "newPassword", "differentNewPassword"));
    }

    @Test
    void testChangePassword_success() {
        // 모든 조건이 충족되어 비밀번호가 성공적으로 변경되는 경우를 검증
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("encodedOldPassword");

        // 이메일로 사용자 찾기
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(userEntity));
        //비밀번호 일치 확인
        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(true);
        // 새 비밀번호 암호화
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        // 메서드 호출 및 결과 확인
        assertTrue(userService.updatePassword("test@example.com", "oldPassword", "newPassword", "newPassword"));
        // 저장 검증
        verify(userRepository, times(1)).save(argThat(entity -> "encodedNewPassword".equals(entity.getPassword())));
    }


}

