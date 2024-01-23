package site.hyundai.wewrite.domain.group.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import site.hyundai.wewrite.domain.board.repository.BoardRepositoryCustom;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.QBoard;
import site.hyundai.wewrite.domain.entity.QUserGroup;
import site.hyundai.wewrite.domain.entity.UserGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static site.hyundai.wewrite.domain.entity.QBoard.board;
import static site.hyundai.wewrite.domain.entity.QUserGroup.userGroup;

public class UserGroupRepositoryImpl extends QuerydslRepositorySupport implements UserGroupRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory queryFactory;

    public UserGroupRepositoryImpl(EntityManager em) {
        super(UserGroup.class);
        this.queryFactory = new JPAQueryFactory(em);

    }

    QUserGroup qUserGroup = userGroup;


    @Override
    public List<UserGroup> getUserGroupsById(String userId) {
        return queryFactory.select(userGroup)
                .from(userGroup)
                .where(userGroup.user.userId.eq(userId))
                .fetch();
    }
}
