package site.hyundai.wewrite.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.auth.entity.User;

public interface UserRepository  extends JpaRepository<User,String> {
}
