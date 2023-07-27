package co_2.suggest_project.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenProvider {
    // JWT 생성 메서드
    public String createToken() {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("your-app-name")
                .withClaim("email", "user-email@example.com")
                .withClaim("auth_code", "123456")
                .sign(algorithm);
        return token;
    }

    // JWT 해독 메서드
    public void decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        DecodedJWT jwt = JWT.require(algorithm)
                .withIssuer("your-app-name")
                .build()
                .verify(token);

        String email = jwt.getClaim("email").asString();
        String authCode = jwt.getClaim("auth_code").asString();

        // 이후 email 및 authCode를 활용하는 로직 작성
    }
}
