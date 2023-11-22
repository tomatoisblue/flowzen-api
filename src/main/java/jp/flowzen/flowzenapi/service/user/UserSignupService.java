package jp.flowzen.flowzenapi.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.repository.UserRepository;


/**
 * UserRegistrationService
 */
@Service
public class UserSignupService {

  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;

  public UserSignupService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public void signup(String username, String email, String password) throws Exception {

    // Generate a hashed password by BCrypt
    String hashedPassword = passwordEncoder.encode(password);
    // Create a User object with the hashed password
    try {
      User user = new User(username, email, hashedPassword);
      userRepository.saveAndFlush(user);
    } catch (Exception e) {
      throw e;
    }
  }
}