package site.hyundai.wewrite.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.hyundai.wewrite.domain.entity.Token;

public interface TokenRepsository extends JpaRepository<Token, Long> {
}
