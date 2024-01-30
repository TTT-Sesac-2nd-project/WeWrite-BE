package site.hyundai.wewrite.domain.auth.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import site.hyundai.wewrite.domain.auth.dto.AuthGetKakaoTokenDTO;
import site.hyundai.wewrite.domain.auth.dto.OpenIdResponseDto;
import site.hyundai.wewrite.domain.auth.repository.TokenRepository;
import site.hyundai.wewrite.domain.auth.repository.UserRepository;
import site.hyundai.wewrite.domain.auth.util.HttpUtil;
import site.hyundai.wewrite.domain.entity.Token;
import site.hyundai.wewrite.domain.entity.User;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.BadVariableRequestException;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;
import site.hyundai.wewrite.global.exeception.service.NotAuthorizedUserException;
import site.hyundai.wewrite.global.util.JwtTokenProvider;
import site.hyundai.wewrite.global.util.ResponseUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

/**
 * @author 김동욱
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private String get_token_url = "https://kauth.kakao.com/oauth/token";

    @Value("${wewrite.kakao-client-id}")
    private String client_id;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    private final HttpUtil httpUtil;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepsository;
    private final ResponseUtil responseUtil;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseSuccessDTO<AuthGetKakaoTokenDTO> getTokenAndUserInfo(String authorization_code, String redirect_base) throws IOException {
        String access_Token = "";
        String refresh_Token = "";
        AuthGetKakaoTokenDTO responseDto = new AuthGetKakaoTokenDTO();
        HashMap<String, Object> resultMap = new HashMap<>();

        String requestURL = get_token_url;

        URL url = new URL(requestURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        StringBuilder sb = new StringBuilder();

        sb.append("grant_type=authorization_code");
        sb.append("&client_id=" + client_id); // TODO REST_API_KEY 입력
        sb.append("&redirect_uri=" + redirect_base); // TODO 인가코드 받은 redirect_uri 입력
        sb.append("&prompt=login");
        sb.append("&code=" + authorization_code);
        bw.write(sb.toString());
        bw.flush();
        //인가코드로 토큰 받아오기
        log.info(sb.toString());
        //결과 코드가 200이라면 성공
        int responseCode = connection.getResponseCode();
        log.info("get_token_res_code : {}", responseCode);

        if (responseCode == 400) {
            //Error
            throw new NotAuthorizedUserException("인가 코드로 토큰 받는 과정에서 오류");
        }
        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }

        //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        access_Token = element.getAsJsonObject().get("access_token").getAsString();
        refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

        log.info("access_token : {}", access_Token);
        log.info("refresh_token : {}", refresh_Token);

        //access-token을 파싱 하여 카카오 id가 디비에 있는지 확인
        String user_id = httpUtil.parseToken(access_Token);
        log.info("parse result : {}", user_id);

        if (user_id == null) { //카카오에서 에러 래요
            log.error("유효하지 않은 토큰");
            throw new BadVariableRequestException("유효 하지 않은 토큰입니다.");

        } else { //DB에 회원정보가 없으면 insert하고 리턴
            String url2 = "https://kapi.kakao.com/v1/oidc/userinfo";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBearerAuth(access_Token);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<OpenIdResponseDto> response = restTemplate.exchange(url2, HttpMethod.GET, request, OpenIdResponseDto.class);

            OpenIdResponseDto openIdResponseDto = response.getBody();
            String picture = openIdResponseDto.getPicture();
            picture = picture.replace("http", "https");
            User user = User.builder()
                    .userId(openIdResponseDto.getSub())
                    .userEmail(openIdResponseDto.getEmail())
                    .userImage(picture)
                    .userName(openIdResponseDto.getNickname())
                    .build();
            Optional<User> byId = userRepository.findById(user_id);

            userRepository.save(user);
        }
        AuthGetKakaoTokenDTO dto = new AuthGetKakaoTokenDTO();
        ResponseSuccessDTO<AuthGetKakaoTokenDTO> res = responseUtil.successResponse(dto, HttpStatus.OK);
        dto.setAccessToken(access_Token);

        return res;
    }


    @Transactional
    public ResponseSuccessDTO<AuthGetKakaoTokenDTO> getJwtToken(String accessToken) {
        if (accessToken == null) {
            throw new EntityNullException("access_token이 NULL 입니다");

        }

        log.info("access_token : {}", accessToken);


        //access-token을 파싱 하여 카카오 id가 디비에 있는지 확인
        String userId = httpUtil.parseToken(accessToken);
        log.info("parse result : {}", userId);

        AuthGetKakaoTokenDTO dto = new AuthGetKakaoTokenDTO();
        User user = new User();
        if (userId == null) { // 카카오에서 에러 발생
            log.error("유효하지 않은 토큰");
            throw new BadVariableRequestException("유효 하지 않은 토큰입니다.");

        } else { //DB에 회원정보있어? 없으면 insert하고 리턴
            String kakaoUserInfoURL = "https://kapi.kakao.com/v1/oidc/userinfo";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBearerAuth(accessToken);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<OpenIdResponseDto> response = restTemplate.exchange(kakaoUserInfoURL, HttpMethod.GET, request, OpenIdResponseDto.class);

            OpenIdResponseDto openIdResponseDto = response.getBody();
            String picture = openIdResponseDto.getPicture();
            picture = picture.replace("http", "https");

            user = User.builder()
                    .userId(openIdResponseDto.getSub())
                    .userEmail(openIdResponseDto.getEmail())
                    .userImage(picture)
                    .userName(openIdResponseDto.getNickname())
                    .build();

            userRepository.save(user);
            String token = jwtTokenProvider.createAccessToken(accessToken, user.getUserId());
            User returnUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new EntityNullException("유저 " + userId + " 이 DB에 없습니다."));


            Token tokenDto = new Token();
            tokenDto.setTokenValue(accessToken);
            tokenDto.setUser(returnUser);

            tokenRepsository.save(tokenDto);
            dto.setAccessToken(token);
        }

        dto.setUserId(user.getUserId());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserName(user.getUserName());
        dto.setUserImage(user.getUserImage());

        ResponseSuccessDTO<AuthGetKakaoTokenDTO> res = responseUtil.successResponse(dto, HttpStatus.OK);
        return res;
    }

    public ResponseSuccessDTO<String> validateToken(String jwtToken) {
        boolean result = jwtTokenProvider.validateToken(jwtToken);
        String message = result ? "유효한 토큰입니다." : "";

        return responseUtil.successResponse(message, HttpStatus.OK);
    }


    public String getUserId(String jwtToken) {
        validateToken(jwtToken);

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody();

        String userId = claims.get("userId", String.class);

        if (userId == null) {
            throw new EntityNullException("토큰에서 userId를 찾을 수 없습니다.");
        }

        return userId;
    }


}
