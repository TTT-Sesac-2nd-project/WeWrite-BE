package site.hyundai.wewrite.domain.emotion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.board.service.GetBoardService;
import site.hyundai.wewrite.domain.emotion.dto.request.EmotionRequestDTO;
import site.hyundai.wewrite.domain.emotion.dto.response.EmotionResponseDTO;
import site.hyundai.wewrite.domain.emotion.repository.EmotionRepository;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.Emotion;
import site.hyundai.wewrite.domain.entity.EmotionStatus;
import site.hyundai.wewrite.domain.entity.User;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.util.ResponseUtil;

import java.util.Optional;

/**
 * @author 이소민
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EmotionService {
    private final ResponseUtil responseUtil;
    private final EmotionRepository emotionRepository;
    private final GetBoardService getBoardService;

    // 공감 조회
    @Transactional(readOnly = true)
    public ResponseSuccessDTO<EmotionResponseDTO> getEmotion(Long boardId, User user) {
        Board board = getBoardService.getBoardById(boardId);
        Emotion emotion = emotionRepository.findByBoardAndUser(board, user).orElse(null);
        return responseUtil.successResponse(emotion.getEmotionType(), HttpStatus.OK);
    }

    // 공감 등록 및 수정
    @Transactional
    public ResponseSuccessDTO<String> updateEmotion(Long boardId, EmotionRequestDTO requestDTO, User user) {
        EmotionStatus emotionType = requestDTO.getEmotionType();
        Board board = getBoardService.getBoardById(boardId);

        Optional<Emotion> existingEmotionOpt = emotionRepository.findByBoardAndUser(board, user);

        if (existingEmotionOpt.isPresent()) {
            Emotion existingEmotion = existingEmotionOpt.get();
            // 만약 기존의 emotionType과 입력받은 emotionType이 같다면, 해당 데이터를 삭제
            if (existingEmotion.getEmotionType().equals(emotionType)) {
                emotionRepository.delete(existingEmotion);
                return responseUtil.successResponse("공감이 취소되었습니다.", HttpStatus.OK);
            } else {
                // emotionType이 다르면, 업데이트를 진행
                existingEmotion.setType(emotionType);
                emotionRepository.save(existingEmotion);
                return responseUtil.successResponse("공감이 업데이트되었습니다.", HttpStatus.OK);
            }
        } else {
            // 기존 데이터가 없는 경우 새로운 Emotion 객체를 생성
            Emotion newEmotion = new Emotion(board, user, emotionType);
            emotionRepository.save(newEmotion);
            return responseUtil.successResponse("공감이 등록되었습니다.", HttpStatus.OK);
        }
    }

}
