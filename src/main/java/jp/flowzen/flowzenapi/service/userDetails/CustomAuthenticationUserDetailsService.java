package jp.flowzen.flowzenapi.service.userDetails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.exception.EmailNotFoundException;
import jp.flowzen.flowzenapi.repository.UserRepository;

/**
 * CustomAuthenticationUserDetailsService
 */
@Service
public class CustomAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

  @Value("${hmac.secret}")
  private String hmacSecret;

  private UserRepository userRepository;
  private UserDetailsService userDetailsService;

  public CustomAuthenticationUserDetailsService(
      UserRepository userRepository,
      UserDetailsService userDetailsService) {
    this.userRepository = userRepository;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token)
                                                        throws  JWTDecodeException,
                                                                TokenExpiredException,
                                                                EmailNotFoundException {

    DecodedJWT decodedJwt;

    try {
      // decodedJwt = JWT.require(Algorithm.HMAC256("CfNeddheorhDgnUfYqm8pjT5lykpTMUi")).build().verify(token.getPrincipal().toString());
      decodedJwt = JWT.require(Algorithm.HMAC256(hmacSecret)).build().verify(token.getPrincipal().toString());
    } catch (JWTDecodeException e) {
      throw new BadCredentialsException("Authorization header token is invalid");
    } catch (TokenExpiredException e) {
      throw e;
    }

    if (decodedJwt.getToken().isEmpty()) {
      throw new EmailNotFoundException("Authorization header must not be empty");
    }
    User user = userRepository.findByUsername(decodedJwt.getClaim("username").asString());
    return userDetailsService.loadUserByUsername(user.getEmail());
  }
}