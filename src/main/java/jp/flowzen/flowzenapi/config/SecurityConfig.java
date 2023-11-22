package jp.flowzen.flowzenapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import jp.flowzen.flowzenapi.authenticationProvider.CustomAuthenticationProvider;
import jp.flowzen.flowzenapi.filter.CustomRequestHeaderAuthenticationFilter;
import jp.flowzen.flowzenapi.filter.JsonEmailPasswordAuthenticationFilter;
import jp.flowzen.flowzenapi.jwt.JwtUtil;
import jp.flowzen.flowzenapi.service.user.UserFindService;
import jp.flowzen.flowzenapi.service.userDetails.CustomAuthenticationUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
  // private final AuthenticationManagerBuilder authenticationManagerBuilder;
  // private final CustomAuthenticationProvider authenticationProvider;
  // private final PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider;
  // private final CustomAuthenticationUserDetailsService authenticationUserDetailsService;
  private final UserFindService userFindService;
  private final JwtUtil jwtUtil;

  public SecurityConfig(CustomAuthenticationUserDetailsService authenticationUserDetailsService,
                        UserDetailsService userDetailsService,
                        AuthenticationManagerBuilder authenticationManagerBuilder,
                        UserFindService userFindService,
                        JwtUtil jwtUtil) {
    // this.authenticationProvider = authenticationProvider;
    // this.preAuthenticatedAuthenticationProvider = preAuthenticatedAuthenticationProvider;
    // this.authenticationUserDetailsService = authenticationUserDetailsService;
    // this.authenticationManagerBuilder = authenticationManagerBuilder;
    this.userFindService = userFindService;
    this.jwtUtil = jwtUtil;

    var preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
    preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService);
    preAuthenticatedAuthenticationProvider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
    authenticationManagerBuilder.authenticationProvider(preAuthenticatedAuthenticationProvider);

    var authenticationProvider = new CustomAuthenticationProvider(userDetailsService);
    authenticationManagerBuilder.authenticationProvider(authenticationProvider);
  }

  /**
   * @param http
   * @return
   * @throws Exception
   */
  @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
          .requestMatchers("/css/**", "/signup", "/login", "/error").permitAll()
          .anyRequest().authenticated())
        .logout(logout -> logout.disable());

    http.headers(headers -> headers
      .frameOptions(frame -> frame.disable()));


    var authenticationManager = authenticationManager(http.getSharedObject(AuthenticationConfiguration.class));
    JsonEmailPasswordAuthenticationFilter jsonEmailPasswordAuthenticationFilter
      = new JsonEmailPasswordAuthenticationFilter(authenticationManager, jwtUtil, userFindService);

    CustomRequestHeaderAuthenticationFilter customRequestHeaderAuthenticationFilter
      = new CustomRequestHeaderAuthenticationFilter(authenticationManager, jwtUtil, userFindService);

    // JsonEmailPasswordAuthenticationFilter jsonEmailPasswordAuthenticationFilter
    //   = new JsonEmailPasswordAuthenticationFilter(authenticationManager(http), jwtUtil, userFindService);

    // CustomRequestHeaderAuthenticationFilter customRequestHeaderAuthenticationFilter
    //   = new CustomRequestHeaderAuthenticationFilter(authenticationManager(http), jwtUtil, userFindService);
    // var preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
    // preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService);
    // preAuthenticatedAuthenticationProvider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
    // authenticationManagerBuilder.authenticationProvider(preAuthenticatedAuthenticationProvider);

    http.addFilterAt(jsonEmailPasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilter(customRequestHeaderAuthenticationFilter);

    http.exceptionHandling(handling -> handling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
    http.exceptionHandling(handling -> handling.accessDeniedHandler((req, res, ex) -> res.setStatus(HttpServletResponse.SC_FORBIDDEN)));

    return http.build();
    // return http.getOrBuild();
  }

  @Bean
  // public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
  //   AuthenticationManagerBuilder authenticationManagerBuilder =
  //     http.getSharedObject(AuthenticationManagerBuilder.class);
  //     authenticationManagerBuilder.authenticationProvider(authenticationProvider);


  //     var preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
  //     preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService);
  //     preAuthenticatedAuthenticationProvider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
  //     authenticationManagerBuilder.authenticationProvider(preAuthenticatedAuthenticationProvider);

  //     return authenticationManagerBuilder.build();
  // }
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}