package site.hyundai.wewrite.domain.emotion.controller;

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
    public ResponseEntity<ResponseSuccessDTO<EmotionResponseDTO>> getEmotion(@PathVariable Long boardId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(emotionService.getEmotion(boardId, getUserService.getUserByToken(headers)));
    }

    // 공감 등록, 수정, 삭제
    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseSuccessDTO<String>> updateEmotion(@PathVariable Long boardId, @RequestBody EmotionRequestDTO requestDTO, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(emotionService.updateEmotion(boardId, requestDTO, getUserService.getUserByToken(headers)));
    }

}
