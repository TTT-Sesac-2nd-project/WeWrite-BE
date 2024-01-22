package site.hyundai.wewrite.domain.board.repository;

import site.hyundai.wewrite.domain.entity.Board;

import java.util.List;

public interface BoardRepositoryCustom {
List<Board> getBoardList(Long groupId);

}
