package co_2.suggest_project.Controller;

import co_2.suggest_project.Model.UserDTO;
import co_2.suggest_project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public void test(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("성우");
        userDTO.setPassword("1234");
        userDTO.setEmail("123@naver.com");
        userDTO.setRole("회원");
        System.out.println(userDTO);

        userService.insertUser(userDTO);
    }




}
