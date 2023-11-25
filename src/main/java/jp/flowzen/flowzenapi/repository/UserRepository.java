package jp.flowzen.flowzenapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.flowzen.flowzenapi.entity.User;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  public User findByUsername(String username);

  public User findByUsernameIgnoreCase(String username);

  public User findByEmail(String email);


  @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE %:query%")
  public List<User> findByUsernameContainingIgnoreCase(String query);
}