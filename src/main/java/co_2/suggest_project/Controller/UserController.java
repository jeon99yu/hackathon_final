package co_2.suggest_project.Controller;

import co_2.suggest_project.Entity.UserEntity;
import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Repository.UserRepository;
import co_2.suggest_project.Service.UserService;
import co_2.suggest_project.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController

public class UserController {
    private UserRepository userRepository;
    private UserService userService;
    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public UserDTO test(){
        UserDTO userDTO = new UserDTO();
        userDTO.setNickName("서빈");
        userDTO.setPassword("1234");
        userDTO.setEmail("111@naver.com");
        userDTO.setRole("회원");
        System.out.println(userDTO);
        return userDTO;
        //userService.insertUser(userDTO);
    }

    @PostMapping("/test")
    public void testInsertUser() {
        UserDTO userDTO = new UserDTO();
     //   userDTO.setUserId(1L);     UserId는 필요없음.
        userDTO.setNickName("서빈");
        userDTO.setPassword("1234");
        userDTO.setEmail("1111@naver.com");
        userDTO.setRole("회원");
        userService.insertUser(userDTO);
    }

    @PostMapping("/users/password")
    public ResponseEntity<?> updatePassword(@RequestBody UserDTO.ChangePasswordRequest request) {
        boolean result = userService.updatePassword(request.getEmail(), request.getCurrentPassword(), request.getNewPassword(), request.getConfirmNewPassword());
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/users/mypage")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        // JWT 검증
        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 토큰입니다.");
        }
        // JWT에서 사용자 정보 추출
        String email = jwtUtil.getEmailFromToken(token);

        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }
        // 연관된 데이터를 삭제하는 로직(예: 사용자의 게시물, 댓글 등)은 여기에 추가합니다.
        userRepository.delete(userOptional.get()); // 사용자 정보 삭제
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 성공적으로 삭제되었음을 응답합니다.
    }
}
