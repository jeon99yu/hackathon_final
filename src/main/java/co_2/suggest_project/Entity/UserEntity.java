package co_2.suggest_project.Entity;

import co_2.suggest_project.Model.UserDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    public static UserEntity fromDTO(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setRole(userDTO.getRole());
        return userEntity;
    }


}
