package site.hyundai.wewrite.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Token;

/**
 * @author 김동욱
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
}
