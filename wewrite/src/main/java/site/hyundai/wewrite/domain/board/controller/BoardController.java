package site.hyundai.wewrite.domain.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.hyundai.wewrite.domain.auth.service.AuthService;
import site.hyundai.wewrite.domain.board.dto.BoardDTO;
import site.hyundai.wewrite.domain.board.dto.request.BoardModifyRequestDTO;
import site.hyundai.wewrite.domain.board.dto.request.BoardPostRequestDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardListGetResponseDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardPostResponseDTO;
import site.hyundai.wewrite.domain.board.service.BoardService;
import site.hyundai.wewrite.domain.comment.service.CommentService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.UnAuthorizedException;

import java.util.List;

@RestController
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"게시판 API"})
public class BoardController {

    private final AuthService authService;
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성합니다.")
    @ApiImplicitParam(name = "boardDTO", value = "boardDTO 를 담아주세요", required = true, dataTypeClass = BoardDTO.class, paramType = "string")
    public ResponseEntity<ResponseSuccessDTO<BoardPostResponseDTO>> addBoard(@RequestHeader HttpHeaders headers, @RequestPart(value = "multipartFiles", required = false) List<MultipartFile> multipartFiles,
                                                                             @RequestPart(value = "boardDTO", required = true) BoardPostRequestDTO boardDTO) {
        String jwtToken = headers.get("token").toString();
        jwtToken = jwtToken.replace("[", "");
        jwtToken = jwtToken.replace("]", "");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        log.info(": /" + userId);

        return ResponseEntity.ok(boardService.addBoard(userId, boardDTO, multipartFiles));
    }

    @ApiOperation(value = "전체 혹은 그룹별 최신글 리스트 조회", notes = "사용자가 가입되어 있는 그룹별로 게시글 리스트를 조회합니다.")
    @GetMapping("/groups/{groupId}")
    @ApiImplicitParam(name = "groupId", value = "groupId 를 주세요. ( 0 이면 전체그룹, 그외 숫자는 그룹별 groupId)", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<BoardListGetResponseDTO>> getBoardList(@RequestHeader HttpHeaders headers, @PathVariable Long groupId) {

        String jwtToken = headers.get("token").toString();
        jwtToken = jwtToken.replace("[", "");
        jwtToken = jwtToken.replace("]", "");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.getBoardList(userId, groupId));
    }

    @ApiOperation(value = "게시글 하나 조회", notes = "게시글 하나를 조회합니다.")
    @GetMapping("/{boardId}")

    @ApiImplicitParam(name = "boardId", value = "boardId 를 주세요 ", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<BoardDTO>> getBoard(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        jwtToken = jwtToken.replace("[", "");
        jwtToken = jwtToken.replace("]", "");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐
        //  String userId = headers.get("userId").toString();
        return ResponseEntity.ok(boardService.getOneBoard(userId, boardId));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PutMapping("/{boardId}")

    @ApiImplicitParam(name = "boardId", value = "boardId 를 주세요 ", required = true, dataTypeClass = Long.class, paramType = "path")


    public ResponseEntity<ResponseSuccessDTO<String>> modifyBoard(@RequestHeader HttpHeaders headers, @RequestPart BoardModifyRequestDTO boardDTO, @PathVariable(value = "boardId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        if (jwtToken == null) {
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }
        jwtToken = jwtToken.replace("[", "");
        jwtToken = jwtToken.replace("]", "");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.modifyBoard(boardDTO, boardId));
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/{boardId}")

    @ApiImplicitParam(name = "boardId", value = "boardId 를 주세요 ", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<String>> deleteBoard(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        if (jwtToken == null) {
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }
        jwtToken = jwtToken.replace("[", "");
        jwtToken = jwtToken.replace("]", "");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.deleteBoard(boardId));
    }


}
