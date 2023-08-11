package co_2.suggest_project.Service;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

import co_2.suggest_project.Model.AddPostRequestDTO;
import co_2.suggest_project.Model.UpdatePostRequestDTO;
import co_2.suggest_project.Repository.PostRepository;
import co_2.suggest_project.domain.Post;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;

  public Post save(AddPostRequestDTO requestDTO) {
    return (Post) postRepository.save(requestDTO.toEntity());
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public Post findById(long id) {
    return (Post) postRepository.findById(id).orElse(null);
  }

  //게시글 삭제
  public void delete(long id) {
    postRepository.deleteById(id);
  }
  //게시글 수정
  @Transactional
  public Post update(long id, UpdatePostRequestDTO request) {
//    Post post = postRepository.findById(id);
//  return post;

  }
}
