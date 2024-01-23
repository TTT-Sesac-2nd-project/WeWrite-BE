package site.hyundai.wewrite.domain.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.hyundai.wewrite.domain.board.dto.CommentDTO;
import site.hyundai.wewrite.domain.entity.Comment;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetListResponseDTO {
    private List<CommentDTO> commentList;
}
