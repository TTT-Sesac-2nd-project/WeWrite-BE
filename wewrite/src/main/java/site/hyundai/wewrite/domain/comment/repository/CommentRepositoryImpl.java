package site.hyundai.wewrite.domain.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import site.hyundai.wewrite.domain.entity.Comment;
import site.hyundai.wewrite.domain.entity.QComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static site.hyundai.wewrite.domain.entity.QComment.comment;

public class CommentRepositoryImpl extends QuerydslRepositorySupport implements CommentRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        super(Comment.class);
        this.queryFactory = new JPAQueryFactory(em);

    }

    QComment qComment = comment;

    @Override
    public List<Comment> getCommentsByBoardId(Long boardId) {
        return queryFactory.select(comment)
                .from(comment)
                .where(comment.board.boardId.eq(boardId))
                .orderBy(comment.createdAt.asc())
                .fetch();
    }

    @Override
    public Long getCommentCountByBoardId(Long boardId) {
        return queryFactory.select(comment.count())
                .from(comment)
                .where(comment.board.boardId.eq(boardId))
                .fetchCount();
    }
}