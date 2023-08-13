package co_2.suggest_project.Repository;

import co_2.suggest_project.Entity.UserEntity;
import co_2.suggest_project.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository<T> extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(UserEntity user);
}
