package jp.flowzen.flowzenapi.dto.task;

import java.time.Instant;
import java.time.LocalDate;

import jp.flowzen.flowzenapi.enums.Status;

/**
 * TaskDTO
 */
public class TaskDTO {
  private Long taskId;
  private String title;
  private Status status;
  private String description;
  private LocalDate expirationDate;
  private String url;
  private Long boardId;
  private Instant updatedOn;
  private Instant createdOn;

  public TaskDTO(
    Long taskId,
    String title,
    Status status,
    String description,
    LocalDate expirationDate,
    String url,
    Long boardId,
    Instant updatedOn,
    Instant createdOn
  ){
    this.taskId = taskId;
    this.title = title;
    this.status = status;
    this.description = description;
    this.expirationDate = expirationDate;
    this.url = url;
    this.boardId = boardId;
    this.updatedOn = updatedOn;
    this.createdOn = createdOn;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
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
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getBoardId() {
    return boardId;
  }

  public void setBoardId(Long boardId) {
    this.boardId = boardId;
  }

  public Instant getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }

  public Instant getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Instant createdOn) {
    this.createdOn = createdOn;
  }

}