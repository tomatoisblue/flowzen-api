package jp.flowzen.flowzenapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.flowzen.flowzenapi.entity.Board;
import java.util.List;

/**
 * BoardRepository
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {


  public List<Board> findAllByUserId(Long userId);

}