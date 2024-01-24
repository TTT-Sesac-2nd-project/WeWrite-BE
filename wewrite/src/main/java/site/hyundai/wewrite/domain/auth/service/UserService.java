package site.hyundai.wewrite.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.auth.dto.response.UserResponseDTO;
import site.hyundai.wewrite.domain.auth.repository.UserRepository;
import site.hyundai.wewrite.domain.entity.User;
import site.hyundai.wewrite.domain.group.dto.response.GroupResponseDTO;
import site.hyundai.wewrite.domain.group.service.GroupService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.util.ResponseUtil;

import java.util.List;

/**
 * @author 김동욱
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final ResponseUtil responseUtil;
    private final GroupService groupService;
    private final UserRepository userRepository;
    private final GetUserService getUserService;

    // 유저 정보 조회
    @Transactional(readOnly = true)
    public ResponseSuccessDTO<UserResponseDTO> getUserInfo(String userId) {
        User user = getUserService.getUserByUserId(userId);
        // 내 그룹 갯수 조회
        ResponseSuccessDTO<List<GroupResponseDTO>> myGroupsResponse = groupService.getMyGroups(userId);
        List<GroupResponseDTO> myGroups = myGroupsResponse.getData();
        int numberOfMyGroups = myGroups != null ? myGroups.size() : 0;

        UserResponseDTO userResponseDTO = new UserResponseDTO(user, numberOfMyGroups);
        return responseUtil.successResponse(userResponseDTO, HttpStatus.OK);
    }

    // 유저 이름 변경
    @Transactional
    public ResponseSuccessDTO<String> updateUserName(String userName, String userId) {
        User user = getUserService.getUserByUserId(userId);
        user.setUserName(userName);
        userRepository.save(user);
        return responseUtil.successResponse("유저 이름 변경 완료", HttpStatus.OK);
    }
}
