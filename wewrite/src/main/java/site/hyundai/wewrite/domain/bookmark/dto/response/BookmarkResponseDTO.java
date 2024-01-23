package site.hyundai.wewrite.domain.bookmark.dto.response;

import lombok.*;
import site.hyundai.wewrite.domain.board.dto.BoardListDTO;
import site.hyundai.wewrite.domain.entity.Board;

import java.util.List;

/**
 * @author 이소민
 */
@Getter
@Setter
@NoArgsConstructor
public class BookmarkResponseDTO {
    private Long bookmarkId;
    // todo : 그룹의 최신 글 (사진, 작성자, 그룹명, 북마크 여부, 글 제목, 위치, 댓글 수, 조회수, 작성 날짜)
    private String boardTitle;

    public BookmarkResponseDTO(Long bookmarkId, Board board) {
        this.bookmarkId = bookmarkId;
        this.boardTitle = board.getBoardTitle();
    }
}
