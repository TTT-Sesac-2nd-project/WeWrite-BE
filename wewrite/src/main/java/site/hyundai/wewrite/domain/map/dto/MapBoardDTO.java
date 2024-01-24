package site.hyundai.wewrite.domain.map.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapBoardDTO {
    private Long boardId;
    private String boardLat;
    private String boardLng;
    private String boardTitle;
    private String boardContent;
    private String boardCreatedDate;
    private String groupName;
    private String boardImage;


}
