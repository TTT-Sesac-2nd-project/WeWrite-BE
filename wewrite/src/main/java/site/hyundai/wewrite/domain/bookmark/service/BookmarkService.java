package site.hyundai.wewrite.domain.bookmark.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.board.service.GetBoardService;
import site.hyundai.wewrite.domain.bookmark.dto.response.BookmarkResponseDTO;
import site.hyundai.wewrite.domain.bookmark.repository.BookmarkRepository;
import site.hyundai.wewrite.domain.entity.*;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.util.ResponseUtil;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 이소민
 */
@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final ResponseUtil responseUtil;
    private final BookmarkRepository bookmarkRepository;
    private final GetBoardService getBoardService;


    // 북마크 조회
    @Transactional(readOnly = true)
    public ResponseSuccessDTO<List<BookmarkResponseDTO>> getBookmark(User user) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        List<BookmarkResponseDTO> bookmarkDTOs = new ArrayList<>();

        for (Bookmark bookmark : bookmarks) {
            Board board = bookmark.getBoard();
            // todo : 글 추가
            BookmarkResponseDTO dto = new BookmarkResponseDTO(bookmark.getBookmarkId(), board);
            bookmarkDTOs.add(dto);
        }

        return responseUtil.successResponse(bookmarkDTOs, HttpStatus.OK);
    }


    // 북마크 등록, 삭제
    @Transactional
    public ResponseSuccessDTO<String> updateBookmark(Long boardId, User user) {
        Board board = getBoardService.getBoardById(boardId);

        Optional<Bookmark> existingBookmarkOpt = bookmarkRepository.findByBoardAndUser(board, user);

        if (existingBookmarkOpt.isPresent()) {
            Bookmark existingBookmark = existingBookmarkOpt.get();
            bookmarkRepository.delete(existingBookmark);
            return responseUtil.successResponse("북마크가 취소되었습니다.", HttpStatus.OK);
        } else {
            // 기존 데이터가 없는 경우 새로운 Bookmark 객체를 생성
            Bookmark newBookmark = new Bookmark(board, user);
            bookmarkRepository.save(newBookmark);
            return responseUtil.successResponse("북마크가 등록되었습니다.", HttpStatus.OK);
        }
    }
}