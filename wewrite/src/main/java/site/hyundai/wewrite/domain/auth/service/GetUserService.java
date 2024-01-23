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

    // token으로 유저 가져오기
    public User getUserByToken(HttpHeaders headers){
        String jwtToken = Objects.requireNonNull(headers.get("token")).toString();
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken);
        return  userRepository.findById(userId).orElseThrow(
                () -> new EntityNullException("User not found"));
    }
}
