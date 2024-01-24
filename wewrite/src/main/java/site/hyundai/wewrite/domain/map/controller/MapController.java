package site.hyundai.wewrite.domain.map.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.auth.service.AuthService;
import site.hyundai.wewrite.domain.board.service.BoardService;
import site.hyundai.wewrite.domain.map.dto.response.MapListGetResponseDTO;
import site.hyundai.wewrite.domain.map.service.MapService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;

@RestController
@RequestMapping("/map")
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"지도 관련 API"})
public class MapController {

    private final AuthService authService;
    private final BoardService boardService;
    private final MapService mapService;

    @ApiOperation(value = "전체 혹은 그룹별 지도 리스트 정보 가져오기", notes = "사용자가 가입되어 있는 그룹별로 지도 리스트를 조회합니다.")
    @GetMapping("/{groupId}")
    @ApiImplicitParam(name = "groupId", value = "groupId 를 주세요. ( 0 이면 전체그룹, 그외 숫자는 그룹별 groupId)", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<MapListGetResponseDTO>> getMapBoard(@RequestHeader HttpHeaders headers, @PathVariable(value = "groupId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        jwtToken = jwtToken.replace("[", "");
        jwtToken = jwtToken.replace("]", "");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(mapService.getMapList(userId, boardId));
    }

//    @ApiOperation(value = "지도 게시글 상세보기", notes = "지도에 나온 게시글의 대한 정보를 조회합니다.")
//    @GetMapping("/detail/{boardId}")
//    @ApiImplicitParam(name = "boardId", value = "boardId 를 주세요.", required = true, dataTypeClass = Long.class, paramType = "path")
//    public ResponseEntity<ResponseSuccessDTO<MapGetResponseDTO>> getMap(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId) {
//
//        String jwtToken = headers.get("token").toString();
//        jwtToken = jwtToken.replace("[", "");
//        jwtToken = jwtToken.replace("]", "");
//        String userId = authService.getUserId(jwtToken); //userId 가져와짐
//
//        return ResponseEntity.ok(mapService.getMap(boardId));
//    }
}
