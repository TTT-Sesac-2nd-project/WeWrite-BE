package site.hyundai.wewrite.domain.group.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.entity.Group;
import site.hyundai.wewrite.domain.group.dto.request.GroupRequestDTO;
import site.hyundai.wewrite.domain.group.repository.GroupRepository;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.util.ResponseUtil;

import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupService {
    private final ResponseUtil responseUtil;
    private final GroupRepository groupRepository;
    // 그룹 생성
    @Transactional
    public ResponseSuccessDTO<String> createGroup(GroupRequestDTO groupRequestDTO) {
        Group group = new Group(groupRequestDTO.getGroupName(), generateRandomCode(8));
        groupRepository.save(group);
        return responseUtil.successResponse("", HttpStatus.OK);
    }

    public static String generateRandomCode(int length) {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = Base64.getUrlEncoder().encode(uuid.toString().getBytes());
        String code = new String(bytes);
        return code.substring(0, Math.min(length, code.length()));
    }

}
