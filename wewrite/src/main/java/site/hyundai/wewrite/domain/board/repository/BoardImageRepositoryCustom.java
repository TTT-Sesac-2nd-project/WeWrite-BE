package site.hyundai.wewrite.domain.board.repository;

import site.hyundai.wewrite.domain.entity.BoardImage;
import site.hyundai.wewrite.domain.entity.Image;

import java.util.List;

/**
 * @author 김동욱
 */
public interface BoardImageRepositoryCustom {

    public List<BoardImage> findAllByBoardId(Long boardId);

    public Image findOneLatestImageByBoardId(Long boardId);
}
