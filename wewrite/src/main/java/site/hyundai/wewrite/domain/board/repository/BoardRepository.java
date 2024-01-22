package site.hyundai.wewrite.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.Group;

public interface BoardRepository extends JpaRepository<Board, Long>{
}
