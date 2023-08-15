package co_2.suggest_project.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Text;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "posts")
public class Post extends BaseTimeEntity { //게시물 엔티티

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; // 작성자

  @Column(length = 200)
  private String title;

  @Column(length = 100)
  private String category;

  @Lob
  @Column(columnDefinition = "Text")
  private String content;

  @Column(nullable = true)
  private String filePath; //파일 경로

  private Integer hits; //조회수

  @Column(length = 100)
  private String expirationDate;

  private PostStatus status; // 게시글 상태 [ACTIVATED, DISABLED]

  //==게시글 삭제하면 달려 있는 댓글 모두 삭제==//
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  //== 연관관계 편의 메서드 ==//
  public void confirmWriter(Member member) {
    this.member = member;
    member.addPost(this);
  }

  @Builder
  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }

  //==수정==//
  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public void updateFilePath(String filePath) {
    this.filePath = filePath;
  }

  //==댓글 관련 메소드==//
  public void addComment(Comment comment) {+6
    comments.add(comment);
    comment.setPost(this);
  }

  public void removeComment(Comment comment) {
    comments.remove(comment);
    comment.setPost(null);
  }
}

