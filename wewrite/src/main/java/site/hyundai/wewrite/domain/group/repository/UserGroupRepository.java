package site.hyundai.wewrite.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Group;
import site.hyundai.wewrite.domain.entity.User;
import site.hyundai.wewrite.domain.entity.UserGroup;

import java.util.List;
import java.util.Optional;

/**
 * @author 이소민
 */

public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, UserGroupRepositoryCustom{
    Optional<Object> findByGroupAndUser(Group group, User user);

    List<UserGroup> findByUserOrderByCreatedAt(User user);

    void deleteByGroup(Group group);

    void deleteByGroupAndUser(Group group, User user);
}
