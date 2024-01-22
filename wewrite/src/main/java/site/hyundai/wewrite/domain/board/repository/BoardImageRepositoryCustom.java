package site.hyundai.wewrite.domain.board.repository;

import site.hyundai.wewrite.domain.entity.BoardImage;

import java.util.List;

public interface BoardImageRepositoryCustom {

    public List<BoardImage> findAllByBoardId(Long boardId);

    public Long findOneLatestImageIdByBoardId(Long boardId);
}
