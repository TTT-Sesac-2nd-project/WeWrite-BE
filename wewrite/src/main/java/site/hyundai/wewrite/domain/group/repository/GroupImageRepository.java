package site.hyundai.wewrite.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.GroupImage;

public interface GroupImageRepository extends JpaRepository<GroupImage, Long> {
}
