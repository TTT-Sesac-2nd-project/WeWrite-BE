package site.hyundai.wewrite.domain.group.dto.response;

import lombok.Getter;
import site.hyundai.wewrite.domain.entity.Group;

/**
 * @author 이소민
 */

@Getter
public class GroupDetailResponseDTO {
    // 그룹 이름, 그룹 사진, 그룹 멤버 수, 초대코드
    // todo : 그룹의 최신 글 (사진, 작성자, 그룹명, 북마크 여부, 글 제목, 위치, 댓글 수, 조회수, 작성 날짜)
    private String groupName;
    private String groupCode;

    public GroupDetailResponseDTO(Group group) {
        this.groupName = group.getGroupName();
        this.groupCode = group.getGroupCode();
    }
}
