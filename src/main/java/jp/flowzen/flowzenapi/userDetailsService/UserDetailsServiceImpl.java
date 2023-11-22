package jp.flowzen.flowzenapi.userDetailsService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.repository.UserRepository;
import jp.flowzen.flowzenapi.userDetails.UserDetailsImpl;
/**
 * UserDetailsService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if(user == null) {
      throw new UsernameNotFoundException("Not Found");
    }
    return new UserDetailsImpl(user);
  }
}