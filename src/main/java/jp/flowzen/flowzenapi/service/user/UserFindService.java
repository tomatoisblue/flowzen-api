package jp.flowzen.flowzenapi.service.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.repository.UserRepository;

@Service
public class UserFindService {
  private UserRepository userRepository;

  public UserFindService(
    UserRepository userRepository
  )
  {
    this.userRepository = userRepository;
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User findByUserId(Long userId) throws Exception {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new Exception();
    }
  }

}