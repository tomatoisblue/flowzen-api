package jp.flowzen.flowzenapi.service.task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.dto.task.TaskDTO;
import jp.flowzen.flowzenapi.entity.Board;
import jp.flowzen.flowzenapi.entity.Task;
import jp.flowzen.flowzenapi.enums.Status;
import jp.flowzen.flowzenapi.form.task.TaskEditForm;
import jp.flowzen.flowzenapi.form.task.TaskCreationForm;
import jp.flowzen.flowzenapi.helper.AuthenticationHelper;
import jp.flowzen.flowzenapi.repository.TaskRepository;
import jp.flowzen.flowzenapi.service.board.BoardFindService;


/**
 * TaskService
 */
@Service
public class TaskService {
  private TaskRepository taskRepository;
  private BoardFindService boardFindService;
  private AuthenticationHelper authenticationHelper;

  public TaskService(TaskRepository taskRepository,
                     BoardFindService boardFindService,
                     AuthenticationHelper authenticationHelper) {
    this.taskRepository = taskRepository;
    this.boardFindService = boardFindService;
    this.authenticationHelper = authenticationHelper;
  }

  public void create(TaskCreationForm form) throws Exception {
    Board board = boardFindService.findByBoardId(form.getBoardId());

    if (board == null) {
      throw  new Exception();
    }


    try {
      Task task = new Task();
      task.setTitle(form.getTitle());
      task.setStatus(form.getStatus());
      task.setDescription(form.getDescription());
      task.setExpirationDate(form.getExpirationDate());
      task.setUrl(form.getUrl());
      task.setBoard(board);
      task.setUser(authenticationHelper.getCurrentUser());

      taskRepository.save(task);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    }
  }

  public List<TaskDTO> findAllByBoardId(Long boardId) {
    List<Task> allTasks= taskRepository.findAllByBoardId(boardId);
    return allTasks.stream()
                   .map(task ->  new TaskDTO(
                                        task.getId(),
                                        task.getTitle(),
                                        task.getStatus(),
                                        task.getDescription(),
                                        task.getExpirationDate(),
                                        task.getUrl(),
                                        task.getBoard().getId(),
                                        task.getUpdatedOn(),
                                        task.getCreatedOn()))
                     .collect(Collectors.toList());
  }

  // public Task findById(Long taskId) {
  //   Optional<Task> optionalTask = taskRepository.findById(taskId);
  //   if (optionalTask == null) {
  //     return null;
  //   }
  //   return optionalTask.get();
  // }

  // public Map<Status, List<Task>> groupByStatus(List<Task> tasks) {
  //   Map<Status, List<Task>> groupedTasks =
  //     tasks.stream()
  //          .collect(Collectors.groupingBy(task -> task.getStatus()));

  //   return groupedTasks;
  // }

  public void update(TaskEditForm form) throws Exception {
    Optional<Task> optionalTask = taskRepository.findById(form.getTaskId());
    if (optionalTask.isEmpty()) {
      System.out.println("task not found");
      throw new Exception();
    }
    Task task = optionalTask.get();

    Board board = boardFindService.findByBoardId(form.getBoardId());
    if (board == null) {
      System.out.println("board not found");
      throw new Exception();
    }


    try {

      task.setTitle(form.getTitle());
      task.setStatus(form.getStatus());
      task.setDescription(form.getDescription());
      task.setExpirationDate(form.getExpirationDate());
      task.setUrl(form.getUrl());
      task.setBoard(board);
      task.setUser(authenticationHelper.getCurrentUser());

      taskRepository.save(task);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      throw e;
    }
  }

  // returns Board ID
  public void deleteTask(Long taskId) throws Exception {
    Optional<Task>  optionalTask = taskRepository.findById(taskId);
    if (optionalTask.isEmpty()) {
      throw new Exception();
    }

    try {
      taskRepository.deleteById(taskId);
    } catch (Exception e) {
      throw e;
    }
  }

}