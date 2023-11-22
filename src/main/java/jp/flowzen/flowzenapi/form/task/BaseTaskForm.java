package jp.flowzen.flowzenapi.form.task;

import java.time.LocalDate;

import jp.flowzen.flowzenapi.enums.Status;

import jakarta.validation.constraints.Size;



/**
 * BaseTaskForm
 */
public class BaseTaskForm {
  private Long boardId;
  private Long userId;

  @Size(min = 1, max = 30)
  private String title;

  private Status status;

  @Size(max = 512)
  private String description;

  private LocalDate expirationDate;

  @Size(max = 512)
  private String url;


  /*
   * Getters and Setters
   */

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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  public String getUrl() {
    if (url != null){
      url.strip();
    }
    return url;
  }

  public void setUrl(String url) {
    if (url != null){
      url.strip();
    }
    this.url = url;
  }

  public Long getBoardId() {
    return boardId;
  }

  public void setBoardId(Long boardId) {
    this.boardId = boardId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public boolean isStatusSelected(Status status) {
    if(this.status != status) {
      return false;
    }
    return true;
  }

}