package site.hyundai.wewrite.domain.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.hyundai.wewrite.domain.auth.dto.AuthGetKakaoTokenDTO;
import site.hyundai.wewrite.domain.auth.repository.UserRepository;
import site.hyundai.wewrite.domain.board.dto.request.BoardPostRequestDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardPostResponseDTO;
import site.hyundai.wewrite.domain.board.repository.BoardImageRepository;
import site.hyundai.wewrite.domain.board.repository.BoardRepository;
import site.hyundai.wewrite.domain.entity.*;
import site.hyundai.wewrite.domain.group.repository.GroupRepository;
import site.hyundai.wewrite.domain.image.service.S3UploaderService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;
import site.hyundai.wewrite.global.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final S3UploaderService uploaderService;
    private final ResponseUtil responseUtil;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final BoardImageRepository boardImageRepository;


    public ResponseSuccessDTO<BoardPostResponseDTO> addBoard(String userId, BoardPostRequestDTO boardDTO , List<MultipartFile> multipartFiles) {
        if(userId==null){
            throw new EntityNullException("유저 정보가 없습니다.");
        }
        log.info(boardDTO.getGroupId()+"");
        Group group = groupRepository.findById(boardDTO.getGroupId()).get();

        User user = userRepository.findById(userId).get();
        // 보드 이미지 추가 로직 추가해야함
//        Board board = Board.builder()
//                .boardTitle(boardDTO.getBoardTitle())
//                .boardLoc(boardDTO.getBoardLoc())
//                .boardContent(boardDTO.getBoardContent())
//                .boardCreatedDate(boardDTO.getBoardCreatedDate())
//                // 날짜 추가 로직 추가해야함
//                .group(group)
//                .user(user)
//                .build();

        Board board = new Board();
        board.setBoardTitle(boardDTO.getBoardTitle());
        board.setBoardLoc(boardDTO.getBoardLoc());
        board.setBoardContent(boardDTO.getBoardContent());
        board.setGroup(group);
        board.setUser(user);
        board.setBoardLat(boardDTO.getBoardLat());
        board.setBoardLong(boardDTO.getBoardLng());
        board.setBoardView(0L);
        boardRepository.save(board);
        List<Image> imageList = uploaderService.uploadFiles("board",multipartFiles);

        for(Image i : imageList){
            BoardImage boardImage = BoardImage.builder()
                    .board(board)
                    .image(i)
                    .build();
            boardImageRepository.save(boardImage);
        }

        ResponseSuccessDTO<BoardPostResponseDTO> res = responseUtil.successResponse("게시글 등록 성공", HttpStatus.OK);
        return res;
    }

}
