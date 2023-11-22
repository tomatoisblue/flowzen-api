package jp.flowzen.flowzenapi.controller;

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

import jp.flowzen.flowzenapi.entity.Task;
import jp.flowzen.flowzenapi.enums.Status;
import jp.flowzen.flowzenapi.form.task.TaskEditForm;
import jp.flowzen.flowzenapi.form.task.TaskCreationForm;
import jp.flowzen.flowzenapi.helper.AuthenticationHelper;
import jp.flowzen.flowzenapi.helper.BoardPermissionHelper;
import jp.flowzen.flowzenapi.helper.TaskPermissionHelper;
import jp.flowzen.flowzenapi.jwt.JwtUtil;
import jp.flowzen.flowzenapi.service.board.BoardFindService;
import jp.flowzen.flowzenapi.service.task.TaskFindService;
import jp.flowzen.flowzenapi.service.task.TaskService;

import jakarta.validation.Valid;

/**
 * TaskController
 */
@RestController
public class TaskController {
  private TaskService taskService;
  private TaskFindService taskFindService;
  private BoardFindService boardFindService;
  private JwtUtil jwtUtil;

  public TaskController(TaskService taskService,
                        TaskFindService taskFindService,
                        BoardFindService boardFindService,
                        JwtUtil jwtUtil) {
    this.taskService = taskService;
    this.taskFindService = taskFindService;
    this.boardFindService = boardFindService;
    this.jwtUtil = jwtUtil;
  }

  // @GetMapping("/tasks/{taskId}")
  // public String showTaskDetails(@PathVariable Long taskId, Model model) {

  //   Task task = taskFindService.findById(taskId);
  //   model.addAttribute("task", task);
  //   return "task/task-details";
  // }

  // @GetMapping("/tasks/{boardId}/new")
  // public String showTaskCreation(@ModelAttribute TaskCreationForm form, @PathVariable Long boardId, Model model) {
  //   if (!boardPermissionHelper.isOwner(boardId)) {
  //     return "redirect:/boards";
  //   }
  //   model.addAttribute("boardId", boardId);
  //   model.addAttribute("form", form);
  //   model.addAttribute("statusList", Status.values());

  //   return "task/task-creation";
  // }

  @PostMapping("/boards/{boardId}/tasks")
  public ResponseEntity<?> createTask(@RequestHeader("Authorization") String token, @Valid @RequestBody TaskCreationForm form, BindingResult result, @PathVariable Long boardId) {
    Long userId = jwtUtil.getUserId(token);
    if (userId != boardFindService.findByBoardId(boardId).getUser().getId()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    if(result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    }

    form.setBoardId(boardId);
    form.setUserId(userId);
    try {
      taskService.create(form);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.ok().body(null);
  }

  // @GetMapping("/tasks/{taskId}/edit")
  // public String showTaskEdit(@ModelAttribute TaskEditForm form, @PathVariable("taskId") Long taskId, Model model) {
  //   if (!taskPermissionHelper.isOwner(taskId)) {
  //     return "redirect:/boards";
  //   }

  //   Task task = taskFindService.findById(taskId);
  //   if (task == null) {
  //     return "redirect:/boards";
  //   }

  //   form.setTaskId(task.getId());
  //   form.setTitle(task.getTitle());
  //   form.setStatus(task.getStatus());
  //   form.setDescription(task.getDescription());
  //   form.setExpirationDate(task.getExpirationDate());
  //   form.setUrl(task.getUrl());
  //   form.setBoardId(task.getBoard().getId());

  //   System.out.println(form);


  //   model.addAttribute("statusList", Status.values());
  //   model.addAttribute("form", form);
  //   return "task/task-edit";
  // }

  @PatchMapping("/boards/{boardId}/tasks/{taskId}")
  public ResponseEntity<?> updateTask(@RequestHeader("Authorization") String token, @PathVariable("boardId") Long boardId, @PathVariable("taskId") Long taskId, @Valid @RequestBody TaskEditForm form, BindingResult result) {
    Long userId = jwtUtil.getUserId(token);
    if (userId != boardFindService.findByBoardId(boardId).getUser().getId() ||
        userId != taskFindService.findById(taskId).getUser().getId() ||
        taskId != form.getTaskId()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    if (result.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
    }


    try {
      taskService.update(form);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.ok().body(null);
  }

  @DeleteMapping("boards/{boardId}/tasks/{taskId}")
  public ResponseEntity<?> deleteTask(@RequestHeader("Authorization") String token, @PathVariable Long boardId, @PathVariable Long taskId) {
    Long userId = jwtUtil.getUserId(token);
    if (userId != boardFindService.findByBoardId(boardId).getUser().getId() ||
        boardId != taskFindService.findById(taskId).getBoard().getId()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    try {
      taskService.deleteTask(taskId);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.ok().body(null);
  }
}