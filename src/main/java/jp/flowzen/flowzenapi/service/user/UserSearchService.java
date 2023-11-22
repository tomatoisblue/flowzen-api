package jp.flowzen.flowzenapi.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.repository.UserRepository;

/**
 * UserSearchService
 */
@Service
public class UserSearchService {

  private UserRepository userRepository;

  public UserSearchService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> searchUser(String query) {
    return userRepository.findByUsernameContainingIgnoreCase(query);
  }

}