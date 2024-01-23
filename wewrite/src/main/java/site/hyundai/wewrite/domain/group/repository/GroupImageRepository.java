package site.hyundai.wewrite.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Group;
import site.hyundai.wewrite.domain.entity.GroupImage;

import java.util.Optional;

public interface GroupImageRepository extends JpaRepository<GroupImage, Long> {
    Optional<GroupImage> findByGroup(Group group);
}
