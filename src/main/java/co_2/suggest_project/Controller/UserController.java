package co_2.suggest_project.Controller;

import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/test")
    public UserDTO test(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("서빈");
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
        userDTO.setUserName("서빈");
        userDTO.setPassword("1234");
        userDTO.setEmail("1111@naver.com");
        userDTO.setRole("회원");
        userService.insertUser(userDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> updatePassword(@RequestBody UserDTO.ChangePasswordRequest request) {
        boolean result = userService.updatePassword(request.getEmail(), request.getCurrentPassword(), request.getNewPassword(), request.getConfirmNewPassword());
        return result ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




}
