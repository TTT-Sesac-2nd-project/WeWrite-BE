package site.hyundai.wewrite.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.auth.dto.AuthGetKakaoTokenDTO;
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
        String authorization_code = headers.get("authorization-code").toString();
        String redirect_base = headers.get("redirect-base").toString();
        authorization_code = authorization_code.replace("[","");
        authorization_code = authorization_code.replace("]","");
        redirect_base = redirect_base.replace("[","");
        redirect_base = redirect_base.replace("]","");
        log.info("인가코드로 토큰 요청 POST: /user/kakao, code : {}, redirect : {}",authorization_code,redirect_base);

        return ResponseEntity.ok(authService.getTokenAndUserInfo(authorization_code,redirect_base));
    }


    @PostMapping("/issue-token")
    public ResponseEntity<ResponseSuccessDTO<AuthGetKakaoTokenDTO>> getJwtToken(@RequestHeader HttpHeaders headers){
        String access_token = headers.get("access-token").toString();
        access_token= access_token.replace("[","");
        access_token= access_token.replace("]","");
        log.info("유저 정보 요청 POST: /user/issue-token, access-token : {}",access_token);

        return ResponseEntity.ok(authService.getJwtToken(access_token));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ResponseSuccessDTO<String>> validateToken(@RequestHeader HttpHeaders headers){
        String jwtToken = headers.get("token").toString();

        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        
        return ResponseEntity.ok(authService.validateToken(jwtToken));
    }
}

