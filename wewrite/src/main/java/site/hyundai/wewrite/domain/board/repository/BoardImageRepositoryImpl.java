package site.hyundai.wewrite.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import site.hyundai.wewrite.domain.entity.BoardImage;
import site.hyundai.wewrite.domain.entity.Image;
import site.hyundai.wewrite.domain.entity.QBoardImage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author 김동욱
 */
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
    public Image findOneLatestImageByBoardId(Long boardId) {
        return queryFactory.select(boardImage.image)
                .from(boardImage)
                .where(boardImage.board.boardId.eq(boardId))
                .orderBy(boardImage.image.imageId.desc())
                .fetchFirst();
    }
}
