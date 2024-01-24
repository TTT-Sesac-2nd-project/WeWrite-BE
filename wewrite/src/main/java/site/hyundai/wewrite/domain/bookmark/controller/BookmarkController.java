package site.hyundai.wewrite.domain.bookmark.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"북마크 API"})
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final GetUserService getUserService;

    // 북마크 조회
    @GetMapping
    @ApiOperation(value = "북마크 조회", notes = "북마크 조회")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<List<BookmarkResponseDTO>>> getBookmark(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(bookmarkService.getBookmark(getUserService.getUserByToken(headers)));
    }

    // 북마크 등록, 삭제
    @PutMapping("/{boardId}")
    @ApiOperation(value = "북마크 등록, 삭제", notes = "북마크 등록, 삭제")
    @ApiImplicitParam(name = "token", value = "JWT TOKEN 을 담아주세요", required = true, dataType = "string", paramType = "header")
    public ResponseEntity<ResponseSuccessDTO<String>> updateBookmark(@PathVariable Long boardId, @RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(bookmarkService.updateBookmark(boardId, getUserService.getUserByToken(headers)));
    }
}
