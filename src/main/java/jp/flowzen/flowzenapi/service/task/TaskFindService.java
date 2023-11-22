package jp.flowzen.flowzenapi.service.task;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jp.flowzen.flowzenapi.entity.Task;
import jp.flowzen.flowzenapi.repository.TaskRepository;


/**
 * TaskFindService
 */
@Service
public class TaskFindService {
  private TaskRepository taskRepository;

  public TaskFindService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }


  // public List<Task> findAllByBoardId(Long boardId) {
  //   return taskRepository.findAllByBoardId(boardId);
  // }

  public Task findById(Long taskId) {
    Optional<Task> optionalTask = taskRepository.findById(taskId);
    if (optionalTask.isEmpty()) {
      return null;
    }
    return optionalTask.get();
  }



}