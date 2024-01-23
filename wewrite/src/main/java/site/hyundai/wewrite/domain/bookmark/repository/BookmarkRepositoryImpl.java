package site.hyundai.wewrite.domain.bookmark.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import site.hyundai.wewrite.domain.board.repository.BoardImageRepositoryCustom;
import site.hyundai.wewrite.domain.entity.Bookmark;
import site.hyundai.wewrite.domain.entity.Comment;
import site.hyundai.wewrite.domain.entity.QBookmark;
import site.hyundai.wewrite.domain.entity.QComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static site.hyundai.wewrite.domain.entity.QBookmark.*;
import static site.hyundai.wewrite.domain.entity.QComment.comment;

public class BookmarkRepositoryImpl extends QuerydslRepositorySupport implements BookmarkRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory queryFactory;

    public BookmarkRepositoryImpl(EntityManager em) {
        super(Comment.class);
        this.queryFactory = new JPAQueryFactory(em);

    }

    QBookmark qBookmark = bookmark;


    @Override
    public boolean isBookmarked(String userId, Long boardId) {
        Bookmark result = queryFactory.select(bookmark)
                .from(bookmark)
                .where(bookmark.user.userId.eq(userId).and(bookmark.board.boardId.eq(boardId)))
                .fetchFirst();
        return result!=null;
    }

    @Override
    public void deleteByBoardId(Long boardId) {
        queryFactory.delete(bookmark)
                .where(bookmark.board.boardId.eq(boardId))
                .execute();
    }
}
