package site.hyundai.wewrite.domain.group.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.hyundai.wewrite.domain.entity.Group;

import java.util.Optional;

/**
 * @author 이소민
 */



public interface GroupRepository extends JpaRepository<Group, Long> {
 Optional<Group> findByGroupCode(String groupCode);

}
