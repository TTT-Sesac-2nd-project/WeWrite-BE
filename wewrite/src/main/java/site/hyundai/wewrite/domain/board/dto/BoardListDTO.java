package site.hyundai.wewrite.domain.board.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
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
    private String userImage;
    private boolean isBookmarked;
}

