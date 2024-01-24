package site.hyundai.wewrite.domain.group.dto.response;

import lombok.Getter;
import lombok.Setter;
import site.hyundai.wewrite.domain.board.dto.BoardListDTO;
import site.hyundai.wewrite.domain.entity.Group;

import java.util.List;

/**
 * @author 이소민
 */

@Getter
@Setter
public class GroupDetailResponseDTO {
    // 그룹 이름, 그룹 사진, 그룹 멤버 수, 초대코드
    private final String groupName;
    private final String groupCode;
    private final Long groupMemberCount;
    private final String groupImageUrl;
    private List<BoardListDTO> boardList;

    public GroupDetailResponseDTO(Group group, String groupImageUrl) {
        this.groupName = group.getGroupName();
        this.groupCode = group.getGroupCode();
        this.groupMemberCount = group.getGroupMemberCount();
        this.groupImageUrl = groupImageUrl;
    }
}
