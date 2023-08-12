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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co_2.suggest_project.Entity.UserEntity;
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
public class Post { //게시물 엔티티

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;

  @ManyToOne
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; // 작성자

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Column(length = 200)
  private String title;

  @Column(length = 100)
  private String category;

  @Column(columnDefinition = "Text")
  private String content;

  private Integer hits;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdTime;

  @Temporal(TemporalType.TIMESTAMP)
  private Date modifiedTime;

  @Column(length = 100)
  private String expirationDate;

  private PostStatus status; // 게시글 상태 [ACTIVATED, DISABLED]

  @Builder
  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }
  //게시글 수정
  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setPost(this);
  }

  public void removeComment(Comment comment) {
    comments.remove(comment);
    comment.setPost(null);
  }
}

