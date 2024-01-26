package site.hyundai.wewrite.domain.group.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupCodeRequestDTO {
    @ApiParam(value = "그룹 코드")
    private String groupCode;
}
