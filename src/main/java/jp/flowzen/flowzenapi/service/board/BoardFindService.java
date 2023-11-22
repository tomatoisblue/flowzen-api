package jp.flowzen.flowzenapi.service.board;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.dto.board.BoardDTO;
import jp.flowzen.flowzenapi.entity.Board;
import jp.flowzen.flowzenapi.repository.BoardRepository;

/**
 * BoardService
 */
@Service
public class BoardFindService {
  private final BoardRepository boardRepository;

  public BoardFindService(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public Board findByBoardId(Long boardId) {
    Optional<Board> optionalBoard = boardRepository.findById(boardId);
    if(optionalBoard.isPresent()) {
      return optionalBoard.get();
    }
    return null;
  }

  public List<BoardDTO> findAllByUserId(Long userId) throws Exception {
    List<Board> allBoards = null;
    try {
      allBoards = boardRepository.findAllByUserId(userId);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    }
    return allBoards.stream()
                    .map(board -> new BoardDTO(
                                        board.getId(),
                                        board.getTitle(),
                                        board.getUpdatedOn()))
                    .collect(Collectors.toList());
  }
}