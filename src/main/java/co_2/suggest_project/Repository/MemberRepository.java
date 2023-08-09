package co_2.suggest_project.Repository;

import co_2.suggest_project.domain.Member;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

  @PersistenceContext
  private EntityManager em;

  public void save(Member member) {
    em.persist(member);
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("select m from Member m", Member.class)
        .getResultList();
  }

  public List<Member> findByName(String nickname) { //닉네임으로 찾기 vs 이메일로 찾기 둘 중 고민이다..
    return em.createQuery("select m from Member m where m.nickname = :nickname",
        Member.class)
        .setParameter("nickname", nickname)
        .getResultList();
  }
}
