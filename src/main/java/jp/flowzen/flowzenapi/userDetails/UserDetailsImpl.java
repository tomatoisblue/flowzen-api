package jp.flowzen.flowzenapi.userDetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import jp.flowzen.flowzenapi.entity.User;

/**
 * UserDetailsImpl
 */
public class UserDetailsImpl implements CustomUserDetails {

  private User user;

  public UserDetailsImpl(User user) {
    this.user = user;
  }

  /*
   * Spring Securityにおいては`ROLE_`プレフィックスをつけた文字列
   * をロールとして扱う。
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.createAuthorityList("ROLE_" + this.user.getRolename());
  }

  @Override
  public Long getId() {
    return user.getId();
  }

  @Override
  public User getUser() {
    return user;
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }



}