package jp.flowzen.flowzenapi.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jp.flowzen.flowzenapi.jwt.JwtUtil;
import jp.flowzen.flowzenapi.service.user.UserFindService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * CustomRequestHeaderAuthenticationFilter
 */
public class CustomRequestHeaderAuthenticationFilter extends RequestHeaderAuthenticationFilter {

  public CustomRequestHeaderAuthenticationFilter(AuthenticationManager authenticationManager,
                                                 JwtUtil jwtUtil,
                                                 UserFindService userFindService)
  {
    setPrincipalRequestHeader("Authorization");
    setExceptionIfHeaderMissing(false);
    setAuthenticationManager(authenticationManager);

    this.setAuthenticationSuccessHandler((req, res, auth) -> {
      String username = auth.getName();
      String role = auth.getAuthorities().iterator().next().toString();

      Long userId = userFindService.findByUsername(username).getId();

      String token = jwtUtil.generateToken(userId, username, role);
      res.setHeader("X-AUTH-TOKEN", token);
      res.setStatus(HttpServletResponse.SC_OK);
    });

    this.setAuthenticationFailureHandler((req, res, auth) -> {
      if (auth.getCause() instanceof TokenExpiredException) {
        System.out.println("Fail: Token has expired.");
        SecurityContextHolder.clearContext();
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired.");
      } else {
        System.out.println("Fail: Header Authentication");
        SecurityContextHolder.clearContext();
      }
    });
  }
}