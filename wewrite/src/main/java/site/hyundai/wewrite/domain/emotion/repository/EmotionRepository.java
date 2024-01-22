package site.hyundai.wewrite.domain.emotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.Emotion;
import site.hyundai.wewrite.domain.entity.User;

import java.util.Optional;

/**
 * @author 이소민
 */

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Optional<Emotion> findByBoardAndUser(Board board, User user);
}
