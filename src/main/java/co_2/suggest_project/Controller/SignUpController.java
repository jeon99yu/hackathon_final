package co_2.suggest_project.Controller;

import co_2.suggest_project.Entity.UserEntity;
import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Repository.UserRepository;
import co_2.suggest_project.Service.UserService;
import co_2.suggest_project.Utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class SignUpController {

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public SignUpController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody HashMap<String, Object> map) throws Exception {
        String nickName = map.get("nickName").toString();
        String email = map.get("email").toString();
        String password = map.get("password").toString();
        String role = map.get("role").toString();
        String inputCode = map.get("code").toString();

        if (userService.isEmailDuplicated((email))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 이메일입니다.");
        }

        if(!userService.checkVerificationCode(email, inputCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 인증 코드입니다.");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setNickName(nickName);
        userDTO.setPassword(password);
        userDTO.setEmail(email);
        userDTO.setRole(role);

        userService.insertUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/generateCode")
    public ResponseEntity<?> generateCode(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        userService.createVerificationCode(email);
        return ResponseEntity.ok().body("Verification code has been sent to " + email);
    }

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        // 1. 이메일로 사용자 찾기
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("존재하지 않는 이메일입니다.");
        }
        // 2. 비밀번호 일치 여부 확인
        UserEntity user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 틀렸습니다.");
        }
        String token = jwtUtil.createToken(email);
        return ResponseEntity.ok().body("로그인 완료. 토큰: "+token);
    }


}
