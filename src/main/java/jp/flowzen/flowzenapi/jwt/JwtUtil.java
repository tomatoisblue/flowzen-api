package jp.flowzen.flowzenapi.jwt;

import java.util.Date;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.exception.EmailNotFoundException;
import jp.flowzen.flowzenapi.repository.UserRepository;

/**
 * JwtUtil class
 */
@Component
public class JwtUtil {

  private UserRepository userRepository;

  public JwtUtil(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User currentUser(String token) throws Exception {

    DecodedJWT decoded;

    try {
      decoded = JWT.decode(token);
    } catch (JWTDecodeException e) {
      throw new BadCredentialsException("Authorization header token is invalid");
    }

    if (decoded.getToken().isEmpty()) {
      throw new EmailNotFoundException("Authorization header must not be empty");
    }

    Claim username = decoded.getClaim("username");

    if (username.isMissing()) {
      throw new Exception("username claim not found");
    }

    User user = userRepository.findByUsername(username.asString());

    if (user == null) {
      throw new Exception("user not found");
    }
    return user;
  }

  public Long getUserId(String token) {
    DecodedJWT decoded;
    decoded = JWT.decode(token);
    Claim userId = decoded.getClaim("userId");
    return userId.asLong();
  }

  public String getUsername(String token) throws Exception{
    DecodedJWT decoded;

    try {
      decoded = JWT.decode(token);
    } catch (JWTDecodeException e) {
      throw new BadCredentialsException("Authorization header token is invalid");
    }

    if (decoded.getToken().isEmpty()) {
      throw new Exception("Authorization header must not be empty");
    }

    Claim username = decoded.getClaim("username");

    if (username.isMissing()) {
      throw new Exception("username claim not found");
    }

    return username.asString();
  }

  public String generateToken(Long userId, String username, String role) {
    Date issuedAt = new Date();

    String token;
    try {
      token = JWT.create()
                  .withIssuer("tomatoisblue.net")
                  .withIssuedAt(issuedAt)
                  .withExpiresAt(new Date(issuedAt.getTime() + 1000 * 60 * 60))
                  .withClaim("userId", userId)
                  .withClaim("username", username)
                  .withClaim("role", role)
                  .sign(Algorithm.HMAC256("CfNeddheorhDgnUfYqm8pjT5lykpTMUi"));
    } catch(Exception e) {
      token = null;
    }
    return token;
  }

  public String generateToken(String username, String role) {
    Date issuedAt = new Date();

    String token;
    try {
      token = JWT.create()
                  .withIssuer("tomatoisblue.net")
                  .withIssuedAt(issuedAt)
                  .withExpiresAt(new Date(issuedAt.getTime() + 1000 * 60 * 60))
                  .withClaim("username", username)
                  .withClaim("role", role)
                  .sign(Algorithm.HMAC256("CfNeddheorhDgnUfYqm8pjT5lykpTMUi"));
    } catch(Exception e) {
      token = null;
    }
    return token;
  }
}