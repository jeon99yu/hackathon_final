package co_2.suggest_project.Controller;

import co_2.suggest_project.Model.AddPostRequestDTO;
import co_2.suggest_project.Model.PostResponseDTO;
import co_2.suggest_project.Service.PostService;
import co_2.suggest_project.domain.Post;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class PostApiController {

  private final PostService postService;

  @PostMapping("/api/posts")
  public ResponseEntity<Post> addPost(@RequestBody AddPostRequestDTO request) {
    Post savedPost = postService.save(request);

    // 요청한 자원이 성공적으로 생성되었으며 저장된 게시글 정보를 응답 객체에 담아 전송
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(savedPost);
  }
  @GetMapping("/api/posts")
  public ResponseEntity<List<PostResponseDTO>> findAllPosts() {
    List<PostResponseDTO> posts = postService.findAll()
        .stream()
        .map(PostResponseDTO::new)
        .collect(Collectors.toList());

    return ResponseEntity.ok()
        .body(posts);
  }
  @GetMapping("/api/posts/{id}")
  //URL 경로에서 값 추출
  public ResponseEntity<PostResponseDTO> findPost(@PathVariable long id) {
    Post post = postService.findById(id);

    return ResponseEntity.ok()
        .body(new PostResponseDTO(post));
  }

  @DeleteMapping("/api/posts/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable long id) {
    postService.delete(id);

    return ResponseEntity.ok()
        .build();
  }
}
