package co_2.suggest_project.Repository;

import co_2.suggest_project.Entity.UserEntity;
import co_2.suggest_project.Model.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long Id);
    Optional<UserEntity> findByEmail(String email);

}
//

