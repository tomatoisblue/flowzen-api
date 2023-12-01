package jp.flowzen.flowzenapi.service.user_service_helper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.repository.UserRepository;
import jp.flowzen.flowzenapi.service.user.UserServiceHelper;

@ExtendWith(MockitoExtension.class)
public class EmailExistsTest {
  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceHelper userServiceHelper;

  @Test
  public void emailExists_ShouldReturnTrueWhenUserExists() {
    // Given
    String existingEmail = "existing@example.com";
    User user = new User("testuser", existingEmail, "7isTuGob8Z8G8%Lz8ZGB");

    // Mock the behavior of userRepository
    when(userRepository.findByEmailIgnoreCase(existingEmail)).thenReturn(user);

    // Create UserServiceHelper with mocked dependencies
    UserServiceHelper userServiceHelper = new UserServiceHelper(passwordEncoder, userRepository);

    //When
    boolean exists = userServiceHelper.emailExists(existingEmail);

    // Then
    assertThat(exists).isTrue();
  }

  @Test
  public void emailExists_ShouldReturnFalseWhenUserDoesNotExist() {
    // Given
    String nonExistingEmail = "non-existing@example.com";

    // Mock the behavior of userRepository
    when(userRepository.findByEmailIgnoreCase(nonExistingEmail)).thenReturn(null);

    // Create UserServiceHelper with mocked dependencies
    UserServiceHelper userServiceHelper = new UserServiceHelper(passwordEncoder, userRepository);

    //When
    boolean exists = userServiceHelper.emailExists(nonExistingEmail);

    // Then
    assertThat(exists).isFalse();
  }
}