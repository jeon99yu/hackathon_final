package co_2.suggest_project.Model;

import co_2.suggest_project.domain.Post;
import lombok.Getter;

@Getter
public class PostResponseDTO {

  private String title;
  private String content;

  public PostResponseDTO(Post post) {
    this.title = post.getTitle();
    this.content = post.getContent();
  }
}
