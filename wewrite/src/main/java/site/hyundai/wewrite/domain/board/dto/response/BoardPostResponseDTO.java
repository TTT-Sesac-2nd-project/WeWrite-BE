package site.hyundai.wewrite.domain.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardPostResponseDTO {
    private String boardTitle;
    private String boardLoc;
    private String boardContent;
    private String groupId;
    private String boardCreatedDate;

}
