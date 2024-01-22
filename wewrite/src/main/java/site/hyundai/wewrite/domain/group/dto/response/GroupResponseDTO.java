package site.hyundai.wewrite.domain.group.dto.response;

import lombok.Getter;
import site.hyundai.wewrite.domain.entity.Group;

/**
 * @author 이소민
 */

@Getter
public class GroupResponseDTO {
    // 그룹 이미지, 그룹 이름, 그룹 코드
    private final Long groupId;
    private final String groupName;
    private final String groupCode;
    private final String groupImageUrl;
    private final Long groupMemberCount;

    public GroupResponseDTO(Group group, String groupImageUrl) {
        this.groupId = group.getGroupId();
        this.groupName = group.getGroupName();
        this.groupCode = group.getGroupCode();
        this.groupMemberCount = group.getGroupMemberCount();
        this.groupImageUrl = groupImageUrl;
    }
}
