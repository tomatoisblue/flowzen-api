package jp.flowzen.flowzenapi.dto.board;

import java.time.Instant;

/**
 * BoardDTO
 */
public class BoardDTO {
  private Long boardId;
  private String title;
  private Instant updatedOn;

  public BoardDTO(Long boardId, String title, Instant updatedOn) {
    this.boardId = boardId;
    this.title = title;
    this.updatedOn = updatedOn;
  }

  public Long getBoardId() {
    return boardId;
  }

  public void setBoardId(Long boardId) {
    this.boardId = boardId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Instant getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Instant updatedOn) {
    this.updatedOn = updatedOn;
  }
}