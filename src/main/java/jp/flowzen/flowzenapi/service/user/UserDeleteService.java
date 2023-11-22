package jp.flowzen.flowzenapi.service.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.repository.UserRepository;


/**
 * UserDeleteService
 */
@Service
public class UserDeleteService {

  private UserRepository userRepository;

  public UserDeleteService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void delete(Long userId) throws Exception {
    try {
      userRepository.deleteById(userId);
    } catch (Exception e) {
      throw e;
    }
  }
}