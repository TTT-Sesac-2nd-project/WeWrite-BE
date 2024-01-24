package site.hyundai.wewrite.domain.board.repository;

import site.hyundai.wewrite.domain.entity.Board;

import java.util.List;

/**
 * @author 김동욱
 */
public interface BoardRepositoryCustom {
    List<Board> getBoardList(Long groupId);


}
