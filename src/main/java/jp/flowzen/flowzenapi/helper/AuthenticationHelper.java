package jp.flowzen.flowzenapi.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.userDetails.CustomUserDetails;

/**
 * Authentication Helper
 */

@Component
public class AuthenticationHelper {

  public Long getCurrentUserId() {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userDetails.getUser().getId();
  }

  public User getCurrentUser() {
    CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userDetails.getUser();
  }

  public CustomUserDetails getAuthenticatedUserDetails() {
    return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public String redirectIfLoggedIn(String viewIfLoggedIn, String viewIfNotLoggedIn) {
    if (isLoggedIn()) {
      return viewIfLoggedIn;
    }
    return viewIfNotLoggedIn;
  }

  public boolean isLoggedIn() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.getPrincipal() instanceof UserDetails) {
      return true;
    }
    return false;
  }

  public void logout(Long userId) {

  }
}