package site.hyundai.wewrite.domain.board.controller;

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
import site.hyundai.wewrite.domain.board.dto.request.BoardModifyRequestDTO;
import site.hyundai.wewrite.domain.board.dto.request.BoardPostRequestDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardListGetResponseDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardPostResponseDTO;
import site.hyundai.wewrite.domain.board.service.BoardService;
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


//   @GetMapping("/groups/{groupId}")
//   public ResponseEntity<ResponseSuccessDTO<BoardListGetResponseDTO>> getBoard(@PathVariable("groupsId") Long groupsId,
//                                                                               @ApiIgnore HttpSession session, @ApiIgnore HttpHeaders headers) {
//       String jwtToken = headers.get("token").toString();
//       jwtToken= jwtToken.replace("[","");
//       jwtToken= jwtToken.replace("]","");
//       String userId = authService.getUserId(jwtToken); //userId 가져와짐
//
//       log.info(": /groups/{groupId}" + userId);
//
//
//       //String userId = "4";
//       return ResponseEntity.ok(boardService.getBoard(boardNo, userId));
//
//   }

    @PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<ResponseSuccessDTO<BoardListGetResponseDTO>> getBoardList(@RequestHeader HttpHeaders headers, @PathVariable Long groupId) {

        String jwtToken = headers.get("token").toString();
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.getBoardList(userId, groupId));
    }
    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseSuccessDTO<BoardDTO>> getBoardId(@RequestHeader HttpHeaders headers,@PathVariable(value = "boardId") Long boardId) {

        String jwtToken = headers.get("token").toString();
        jwtToken= jwtToken.replace("[","");
        jwtToken= jwtToken.replace("]","");
        String userId = authService.getUserId(jwtToken); //userId 가져와짐

        return ResponseEntity.ok(boardService.getOneBoard(userId, boardId));
    }
    @PutMapping("/{boardId}")
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

}
