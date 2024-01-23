package site.hyundai.wewrite.domain.board.repository;

import site.hyundai.wewrite.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {
    List<Comment> getCommentsByBoardId(Long boardId);

    Long getCommentCountByBoardId(Long boardId);
}
