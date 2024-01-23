package site.hyundai.wewrite.domain.emotion.dto.response;

import lombok.Getter;
import site.hyundai.wewrite.domain.entity.EmotionStatus;

/**
 * @author 이소민
 */
@Getter
public class EmotionResponseDTO {
    private EmotionStatus emotionType;
}
