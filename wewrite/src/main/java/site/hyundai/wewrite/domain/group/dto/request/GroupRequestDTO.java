package site.hyundai.wewrite.domain.group.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @ApiParam(value = "그룹 이름")
    private String groupName;
    @ApiParam(value = "그룹 코드")
    private String groupCode;
    @ApiParam(value = "그룹 이미지")
    List<MultipartFile> groupImage;
}
