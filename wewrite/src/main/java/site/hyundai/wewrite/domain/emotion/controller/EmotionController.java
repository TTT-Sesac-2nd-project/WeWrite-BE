package site.hyundai.wewrite.domain.emotion.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.auth.service.GetUserService;
import site.hyundai.wewrite.domain.emotion.dto.request.EmotionRequestDTO;
import site.hyundai.wewrite.domain.emotion.dto.response.EmotionResponseDTO;
import site.hyundai.wewrite.domain.emotion.service.EmotionService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;

/**
 * @author 이소민
 */

@Slf4j
@RestController
@RequestMapping("/emotion")
@RequiredArgsConstructor
public class EmotionController {
    private final EmotionService emotionService;
    private final GetUserService getUserService;

    // 공감 조회
    @GetMapping("/{boardId}")
    @ApiOperation(value = "공감 조회", notes = "공감 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResponseSuccessDTO<EmotionResponseDTO>> getEmotion(@PathVariable Long boardId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(emotionService.getEmotion(boardId, getUserService.getUserByToken(headers)));
    }

    // 공감 등록, 수정, 삭제
    @PutMapping("/{boardId}")
    @ApiOperation(value = "공감 등록, 수정, 삭제", notes = "공감 등록, 수정, 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResponseSuccessDTO<String>> updateEmotion(@PathVariable Long boardId, @RequestBody EmotionRequestDTO requestDTO, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(emotionService.updateEmotion(boardId, requestDTO, getUserService.getUserByToken(headers)));
    }

}
