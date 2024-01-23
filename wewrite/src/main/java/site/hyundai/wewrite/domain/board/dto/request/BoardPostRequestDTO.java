package site.hyundai.wewrite.domain.board.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardPostRequestDTO {
    private String boardTitle;
    private String boardLoc;
    private String boardContent;
    private Long groupId;
    private String boardCreatedDate;

    private String boardLat;
    private String boardLng;

    List<MultipartFile> boardImage;

}
