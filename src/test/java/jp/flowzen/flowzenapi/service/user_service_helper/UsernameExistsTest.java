package jp.flowzen.flowzenapi.service.user_service_helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.repository.UserRepository;
import jp.flowzen.flowzenapi.service.user.UserServiceHelper;

public class UsernameExistsTest {
  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceHelper userServiceHelper;

  @Test
  public void usernameExists_ShouldReturnTrueWhenUserExists() {
    // Given
    String existingUsername = "existingUser";
    User user = new User(existingUsername, "test@example.com", "7isTuGob8Z8G8%Lz8ZGB");

    // Mock the behavior of userRepository
    when(userRepository.findByUsernameIgnoreCase(existingUsername)).thenReturn(user);

    // Create UserServiceHelper with mocked dependencies
    UserServiceHelper userServiceHelper = new UserServiceHelper(passwordEncoder, userRepository);

    //When
    boolean exists = userServiceHelper.usernameExists(existingUsername);

    // Then
    assertThat(exists).isTrue();
  }

  @Test
  public void usernameExists_ShouldReturnFalseWhenUserDoesNotExist() {
    // Given
    String nonExistingUsername = "nonExistingUser";

    // Mock the behavior of userRepository
    when(userRepository.findByUsernameIgnoreCase(nonExistingUsername)).thenReturn(null);

    // Create UserServiceHelper with mocked dependencies
    UserServiceHelper userServiceHelper = new UserServiceHelper(passwordEncoder, userRepository);

    //When
    boolean exists = userServiceHelper.usernameExists(nonExistingUsername);

    // Then
    assertThat(exists).isFalse();
  }



}