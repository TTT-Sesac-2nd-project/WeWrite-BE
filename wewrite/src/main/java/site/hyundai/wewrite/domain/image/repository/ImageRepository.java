package site.hyundai.wewrite.domain.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Image;

/**
 * @author 이소민
 */
public interface ImageRepository extends JpaRepository<Image, Long> {
}
