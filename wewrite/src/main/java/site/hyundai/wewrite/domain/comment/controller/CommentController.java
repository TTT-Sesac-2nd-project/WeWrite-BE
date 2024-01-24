package site.hyundai.wewrite.domain.comment.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.auth.service.AuthService;
import site.hyundai.wewrite.domain.auth.service.GetUserService;
import site.hyundai.wewrite.domain.comment.dto.CommentDTO;
import site.hyundai.wewrite.domain.comment.dto.request.CommentRequestDTO;
import site.hyundai.wewrite.domain.comment.dto.response.CommentGetListResponseDTO;
import site.hyundai.wewrite.domain.comment.service.CommentService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;

/**
 * @author 김동욱
 */
@RestController
@RequestMapping("/comment")
@Slf4j
@RequiredArgsConstructor
@Api(tags = {"게시판 댓글 API"})
public class CommentController {
    private final AuthService authService;
    private final CommentService commentService;
    private final GetUserService getUserService;

    @ApiOperation(value = "댓글 작성", notes = "게시글에 대한 댓글을 작성합니다.")
    @PostMapping("/{boardId}")
    @ApiImplicitParam(name = "boardId", value = "boardId 를 주세요 ", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<String>> addComment(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId, @RequestBody CommentRequestDTO commentDTO) {

        return ResponseEntity.ok(commentService.addComment(getUserService.getUserByToken(headers), boardId, commentDTO));
    }

    @ApiOperation(value = "댓글 리스트 가져오기", notes = "게시글에 대한 댓글 리스트를 가져옵니다.")
    @GetMapping("/{boardId}")
    @ApiImplicitParam(name = "boardId", value = "boardId 를 주세요 ", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<CommentGetListResponseDTO>> getComments(@RequestHeader HttpHeaders headers, @PathVariable(value = "boardId") Long boardId) {

        return ResponseEntity.ok(commentService.getCommentsByBoardId(getUserService.getUserByToken(headers), boardId));
    }

    @ApiOperation(value = "댓글 상세보기", notes = "댓글에 대한 상세보기를 가져옵니다.")
    @GetMapping("/detail/{commentId}")
    @ApiImplicitParam(name = "commentId", value = "commentId 를 주세요 ", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<CommentDTO>> getComment(@RequestHeader HttpHeaders headers, @PathVariable(value = "commentId") Long commentId) {
        return ResponseEntity.ok(commentService.getComment(commentId));
    }

    @ApiOperation(value = "댓글 수정하기", notes = "게시글에 대한 댓글 리스트를 가져옵니다.")
    @PutMapping("/{commentId}")
    @ApiImplicitParam(name = "commentId", value = "commentId 를 주세요 ", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<String>> modifyComment(@RequestHeader HttpHeaders headers, @RequestBody CommentRequestDTO commentDTO, @PathVariable(value = "commentId") Long commentId) {

        return ResponseEntity.ok(commentService.modifyComment(getUserService.getUserByToken(headers), commentId, commentDTO));
    }

    @ApiOperation(value = "댓글 삭제하기", notes = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    @ApiImplicitParam(name = "commentId", value = "commentId 를 주세요 ", required = true, dataTypeClass = Long.class, paramType = "path")
    public ResponseEntity<ResponseSuccessDTO<String>> deleteComment(@RequestHeader HttpHeaders headers, @PathVariable(value = "commentId") Long commentId) {


        return ResponseEntity.ok(commentService.deleteComment(getUserService.getUserByToken(headers), commentId));
    }


}
