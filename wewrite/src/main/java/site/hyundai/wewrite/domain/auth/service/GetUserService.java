package site.hyundai.wewrite.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import site.hyundai.wewrite.domain.auth.repository.UserRepository;
import site.hyundai.wewrite.domain.entity.User;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;

import java.util.Objects;

/**
 * @author 이소민
 */

@Service
@RequiredArgsConstructor
public class GetUserService {
    private final UserRepository userRepository;
    private final AuthService authService;

    // token으로 유저 id 가져오기
    public String getUserByToken(HttpHeaders headers) {
        try {
            String jwtToken = Objects.requireNonNull(headers.get("token")).toString();
            jwtToken = jwtToken.replace("[", "");
            jwtToken = jwtToken.replace("]", "");
            return authService.getUserId(jwtToken);
        } catch (NullPointerException e) {
            throw new EntityNullException("토큰이 NULL(로그인 후 사용해주세요)");
        }


    }

    // userId로 유저 가져오기
    public User getUserByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNullException("User not found"));
    }
}
