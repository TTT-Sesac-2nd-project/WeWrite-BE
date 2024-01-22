package site.hyundai.wewrite.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import site.hyundai.wewrite.domain.entity.BoardImage;
import site.hyundai.wewrite.domain.entity.Image;
import site.hyundai.wewrite.domain.entity.QBoardImage;
import site.hyundai.wewrite.domain.entity.QImage;
import site.hyundai.wewrite.domain.image.repository.ImageRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static site.hyundai.wewrite.domain.entity.QBoardImage.boardImage;
import static site.hyundai.wewrite.domain.entity.QImage.image;

public class BoardImageRepositoryImpl extends QuerydslRepositorySupport implements BoardImageRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory queryFactory;

    public BoardImageRepositoryImpl(EntityManager em) {
        super(BoardImage.class);
        this.queryFactory = new JPAQueryFactory(em);

    }

   QBoardImage boardImage = QBoardImage.boardImage;

    @Override
    public List<BoardImage> findAllByBoardId(Long boardId) {
        return queryFactory.select(boardImage)
                .from(boardImage)
                .where(boardImage.board.boardId.eq(boardId))
                .fetch();
    }

    @Override
    public Long findOneLatestImageIdByBoardId(Long boardId) {
        return queryFactory.select(boardImage.image.imageId)
                .from(boardImage)
                .where(boardImage.board.boardId.eq(boardId))
                .orderBy(boardImage.image.imageId.desc())
                .fetchFirst();
    }
}
