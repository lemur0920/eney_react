package eney.security;

import eney.domain.UserPrincipal;
import eney.domain.UserVO;
import eney.property.AppProperties;
import eney.property.EneyProperties;
import eney.property.UserCertifyProperties;
import eney.domain.UserPrincipal;
import eney.property.AppProperties;
import eney.property.UserCertifyProperties;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private UserCertifyProperties userCertifyProperties;

    @Value("${eney.portal.token.jwtSecret}")
    private String jwtSecret;

    @Value("${eney.portal.token.jwtExpirationInms}")
    private int jwtExpirationInms;


//    @Value("${app.jwtSecret}")
//    private String jwtSecret;
//
//    @Value("${app.jwtExpirationInMs}")
//    private int jwtExpirationInMs;

    @Autowired
    private AppProperties appProperties;

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        System.out.println("===토큰 생성");
        System.out.println(userPrincipal.getUserId());
        System.out.println(userPrincipal);
        System.out.println(jwtExpirationInms);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInms);

        System.out.println(expiryDate);
        System.out.println("===토큰 생성");

        return Jwts.builder()
                .setSubject(userPrincipal.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        System.out.println("토큰 체크");
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

}