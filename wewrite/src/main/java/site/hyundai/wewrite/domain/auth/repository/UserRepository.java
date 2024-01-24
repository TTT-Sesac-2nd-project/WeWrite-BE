package site.hyundai.wewrite.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.hyundai.wewrite.domain.entity.User;

/**
 * @author 김동욱
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
