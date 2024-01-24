package site.hyundai.wewrite.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.auth.repository.UserRepository;
import site.hyundai.wewrite.domain.board.repository.BoardRepository;
import site.hyundai.wewrite.domain.comment.dto.CommentDTO;
import site.hyundai.wewrite.domain.comment.dto.request.CommentRequestDTO;
import site.hyundai.wewrite.domain.comment.dto.response.CommentGetListResponseDTO;
import site.hyundai.wewrite.domain.comment.repository.CommentRepository;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.Comment;
import site.hyundai.wewrite.domain.entity.User;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.BadVariableRequestException;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;
import site.hyundai.wewrite.global.exeception.service.UnAuthorizedException;
import site.hyundai.wewrite.global.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CommentService {

    private final ResponseUtil responseUtil;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseSuccessDTO<String> addComment(String userId, Long boardId, CommentRequestDTO commentDTO) {
        if (boardId == null) {
            throw new BadVariableRequestException("boardId 가 NULL입니다.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNullException("유저가 DB에 없습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new EntityNullException("게시물이 DB에 없습니다."));
        Comment comment = Comment.builder()
                .board(board)
                .user(user)
                .commentContent(commentDTO.getCommentContent())
                .build();
        Long commentId = commentRepository.save(comment).getCommentId();

        ResponseSuccessDTO<String> res = responseUtil.successResponse(boardId + "번 게시글에 " + commentId + "번 댓글이 잘 등록되었습니다.", HttpStatus.OK);
        return res;
    }

    public ResponseSuccessDTO<CommentGetListResponseDTO> getCommentsByBoardId(Long boardId) {
        if (boardId == null) {
            throw new BadVariableRequestException("boardId 가 NULL입니다.");
        }
        List<Comment> commentList = commentRepository.getCommentsByBoardId(boardId);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment c : commentList) {
            CommentDTO commentDTO = CommentDTO.builder()
                    .commentId(c.getCommentId())
                    .commentContent(c.getCommentContent()).build();

            commentDTOList.add(commentDTO);
        }
        CommentGetListResponseDTO dto = CommentGetListResponseDTO.builder()
                .commentList(commentDTOList).build();

        ResponseSuccessDTO<CommentGetListResponseDTO> res = responseUtil.successResponse(dto, HttpStatus.OK);
        return res;

    }

    @Transactional
    public ResponseSuccessDTO<String> modifyComment(String userId, Long commentId, CommentRequestDTO commentDTO) {
        if (commentId == null) {
            throw new BadVariableRequestException("commentId 가 NULL입니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNullException("댓글이 DB에 없습니다."));
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnAuthorizedException("댓글 글쓴이만 수정이 가능합니다.");
        }
        comment.setCommentContent(commentDTO.getCommentContent());
        ResponseSuccessDTO<String> res = responseUtil.successResponse(comment.getCommentId() + "번 댓글 수정 완료", HttpStatus.OK);
        return res;
    }

    @Transactional
    public ResponseSuccessDTO<String> deleteComment(String userId, Long commentId) {
        if (commentId == null) {
            throw new EntityNullException("댓글 ID 가 없습니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNullException("댓글이 DB에 없습니다."));
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new UnAuthorizedException("댓글 글쓴이만 삭제가 가능합니다.");
        }
        commentRepository.deleteById(commentId);
        ResponseSuccessDTO<String> res = responseUtil.successResponse("댓글" + commentId + " 번 삭제 성공", HttpStatus.OK);
        return res;
    }

    public ResponseSuccessDTO<CommentDTO> getComment(Long commentId) {
        if (commentId == null) {
            throw new EntityNullException("댓글 ID 가 없습니다.");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNullException("댓글이 DB에 없습니다."));
        CommentDTO commentDTO = CommentDTO.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent()).build();
        ResponseSuccessDTO<CommentDTO> res = responseUtil.successResponse(commentDTO, HttpStatus.OK);

        return res;
    }
}
