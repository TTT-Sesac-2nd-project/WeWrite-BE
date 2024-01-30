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
        log.info("인가코드로 토큰 요청 POST: /user/kakao, code : {}, redirect : {}", getHeaderValue(headers, "authorization-code"), getHeaderValue(headers, "redirect-base"));
        return ResponseEntity.ok(authService.getTokenAndUserInfo(getHeaderValue(headers, "authorization-code"), getHeaderValue(headers, "redirect-base")));
    }

    @PostMapping("/issue-token")
    @ApiImplicitParam(name = "access-token", value = "access-token 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<AuthGetKakaoTokenDTO>> getJwtToken(@RequestHeader HttpHeaders headers) {
        String access_token = getHeaderValue(headers, "access-token");
        log.info("유저 정보 요청 POST: /user/issue-token, access-token : {}", access_token);
        return ResponseEntity.ok(authService.getJwtToken(access_token));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ResponseSuccessDTO<String>> validateToken(@RequestHeader HttpHeaders headers) {
        String jwtToken = getHeaderValue(headers, "token");
        return ResponseEntity.ok(authService.validateToken(jwtToken));
    }

    @ApiOperation(value = "사용자 정보 가져오기", notes = "토큰으로 사용자 정보를 발급해줍니다.")
    @GetMapping
    public ResponseEntity<ResponseSuccessDTO<UserResponseDTO>> getUserInfo(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(userService.getUserInfo(getUserService.getUserByToken(headers)));
    }

    @ApiOperation(value = "사용자 이름 변경", notes = "사용자 이름을 변경합니다.")
    @PutMapping("/modify/userName")
    public ResponseEntity<ResponseSuccessDTO<String>> updateUserName(@RequestBody UserRequestDTO requestDTO, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(userService.updateUserName(requestDTO.getUserName(), getUserService.getUserByToken(headers)));
    }

    // header 에서 key 에 해당하는 value 를 가져옵니다.
    private String getHeaderValue(HttpHeaders headers, String key) {
        return headers.getFirst(key);
    }
}


