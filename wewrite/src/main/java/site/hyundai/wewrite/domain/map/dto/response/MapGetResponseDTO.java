package site.hyundai.wewrite.domain.map.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MapGetResponseDTO {
    private String boardTitle;
    private String boardContent;
    private String boardCreatedDate;
    private String groupName;
    private String boardImage;
}
