package site.hyundai.wewrite.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.board.repository.BoardRepository;
import site.hyundai.wewrite.domain.entity.Board;

@Service
@RequiredArgsConstructor
public class GetBoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
    }
}
