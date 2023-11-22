package jp.flowzen.flowzenapi.form.user;

/**
 * UserEditForm
 */
public class UserEditForm extends BaseUserForm {
  private Long userId;


  /*
   * Getters and Setters
   */

  public Long setUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}