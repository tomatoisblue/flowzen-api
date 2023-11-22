package jp.flowzen.flowzenapi.service.board;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.entity.Board;
import jp.flowzen.flowzenapi.entity.User;
import jp.flowzen.flowzenapi.form.board.BoardEditForm;
import jp.flowzen.flowzenapi.form.board.BoardCreationForm;
import jp.flowzen.flowzenapi.repository.BoardRepository;
import jp.flowzen.flowzenapi.service.user.UserFindService;

/**
 * BoardService
 */
@Service
public class BoardService {
  private BoardRepository boardRepository;
  private UserFindService userFindService;

  public BoardService(BoardRepository boardRepository,
                      UserFindService userFindService) {
    this.boardRepository = boardRepository;
    this.userFindService = userFindService;
  }

  public void create(BoardCreationForm form) throws Exception {
    form.getTitle().strip();
    User user = null;
    try {
      user = userFindService.findByUserId(form.getUserId());
    } catch (Exception e) {
      throw e;
    }
    Board board = new Board(form.getTitle(), user);
    boardRepository.save(board);
  }

  public Board update(BoardEditForm form)  {
    form.getTitle().strip();
    Optional<Board> optionalBoard = boardRepository.findById(form.getBoardId());
    if (optionalBoard.isEmpty()) {
      return null;
    }

    Board board = (Board) optionalBoard.get();
    board.setTitle(form.getTitle());
    return boardRepository.save(board);
  }

  public List<Board> findAllByUserId(Long boardId) {
    return boardRepository.findAllByUserId(boardId);
  }

  // public void changeTitle(BoardEditForm form) {
  //   Optional<Board> optionalBoard = boardRepository.findById(form.getBoardId());
  //   if (optionalBoard.isEmpty()) {
  //     return;
  //   }

  //   Board board = optionalBoard.get();
  //   board.setTitle(form.getTitle());
  //   boardRepository.save(board);
  // }

  public void deleteBoard(Long boardId) {
    Optional<Board> optionalBoard = boardRepository.findById(boardId);
    if (optionalBoard.isEmpty()) {
      return;
    }
    boardRepository.delete(optionalBoard.get());
  }
}