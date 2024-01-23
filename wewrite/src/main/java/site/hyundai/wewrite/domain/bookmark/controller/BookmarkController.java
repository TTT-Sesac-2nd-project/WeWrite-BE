package site.hyundai.wewrite.domain.bookmark.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.hyundai.wewrite.domain.auth.service.GetUserService;
import site.hyundai.wewrite.domain.bookmark.dto.response.BookmarkResponseDTO;
import site.hyundai.wewrite.domain.bookmark.service.BookmarkService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;

import java.util.List;

/**
 * @author 이소민
 */

@Slf4j
@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final GetUserService getUserService;

    // 북마크 조회
    @GetMapping
    public ResponseEntity<ResponseSuccessDTO<List<BookmarkResponseDTO>>> getBookmark(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(bookmarkService.getBookmark(getUserService.getUserByToken(headers)));
    }

    // 북마크 등록, 삭제
    @PutMapping
    public ResponseEntity<ResponseSuccessDTO<String>> updateBookmark(@RequestParam Long boardId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(bookmarkService.updateBookmark(boardId, getUserService.getUserByToken(headers)));
    }
}
