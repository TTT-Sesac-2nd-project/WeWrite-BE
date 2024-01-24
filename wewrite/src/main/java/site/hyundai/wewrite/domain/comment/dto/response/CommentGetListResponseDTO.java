package site.hyundai.wewrite.domain.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.hyundai.wewrite.domain.comment.dto.CommentDTO;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentGetListResponseDTO {
    private List<CommentDTO> commentList;
}
