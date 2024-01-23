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
public class BoardDTO {

    private String boardTitle;
    private String boardContent;
    private String userName;
    private String boardLoc;
    private List<String> boardImageList;
    private String userImage;

}
