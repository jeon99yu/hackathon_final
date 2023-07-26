package co_2.suggest_project.Model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserDTO {

    @Id
    private int userId;
    private String userName;
    private String password;
    private String email;
    private String role;
}
