package site.hyundai.wewrite.domain.group.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.group.dto.request.GroupRequestDTO;
import site.hyundai.wewrite.domain.group.service.GroupService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;

@RestController
@RequestMapping("/group")
@Slf4j
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping()
    public ResponseEntity<ResponseSuccessDTO<String>> createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        return ResponseEntity.ok(groupService.createGroup(groupRequestDTO));
    }
}
