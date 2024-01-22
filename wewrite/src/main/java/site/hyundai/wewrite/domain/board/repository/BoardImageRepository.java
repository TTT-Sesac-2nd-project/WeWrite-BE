package site.hyundai.wewrite.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.BoardImage;

public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
}
