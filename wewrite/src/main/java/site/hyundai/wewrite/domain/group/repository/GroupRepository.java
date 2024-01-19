package site.hyundai.wewrite.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
