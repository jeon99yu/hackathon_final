package co_2.suggest_project.Repository;

import co_2.suggest_project.Model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDTO, Integer> {
    UserDTO findByUserId(int userId);

}
