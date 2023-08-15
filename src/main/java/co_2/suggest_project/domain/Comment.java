package co_2.suggest_project.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Table(name = "comments")
public class Comment extends BaseTimeEntity { //댓글 entity
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; // 작성자

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @Lob
  @Column(columnDefinition = "Text")
  private String content;

  private boolean isRemoved = false;

  //== 연관관계 편의 메서드 ==//
  public void confirmWriter(Member member) {
    this.member = member;
    member.addComment(this);
  }

  public void confirmPost(Post post) {
    this.post = post;
    post.addComment(this);
  }

  //== 수정 ==//
  public void updateContent(String content) {
    this.content = content;
  }
  //== 삭제 ==//
  public void remove() {
    this.isRemoved = true;
  }

  @Builder
  public Comment( Member member, Post post, String content) {
    this.member = member;
    this.post = post;
    this.content = content;
    this.isRemoved = false;
  }
}
