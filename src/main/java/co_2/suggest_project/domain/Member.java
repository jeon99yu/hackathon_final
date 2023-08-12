package co_2.suggest_project.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@Table(name = "members")
public class Member { //회원 엔티티
  @Id @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  @Column(length = 100)
  private String nickname;

  @Column(length = 100)
  private String password;

  @Column(length = 100)
  private String email;

  @Column(length = 100)
  private String role;


  //== 회원탈퇴 -> 작성한 게시물, 댓글 모두 삭제 ==//
  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts = new ArrayList<>();

  @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  // 게시물 추가,삭제 메소드
  public void addPost(Post post) {
    posts.add(post);
    post.setMember(this);
  }

  public void removePost(Post post) {
    posts.remove(post);
    post.setMember(null);
  }

  // 댓글 추가, 삭제 메소드
  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setMember(this);
  }

  public void removeComment(Comment comment) {
    comments.remove(comment);
    comment.setMember(null);
  }

  //== 정보 수정 ==//
  public void updatePassword(PasswordEncoder passwordEncoder, String password){
    this.password = passwordEncoder.encode(password);
  }

  public void updateNickName(String nickname){
    this.nickname = nickname;
  }

  //토큰 업데이트 관련
//  public void updateRefreshToken(String refreshToken){
//    this.refreshToken = refreshToken;
//  }
//  public void destroyRefreshToken(){
//    this.refreshToken = null;
//  }



  //== 패스워드 암호화 ==//
  public void encodePassword(PasswordEncoder passwordEncoder){
    this.password = passwordEncoder.encode(password);
  }
}
