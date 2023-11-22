package jp.flowzen.flowzenapi.form.user;

import jp.flowzen.flowzenapi.annotation.UniqueEmail;
import jp.flowzen.flowzenapi.annotation.UniqueUsername;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



/**
 * BaseUserForm
 */
public class BaseUserForm {

  @Size(min = 4, max = 20)
  @Pattern(regexp = "^[\\p{Alnum}-_]{4,20}$", message = "ユーザーネームには半角の英数字とアンダースコア(_)、ハイフン(-)が使用できます")
  @UniqueUsername
  private String username;

  @NotBlank
  @Email
  @UniqueEmail
  private String email;

  @Size(min = 8, max = 30)
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$", message = "パスワードには英小文字、英大文字、数字、記号から最低1つずつ使用してください")
  private String password;

  @NotBlank
  private String confirmPassword;

  @AssertTrue
  public boolean isPasswordValid() {
    if (password == null || confirmPassword == null) {
      return false;
    }
    return password.equals(confirmPassword);
  }

  /*
   * Getters and Setters
   */

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

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String password) {
    this.confirmPassword = password;
  }
}