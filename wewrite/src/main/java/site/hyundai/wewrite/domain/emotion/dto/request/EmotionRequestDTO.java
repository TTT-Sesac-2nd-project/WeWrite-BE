package site.hyundai.wewrite.domain.emotion.dto.request;

import lombok.*;
import site.hyundai.wewrite.domain.entity.EmotionStatus;

/**
 * @author 이소민
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmotionRequestDTO {
    private EmotionStatus emotionType;
}
