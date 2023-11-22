package jp.flowzen.flowzenapi.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.repository.UserRepository;


/**
 * UserServiceHelper
 */
@Service
public class UserServiceHelper {

  private UserRepository userRepository;

  public UserServiceHelper(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public boolean usernameExists(String username) {
    if (userRepository.findByUsername(username) == null) {
      return false;
    }
    return true;
  }

  public boolean emailExists(String email) {
    if (userRepository.findByEmail(email) == null) {
      return false;
    }
    return true;
  }
}