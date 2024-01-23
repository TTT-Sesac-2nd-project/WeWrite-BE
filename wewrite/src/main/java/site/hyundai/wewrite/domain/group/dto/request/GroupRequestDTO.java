package site.hyundai.wewrite.domain.group.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 이소민
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequestDTO {
    private String groupName;
    private String groupCode;
    List<MultipartFile> groupImage;
}
