package co_2.suggest_project.Model;


import co_2.suggest_project.Entity.UserEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {

    private Long userId;
    private String userName;
    private String password;
    private String email;
    private String role;

    public static UserDTO fromEntity(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setRole(userEntity.getRole());
        return userDTO;
    }

}

