package site.hyundai.wewrite.domain.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.Bookmark;
import site.hyundai.wewrite.domain.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author 이소민
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> , BookmarkRepositoryCustom {
    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByBoardAndUser(Board board, User user);
}
