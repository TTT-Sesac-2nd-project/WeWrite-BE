package site.hyundai.wewrite.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import site.hyundai.wewrite.domain.board.repository.BoardRepositoryCustom;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.QBoard;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static site.hyundai.wewrite.domain.entity.QBoard.board;

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        super(Board.class);
        this.queryFactory = new JPAQueryFactory(em);

    }

    QBoard qBoard = board;


}
