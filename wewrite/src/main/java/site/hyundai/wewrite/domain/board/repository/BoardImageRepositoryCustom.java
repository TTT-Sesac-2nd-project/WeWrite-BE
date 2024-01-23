package site.hyundai.wewrite.domain.board.repository;

import site.hyundai.wewrite.domain.entity.BoardImage;
import site.hyundai.wewrite.domain.entity.Image;

import java.util.List;

public interface BoardImageRepositoryCustom {

    public List<BoardImage> findAllByBoardId(Long boardId);

    public Image findOneLatestImageByBoardId(Long boardId);
}
