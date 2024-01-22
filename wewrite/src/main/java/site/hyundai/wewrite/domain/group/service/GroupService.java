package site.hyundai.wewrite.domain.group.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.entity.*;
import site.hyundai.wewrite.domain.group.dto.request.GroupRequestDTO;
import site.hyundai.wewrite.domain.group.dto.response.GroupDetailResponseDTO;
import site.hyundai.wewrite.domain.group.dto.response.GroupResponseDTO;
import site.hyundai.wewrite.domain.group.repository.GroupImageRepository;
import site.hyundai.wewrite.domain.group.repository.GroupRepository;
import site.hyundai.wewrite.domain.group.repository.UserGroupRepository;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.DefaultException;
import site.hyundai.wewrite.global.exeception.service.UnAuthorizedException;
import site.hyundai.wewrite.global.util.ResponseUtil;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @author 이소민
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final ResponseUtil responseUtil;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupImageRepository groupImageRepository;

    // 그룹 생성
    @Transactional
    public ResponseSuccessDTO<String> createGroup(GroupRequestDTO groupRequestDTO, Image image, User user) {
        // group 저장
        Group group = new Group(groupRequestDTO.getGroupName(), generateRandomCode(8));
        groupRepository.save(group);

        // user_group 저장
        UserGroup userGroup = new UserGroup();
        userGroup.setUser(user);
        userGroup.setGroup(group);
        userGroupRepository.save(userGroup);

        // group_image 저장
        if(image != null){
            groupImageRepository.save(new GroupImage(group, image));
        }

        return responseUtil.successResponse("", HttpStatus.OK);
    }

    // 그룹 페이지 조회
    @Transactional
    public ResponseSuccessDTO<GroupDetailResponseDTO> getDetailGroup(Long groupId, User user) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new DefaultException("해당 그룹이 없습니다. id=" + groupId));
        checkRole(group, user);
        GroupDetailResponseDTO groupDetailResponseDTO = new GroupDetailResponseDTO(group);
        return responseUtil.successResponse(groupDetailResponseDTO, HttpStatus.OK);
    }

    // 내 그룹 조회
    @Transactional
    public ResponseSuccessDTO<List<GroupResponseDTO>> getMyGroups(User user) {
        List<UserGroup> userGroups = userGroupRepository.findByUser(user);

        List<GroupResponseDTO> myGroups = new ArrayList<>();
        for(UserGroup userGroup : userGroups) {
            Group group = groupRepository.findById(userGroup.getGroup().getGroupId()).orElseThrow(() -> new IllegalArgumentException("해당 그룹이 존재하지 않습니다."));
            myGroups.add(new GroupResponseDTO(group));
        }
        return responseUtil.successResponse(myGroups, HttpStatus.OK);
    }

    // 초대코드 생성 (8자리)
    public static String generateRandomCode(int length) {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = Base64.getUrlEncoder().encode(uuid.toString().getBytes());
        String code = new String(bytes);
        return code.substring(0, Math.min(length, code.length()));
    }

    // checkRole 권한 확인
    @Transactional
    public void checkRole(Group group, User user){
        userGroupRepository.findByGroupAndUser(group, user).orElseThrow(
                () -> new UnAuthorizedException("해당 그룹에 권한이 없습니다.")
        );
    }
}
