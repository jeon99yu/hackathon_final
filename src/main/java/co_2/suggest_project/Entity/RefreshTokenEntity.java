//package co_2.suggest_project.Entity;
//
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//public class RefreshTokenEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    private UserEntity user;
//    private String Token;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date expiredAt;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public UserEntity getUser() {
//        return user;
//    }
//
//    public void setUser(UserEntity user) {
//        this.user = user;
//    }
//
//    public String getToken() {
//        return Token;
//    }
//
//    public void setToken(String token) {
//        Token = token;
//    }
//
//    public Date getExpiredAt() {
//        return expiredAt;
//    }
//
//    public void setExpiredAt(Date expiredAt) {
//        this.expiredAt = expiredAt;
//    }
//}
//
