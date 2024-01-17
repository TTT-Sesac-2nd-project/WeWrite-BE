package site.hyundai.wewrite.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.auth.dto.AuthGetKakaoTokenDTO;
import site.hyundai.wewrite.domain.auth.dto.AuthUserIdResponseDTO;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.domain.auth.service.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/kakao")
    public ResponseEntity<ResponseSuccessDTO<AuthGetKakaoTokenDTO>> getKaKaoToken(@RequestHeader HttpHeaders headers) throws IOException {
        log.info(headers.toString());
        if(headers.isEmpty()){
            log.info("비어있으면 뜹니다.");
        }
        String authorization_code = headers.get("authorization_code").toString();
        String redirect_base = headers.get("redirect_base").toString();
        authorization_code = authorization_code.replace("[","");
        authorization_code = authorization_code.replace("]","");
        redirect_base = redirect_base.replace("[","");
        redirect_base = redirect_base.replace("]","");
        log.info("인가코드로 토큰 요청 POST: /user/kakao, code : {}, redirect : {}",authorization_code,redirect_base);

        return ResponseEntity.ok(authService.getTokenAndUserInfo(authorization_code,redirect_base));
    }
    @PostMapping("/userid")
    public ResponseEntity<ResponseSuccessDTO<AuthUserIdResponseDTO>> getUserId(@RequestHeader HttpHeaders headers) throws IOException {
        String access_token = headers.get("access_token").toString();
        log.info("토큰 검증, 유저아이디 리턴 POST: /member/userid, access_token : {}",access_token);

        return ResponseEntity.ok(authService.getUserId(access_token));
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseSuccessDTO> getUserInfo(@RequestHeader HttpHeaders headers){
        String access_token = headers.get("access_token").toString();
        log.info("유저 정보 요청 GET: /member/info, access_token : {}",access_token);

        return ResponseEntity.ok(authService.getUserInfo(access_token));
    }
}

