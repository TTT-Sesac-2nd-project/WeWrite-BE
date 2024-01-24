package site.hyundai.wewrite.domain.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import site.hyundai.wewrite.domain.entity.Image;
import site.hyundai.wewrite.domain.entity.QImage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static site.hyundai.wewrite.domain.entity.QImage.image;

/**
 * @author 김동욱
 */
public class ImageRepositoryImpl extends QuerydslRepositorySupport implements ImageRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    private final JPAQueryFactory queryFactory;

    public ImageRepositoryImpl(EntityManager em) {
        super(Image.class);
        this.queryFactory = new JPAQueryFactory(em);

    }

    QImage qImage = image;


}
