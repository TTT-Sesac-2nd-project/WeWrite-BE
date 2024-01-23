package site.hyundai.wewrite.domain.map.controller;

import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.auth.service.AuthService;
import site.hyundai.wewrite.domain.board.dto.BoardDTO;
import site.hyundai.wewrite.domain.board.service.BoardService;
import site.hyundai.wewrite.domain.board.service.CommentService;
import site.hyundai.wewrite.domain.map.dto.response.MapBoardGetResponseDTO;
import site.hyundai.wewrite.domain.map.service.MapService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;

@RestController
@RequestMapping("/map")
@Slf4j
@RequiredArgsConstructor
public class MapController {

    private final AuthService authService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final MapService mapService;

//    @GetMapping("/{groupId}")
//    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
//    public ResponseEntity<ResponseSuccessDTO<BoardDTO>> getMapBoard(@RequestHeader HttpHeaders headers, @PathVariable(value = "groupId") Long boardId) {
//
//        String jwtToken = headers.get("token").toString();
//        jwtToken= jwtToken.replace("[","");
//        jwtToken= jwtToken.replace("]","");
//        String userId = authService.getUserId(jwtToken); //userId 가져와짐
//
//        //return ResponseEntity.ok(boardService.getOneBoard(userId, boardId));
//    }

    @GetMapping("/detail/{boardId}")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<MapBoardGetResponseDTO>> getMap(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(mapService.getMap(boardId));
    }
}
