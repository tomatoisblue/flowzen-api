package jp.flowzen.flowzenapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * User
 */
@Entity
@Table(name = "\"user\"")
public class User {
  private final static String DEFAULT_ROLENAME = "GENERAL";

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 4, max = 20)
  @Column(name = "username", unique = true, length = 25, nullable = false)
  private String username;

  @Column(name = "email", unique = true, length = 255, nullable = false)
  private String email;

  @Column(name = "password", length = 120, nullable = false)
  private String password;

  @Column(name = "rolename", length = 20, nullable = false)
  private String rolename;

  // has many tables
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Board> boards;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Task> tasks;

  @CreationTimestamp
  @Column(name = "created_on", nullable = false)
  private Instant createdOn;

  @UpdateTimestamp
  @Column(name = "updated_on", nullable = false)
  private Instant updatedOn;

  public User() {}

  public User(String username, String email, String password) {
    this(username, email, password, DEFAULT_ROLENAME);
  }

  public User(String username, String email, String password, String rolename) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.rolename = rolename;
  }

  @Override
  public String toString() {
    return String.format("Username : %s\nRole: %s\n", username, rolename);
  }

  /*
   * Getters and Setter
   */

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRolename() {
    return rolename;
  }

  public void setRolename(String rolename) {
    this.rolename = rolename;
  }
}
