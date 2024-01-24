package site.hyundai.wewrite.domain.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.auth.dto.AuthGetKakaoTokenDTO;
import site.hyundai.wewrite.domain.auth.dto.request.UserRequestDTO;
import site.hyundai.wewrite.domain.auth.dto.response.UserResponseDTO;
import site.hyundai.wewrite.domain.auth.service.AuthService;
import site.hyundai.wewrite.domain.auth.service.GetUserService;
import site.hyundai.wewrite.domain.auth.service.UserService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.UnAuthorizedException;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"로그인 관련 API"})
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final GetUserService getUserService;


    @ApiOperation(value = "카카오에게 access-token 토큰 요청", notes = "카카오 서버에 토큰을 요청합니다.(백엔드 토큰 발급용)")
    @PostMapping("/kakao")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization-code", value = "카카오 인가코드", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "redirect-base", value = "리다이렉트 URI", required = true, dataType = "string", paramType = "header")

    })
    public ResponseEntity<ResponseSuccessDTO<AuthGetKakaoTokenDTO>> getKaKaoToken(@RequestHeader HttpHeaders headers) throws IOException {
        log.info(headers.toString());
        if (headers.isEmpty()) {
            log.info("비어있으면 뜹니다.");
        }
        String authorization_code = headers.get("authorization-code").toString();
        String redirect_base = headers.get("redirect-base").toString();
        authorization_code = authorization_code.replace("[", "");
        authorization_code = authorization_code.replace("]", "");
        redirect_base = redirect_base.replace("[", "");
        redirect_base = redirect_base.replace("]", "");
        log.info("인가코드로 토큰 요청 POST: /user/kakao, code : {}, redirect : {}", authorization_code, redirect_base);

        return ResponseEntity.ok(authService.getTokenAndUserInfo(authorization_code, redirect_base));
    }


    @PostMapping("/issue-token")
    @ApiImplicitParam(name = "access-token", value = "access-token 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<AuthGetKakaoTokenDTO>> getJwtToken(@RequestHeader HttpHeaders headers) {
        if (headers.get("access-token").isEmpty()) {
            throw new UnAuthorizedException("access-token 이 없습니다.");
        }
        String access_token = headers.get("access-token").toString();
        access_token = access_token.replace("[", "");
        access_token = access_token.replace("]", "");
        log.info("유저 정보 요청 POST: /user/issue-token, access-token : {}", access_token);

        return ResponseEntity.ok(authService.getJwtToken(access_token));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ResponseSuccessDTO<String>> validateToken(@RequestHeader HttpHeaders headers) {
        String jwtToken = headers.get("token").toString();

        jwtToken = jwtToken.replace("[", "");
        jwtToken = jwtToken.replace("]", "");

        return ResponseEntity.ok(authService.validateToken(jwtToken));
    }

    // 유저 정보 조회
    @ApiOperation(value = "사용자 정보 가져오기", notes = "토큰으로 사용자 정보를 발급해줍니다.")
    @GetMapping
    public ResponseEntity<ResponseSuccessDTO<UserResponseDTO>> getUserInfo(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(userService.getUserInfo(getUserService.getUserByToken(headers)));
    }

    // 유저 네임 변경
    @ApiOperation(value = "사용자 이름 변경", notes = "사용자 이름을 변경합니다.")
    @PutMapping("/modify/userName")
    public ResponseEntity<ResponseSuccessDTO<String>> updateUserName(@RequestBody UserRequestDTO requestDTO, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(userService.updateUserName(requestDTO.getUserName(), getUserService.getUserByToken(headers)));
    }

}

