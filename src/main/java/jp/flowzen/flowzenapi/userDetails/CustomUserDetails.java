package jp.flowzen.flowzenapi.userDetails;

import org.springframework.security.core.userdetails.UserDetails;

import jp.flowzen.flowzenapi.entity.User;

/**
 * CustomUserDetails
 */
public interface CustomUserDetails extends UserDetails {
  public Long getId();

  public User getUser();
}