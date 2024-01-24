package site.hyundai.wewrite.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Board;

/**
 * @author 김동욱
 */
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
