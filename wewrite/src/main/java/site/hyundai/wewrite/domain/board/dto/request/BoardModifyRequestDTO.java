package site.hyundai.wewrite.domain.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardModifyRequestDTO {

    private String boardTitle;
    private String boardContent;
}
