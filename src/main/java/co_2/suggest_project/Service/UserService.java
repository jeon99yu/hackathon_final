package co_2.suggest_project.Service;

import co_2.suggest_project.Entity.UserEntity;
import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    @Autowired    //Autowired를 통해 의존성주입 -> UserRepository 주입
    public UserService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void insertUser(UserDTO userDTO) {          //UserDTO -> UserEntity 변환
        UserEntity userEntity = UserEntity.fromDTO(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
    }


//    @Transactional
//    public UserDTO selectUser(int id) {
//        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
//        return UserDTO.fromEntity(userEntity);
//    }

//    @Transactional
//    public List<UserDTO> selectAllUser() {
//        List<UserEntity> userEntities = userRepository.findAll();
//        return userEntities.stream()
//                .map(UserDTO::fromEntity)
//                .collect(Collectors.toList());
//    }

    public boolean isEmailDuplicated(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private Map<String, String> verificationCodes = new HashMap<>();
//
    public void createVerificationCode(String email) {
        String code = emailService.generateVerificationCode();
        emailService.sendVerificationEmail(email,code);
        verificationCodes.put(email,code);
    }

    public boolean checkVerificationCode(String email, String inputCode){
        String correctCode = verificationCodes.get(email);
        return correctCode != null && correctCode.equals(inputCode);
    }
}
