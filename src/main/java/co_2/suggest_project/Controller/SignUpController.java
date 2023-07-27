package co_2.suggest_project.Controller;

import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Service.UserService;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SignUpController {

    private UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;

    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody HashMap<String, Object> map) throws Exception {
        String username = map.get("username").toString();
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
        userDTO.setUserName(username);
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

//    @PostMapping("/sendVerificationCode")
//    public ResponseEntity<?> sendVerificationCode(@RequestBody HashMap<String, String> map) throws Exception {
//        String email = map.get("email");
//
//        if (userService.isEmailDuplicated((email))) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 이메일입니다.");
//        }
//
//        userService.createVerificationCode(email);
//
//        return ResponseEntity.status(HttpStatus.OK).body("인증 코드가 발송되었습니다.");
//    }



}
