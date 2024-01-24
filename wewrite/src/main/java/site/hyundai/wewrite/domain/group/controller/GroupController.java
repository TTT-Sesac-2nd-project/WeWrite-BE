package site.hyundai.wewrite.domain.group.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.hyundai.wewrite.domain.auth.service.GetUserService;
import site.hyundai.wewrite.domain.entity.Image;
import site.hyundai.wewrite.domain.group.dto.request.GroupRequestDTO;
import site.hyundai.wewrite.domain.group.dto.response.GroupDetailResponseDTO;
import site.hyundai.wewrite.domain.group.dto.response.GroupResponseDTO;
import site.hyundai.wewrite.domain.group.service.GroupService;
import site.hyundai.wewrite.domain.image.service.S3UploaderService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;

import java.util.List;

/**
 * @author 이소민
 */
@Api(tags = {"그룹 API"})
@Slf4j
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final GetUserService getUserService;
    private final S3UploaderService s3UploaderService;

    // 그룹 생성
    @PostMapping()
    @ApiOperation(value = "그룹 생성", notes = "그룹 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResponseSuccessDTO<String>> createGroup(GroupRequestDTO groupRequestDTO, @RequestHeader HttpHeaders headers) {
        List<Image> images = null;
        if(groupRequestDTO.getGroupImage() != null){
            List<MultipartFile> groupImage = groupRequestDTO.getGroupImage();
            images = s3UploaderService.uploadFiles("group", groupImage);
        }
        Image image = null;
        if (images != null) {
            image = images.get(0);
        }
        return ResponseEntity.ok(groupService.createGroup(groupRequestDTO, image, getUserService.getUserByToken(headers)));
    }

    // 그룹 페이지 조회
    @ApiOperation(value = "그룹 페이지 조회", notes = "그룹 페이지 조회")
    @GetMapping("/{groupId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "조회할 그룹 id",required = true),
            @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResponseSuccessDTO<GroupDetailResponseDTO>> getDetailGroup(@PathVariable Long groupId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(groupService.getDetailGroup(groupId, getUserService.getUserByToken(headers)));
    }

    // 내 그룹 조회
    @ApiOperation(value = "내 그룹 조회", notes = "내 그룹 조회")
    @GetMapping()
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<List<GroupResponseDTO>>> getGroups(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(groupService.getMyGroups(getUserService.getUserByToken(headers)));
    }

    // 초대코드로 그룹 가입하기
    @ApiOperation(value = "초대코드로 그룹 가입하기", notes = "초대코드로 그룹 가입하기")
    @PostMapping("/join")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<String>> joinGroup(@RequestBody GroupRequestDTO groupRequestDTO, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(groupService.joinGroup(groupRequestDTO.getGroupCode(), getUserService.getUserByToken(headers)));
    }

    // 그룹 수정
    @ApiOperation(value = "그룹 수정", notes = "그룹 수정")
    @PatchMapping("/{groupId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "수정할 그룹 id",required = true),
            @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResponseSuccessDTO<String>> updateGroup(@PathVariable Long groupId, GroupRequestDTO groupRequestDTO, @RequestHeader HttpHeaders headers) {
        List<Image> images = null;
        if(groupRequestDTO.getGroupImage() != null){
            List<MultipartFile> groupImage = groupRequestDTO.getGroupImage();
            images = s3UploaderService.uploadFiles("group", groupImage);
        }
        Image image = null;
        if (images != null) {
            image = images.get(0);
        }
        return ResponseEntity.ok(groupService.updateGroup(groupId, groupRequestDTO, image, getUserService.getUserByToken(headers)));
    }


    // 그룹 삭제
    @ApiOperation(value = "그룹 삭제", notes = "그룹 삭제")
    @DeleteMapping("/{groupId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "삭제할 그룹 id",required = true),
            @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResponseSuccessDTO<String>> deleteGroup(@PathVariable Long groupId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(groupService.deleteGroup(groupId, getUserService.getUserByToken(headers)));
    }

    // 그룹 탈퇴
    @ApiOperation(value = "그룹 탈퇴", notes = "그룹 탈퇴")
    @DeleteMapping("/leave/{groupId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", value = "탈퇴할 그룹 id",required = true),
            @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    })
    public ResponseEntity<ResponseSuccessDTO<String>> leaveGroup(@PathVariable Long groupId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(groupService.leaveGroup(groupId, getUserService.getUserByToken(headers)));
    }
}
