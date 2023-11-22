package jp.flowzen.flowzenapi.controller;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.flowzen.flowzenapi.dto.board.BoardDTO;
import jp.flowzen.flowzenapi.dto.task.TaskDTO;
import jp.flowzen.flowzenapi.entity.Board;
import jp.flowzen.flowzenapi.entity.Task;
import jp.flowzen.flowzenapi.enums.Status;
import jp.flowzen.flowzenapi.form.board.BoardEditForm;
import jp.flowzen.flowzenapi.form.board.BoardCreationForm;
import jp.flowzen.flowzenapi.service.board.BoardFindService;
import jp.flowzen.flowzenapi.service.board.BoardService;
import jp.flowzen.flowzenapi.service.task.TaskService;
import jp.flowzen.flowzenapi.userDetails.CustomUserDetails;
import jp.flowzen.flowzenapi.helper.AuthenticationHelper;
import jp.flowzen.flowzenapi.helper.BoardPermissionHelper;
import jp.flowzen.flowzenapi.jwt.JwtUtil;

import jakarta.validation.Valid;

/**
 * BoardController
 */
@RestController
public class BoardController {
  private BoardService boardService;
  private BoardFindService boardFindService;
  private TaskService taskService;
  private JwtUtil jwtUtil;

  BoardController(BoardService boardService,
                  BoardFindService boardFindService,
                  TaskService taskService,
                  JwtUtil jwtUtil) {
    this.boardService = boardService;
    this.boardFindService = boardFindService;
    this.taskService = taskService;
    this.jwtUtil = jwtUtil;

  }

  @GetMapping("/boards")
  public ResponseEntity<?> showAllBoards(@RequestHeader("Authorization") String token) {
    Long userId = jwtUtil.getUserId(token);
    try {
      List<BoardDTO> boards = boardFindService.findAllByUserId(userId);
      return ResponseEntity.ok().body(boards);
    } catch(Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @GetMapping("/boards/{boardId}")
  public ResponseEntity<?> showBoardDetails(@RequestHeader("Authorization") String token, @PathVariable Long boardId) {

    Board board = boardFindService.findByBoardId(boardId);
    if (board == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    // Map<Status, List<Task>> tasks = taskService.groupByStatus(taskService.findAllByBoardId(id));
    // model.addAttribute("board", board);
    // model.addAttribute("todoTasks", tasks.get(Status.TODO));
    // model.addAttribute("inProgressTasks", tasks.get(Status.IN_PROGRESS));
    // model.addAttribute("doneTasks", tasks.get(Status.DONE));

    List<TaskDTO> allTasks = taskService.findAllByBoardId(boardId);
    return ResponseEntity.ok().body(allTasks);
  }

  // @GetMapping("/board/new")
  // public String showBoardCreation(@ModelAttribute("form") BoardCreationForm form) {
  //   return "board/board-creation";
  // }


  @PostMapping("/boards")
  public ResponseEntity<?> createBoard(@RequestHeader("Authorization") String token ,@Valid @RequestBody BoardCreationForm form) {
    Long userId = jwtUtil.getUserId(token);
    form.setUserId(userId);
    try {
      boardService.create(form);
      return ResponseEntity.ok().body(null);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }


  // @GetMapping("/boards/{id}/edit")
  // public String showBoardEdit(@ModelAttribute BoardEditForm form, @PathVariable("id") Long id, Model model) {
  //   if (!boardPermissionHelper.isOwner(id)) {
  //     return "redirect:/boards";
  //   }

  //   Board board = boardFindService.findByBoardId(id);
  //   if (board == null) {
  //     return "redirect:/boards";
  //   }
  //   model.addAttribute(board);
  //   return "board/board-edit";
  // }

  @PatchMapping("/boards/{boardId}")
  public ResponseEntity<?> updateBoard(@RequestHeader("Authorization") String token, @Valid @PathVariable Long boardId, @Valid @RequestBody BoardEditForm form, BindingResult result) {
    Long userId = jwtUtil.getUserId(token);

    if (boardId != form.getBoardId() || userId != boardFindService.findByBoardId(boardId).getUser().getId()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    try {
      form.setBoardId(boardId);
      boardService.update(form);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.ok().body(null);
  }

  @DeleteMapping("/boards/{boardId}")
  public ResponseEntity<?> DeleteBoard(@PathVariable Long boardId) {
    try {
      boardService.deleteBoard(boardId);
      return ResponseEntity.ok().body(null);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }


}