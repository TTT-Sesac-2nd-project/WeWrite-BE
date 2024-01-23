package site.hyundai.wewrite.domain.board.controller;

import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.hyundai.wewrite.domain.auth.dto.AuthGetKakaoTokenDTO;
import site.hyundai.wewrite.domain.auth.service.AuthService;
import site.hyundai.wewrite.domain.board.dto.BoardDTO;
import site.hyundai.wewrite.domain.board.dto.BoardListDTO;
import site.hyundai.wewrite.domain.board.dto.CommentDTO;
import site.hyundai.wewrite.domain.board.dto.request.BoardModifyRequestDTO;
import site.hyundai.wewrite.domain.board.dto.request.BoardPostRequestDTO;
import site.hyundai.wewrite.domain.board.dto.request.CommentPostRequestDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardListGetResponseDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardPostResponseDTO;
import site.hyundai.wewrite.domain.board.dto.response.CommentGetListResponseDTO;
import site.hyundai.wewrite.domain.board.service.BoardService;
import site.hyundai.wewrite.domain.board.service.CommentService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;
import site.hyundai.wewrite.global.exeception.service.UnAuthorizedException;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final AuthService authService;
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<BoardPostResponseDTO>> addBoard(@RequestHeader HttpHeaders headers, @RequestPart(value="multipartFiles", required = false)List<MultipartFile> multipartFiles,
                                                                             @RequestPart(value="boardDTO",required = true) BoardPostRequestDTO boardDTO) {

        String jwtToken = headers.get("token").toString();
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        log.info(": /" + userId);

    return ResponseEntity.ok(boardService.addBoard(userId, boardDTO,multipartFiles));
    }
    @GetMapping("/groups/{groupId}")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<BoardListGetResponseDTO>> getBoardList(@RequestHeader HttpHeaders headers, @PathVariable Long groupId) {

        String jwtToken = headers.get("token").toString();
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.getBoardList(userId, groupId));
    }
    @GetMapping("/{boardId}")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<BoardDTO>> getBoard(@RequestHeader HttpHeaders headers,@PathVariable(value = "boardId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.getOneBoard(userId, boardId));
    }
    @PutMapping("/{boardId}")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<String>> modifyBoard(@RequestHeader HttpHeaders headers, @RequestPart BoardModifyRequestDTO boardDTO , @PathVariable(value = "boardId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        if(jwtToken==null){
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.modifyBoard(boardDTO, boardId));
    }

    @DeleteMapping("/{boardId}")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<String>> deleteBoard(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        if(jwtToken==null){
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.deleteBoard(boardId));
    }

    @PostMapping("/{boardId}/comment")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<String>> addComment(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId, @RequestBody CommentPostRequestDTO commentDTO){
        String jwtToken = headers.get("token").toString();
        if(jwtToken==null){
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐
        return ResponseEntity.ok(commentService.addComment(userId,boardId, commentDTO));
    }

    @GetMapping("/{boardId}/comment")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<CommentGetListResponseDTO>> getComments(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId){
        String jwtToken = headers.get("token").toString();
        if(jwtToken==null){
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐
        return ResponseEntity.ok(commentService.getCommentsByBoardId(boardId));
    }

    @PutMapping("/comment")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<String>> modifyComment(@RequestHeader HttpHeaders headers, @RequestBody CommentDTO commentDTO){
        String jwtToken = headers.get("token").toString();
        if(jwtToken==null){
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐
        return ResponseEntity.ok(commentService.modifyComment(userId, commentDTO));
    }

    @DeleteMapping("/comment/{commentId}")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<String>> deleteComment(@RequestHeader HttpHeaders headers, @PathVariable(value = "commentId") Long commentId) {

        String jwtToken = headers.get("token").toString();
        if(jwtToken==null){
            throw new UnAuthorizedException("접근 권한이 없습니다.");
        }
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(commentService.deleteComment(userId, commentId));
    }

    @GetMapping("/comment/{commentId}")
    //@ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<CommentDTO>> getComment(@RequestHeader HttpHeaders headers,@PathVariable(value="commentId") Long commentId){
    return ResponseEntity.ok(commentService.getComment(commentId));
    }




}
