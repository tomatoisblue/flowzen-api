package jp.flowzen.flowzenapi.authenticationProvider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import jp.flowzen.flowzenapi.exception.EmailNotFoundException;
import jp.flowzen.flowzenapi.userDetails.CustomUserDetails;

/**
 * CustomAuthenticationProvider
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
  private final UserDetailsService userDetailsService;

  public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws EmailNotFoundException, AuthenticationException {
    String email = authentication.getName();
    String plainPassword = authentication.getCredentials().toString();
    // String hashedPassword = passwordEncoder.encode(plainPassword);

    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

    if (userDetails == null) {
      throw new EmailNotFoundException("指定されたメールアドレスを持つユーザーが見つかりませんでした。");
    }

    String storedHashedPassword = userDetails.getPassword();

    if (!BCrypt.checkpw(plainPassword, storedHashedPassword)) {
      throw new AuthenticationException("パスワードが違います。"){};
    }

    Authentication authenticated = new UsernamePasswordAuthenticationToken(
                                          (CustomUserDetails) userDetails,
                                          null,
                                          userDetails.getAuthorities());

    return authenticated;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}