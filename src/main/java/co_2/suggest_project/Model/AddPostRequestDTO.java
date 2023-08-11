package co_2.suggest_project.Model;

import co_2.suggest_project.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddPostRequestDTO {
  private String title;
  private String content;

  public Post toEntity() {
    return Post.builder()
        .title(title)
        .content(content)
        .build();
  }
}
