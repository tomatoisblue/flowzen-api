package jp.flowzen.flowzenapi.form.board;

import jp.flowzen.flowzenapi.entity.User;

import jakarta.validation.constraints.Size;

/**
 * BaseBoardForm
 */
public class BaseBoardForm  {

  private Long userId;

  @Size(min = 1, max = 30)
  private String title;


  /*
   * Getters and Setters
   */

   public Long getUserId() {
    return userId;
   }

   public void setUserId(Long userId) {
    this.userId = userId;
   }


  public String getTitle() {
    if (title != null){
      title.strip();
    }
    return title;
  }
  public void setTitle(String title) {
    if (title != null){
      title.strip();
    }
    this.title = title;
  }
}