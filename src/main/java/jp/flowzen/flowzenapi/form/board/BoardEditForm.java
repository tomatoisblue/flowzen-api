package jp.flowzen.flowzenapi.form.board;

/**
 * BoardEditForm
 */
public class BoardEditForm extends BaseBoardForm  {
  private Long boardId;

  public Long getBoardId() {
    return boardId;
  }

  public void setBoardId(Long boardId) {
    this.boardId = boardId;
  }
}