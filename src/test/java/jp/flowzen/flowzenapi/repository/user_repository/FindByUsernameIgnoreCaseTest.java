package jp.flowzen.flowzenapi.repository.user_repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.repository.UserRepository;

@DataJpaTest
public class FindByUsernameIgnoreCaseTest {

  private final UserRepository userRepository;
  private final TestEntityManager entityManager;

  @Autowired
  public FindByUsernameIgnoreCaseTest (
    UserRepository userRepository,
    TestEntityManager entityManager
  )
  {
    this.userRepository = userRepository;
    this.entityManager = entityManager;
  }


  @Test
  public void findByUsernameIgnoreCase_ShouldIgnoreCase() {
    // Given
    String username = "TestUser";
    String lowercaseUsername = "testuser";
    User user = new User(username, "test@example.com", "7isTuGob8Z8G8%Lz8ZGB");
    entityManager.persist(user);
    entityManager.flush();

    // When
    User foundUser = userRepository.findByUsernameIgnoreCase(lowercaseUsername);

    // Then
    assertThat(foundUser).isNotNull();
    assertThat(foundUser.getUsername()).isEqualTo(username);
  }

  @Test
  public void findByUsernameIgnoreCase_ShouldReturnNullForNoneExistentUser() {
    // When
    User foundUser = userRepository.findByUsernameIgnoreCase("nonexistentuser");;

    // Then
    assertThat(foundUser).isNull();
  }





}