package jp.flowzen.flowzenapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.flowzen.flowzenapi.entity.Task;
import java.util.List;

/**
 * TaskRepository
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

  public List<Task> findAllByBoardId(Long boardId);

  // public void deleteAllByBoardIdInBatch(Long boardId);

}