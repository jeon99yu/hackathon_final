//package co_2.suggest_project.Service;
//
//import co_2.suggest_project.Entity.RefreshTokenEntity;
//import co_2.suggest_project.Entity.UserEntity;
//import co_2.suggest_project.Repository.RefreshTokenRepository;
//import co_2.suggest_project.Repository.UserRepository;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class TokenService {
//
//    @Autowired
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    private final long refreshExpiredMs = 720000000000000L;
//
//    public String createRefreshToken(String email) {
//        Date now = new Date();
//        Date validity = new Date(now.getTime() + refreshExpiredMs);
//
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(now)
//                .setExpiration(validity)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//
//    public String issueRefreshToken(UserEntity user) {
//        // Create refresh token
//        String refreshToken = createRefreshToken(user.getEmail());
//
//        // Save the refresh token in the database
//        refreshTokenRepository.save(new RefreshTokenEntity(user.getUserId(), refreshToken, new Date(System.currentTimeMillis() + refreshExpiredMs)));
//
//        return refreshToken;
//    }
//
//    public String refreshAccessToken(String refreshToken) {
//        // Check if the refresh token is valid
//        RefreshTokenEntity storedToken = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
//
//        // Create a new access token
//        String newAccessToken = ...; // Here goes the logic to create a new access token
//
//        return newAccessToken;
//    }
//
//
//}
