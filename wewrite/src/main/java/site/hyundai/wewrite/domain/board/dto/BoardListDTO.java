package site.hyundai.wewrite.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO {
    private Long boardId;
    private String boardTitle;
    private String userName;
    private String boardLoc;
    private String boardImage;
    private String boardCreatedDate;
    private Long boardViewCount;
    private Long boardCommentCount;
    private String groupName;
    private boolean isBookmarked;

}
