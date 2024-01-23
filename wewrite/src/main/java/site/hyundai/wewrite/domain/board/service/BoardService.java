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
import site.hyundai.wewrite.domain.board.dto.BoardDTO;
import site.hyundai.wewrite.domain.board.dto.BoardListDTO;
import site.hyundai.wewrite.domain.board.dto.request.BoardModifyRequestDTO;
import site.hyundai.wewrite.domain.board.dto.request.BoardPostRequestDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardListGetResponseDTO;
import site.hyundai.wewrite.domain.board.dto.response.BoardPostResponseDTO;
import site.hyundai.wewrite.domain.board.repository.BoardImageRepository;
import site.hyundai.wewrite.domain.board.repository.BoardRepository;
import site.hyundai.wewrite.domain.board.repository.CommentRepository;
import site.hyundai.wewrite.domain.bookmark.repository.BookmarkRepository;
import site.hyundai.wewrite.domain.entity.*;
import site.hyundai.wewrite.domain.group.repository.GroupRepository;
import site.hyundai.wewrite.domain.group.repository.UserGroupRepository;
import site.hyundai.wewrite.domain.image.repository.ImageRepository;
import site.hyundai.wewrite.domain.image.service.S3UploaderService;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;
import site.hyundai.wewrite.global.util.ResponseUtil;
import site.hyundai.wewrite.global.util.TimeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardService {

    private final S3UploaderService uploaderService;
    private final ResponseUtil responseUtil;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final UserGroupRepository userGroupRepository;

    private final BoardImageRepository boardImageRepository;

    private final ImageRepository imageRepository;

    private final CommentRepository commentRepository;

    private final BookmarkRepository bookmarkRepository;

    private final TimeService timeService;

    @Transactional
    public ResponseSuccessDTO<BoardPostResponseDTO> addBoard(String userId, BoardPostRequestDTO boardDTO , List<MultipartFile> multipartFiles) {
        if(userId==null){
            throw new EntityNullException("유저 정보가 없습니다.");
        }
        log.info(boardDTO.getGroupId()+"");
        Group group = groupRepository.findById(boardDTO.getGroupId()).get();

        User user = userRepository.findById(userId).get();

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
        board.setBoardCreatedDate(boardDTO.getBoardCreatedDate());
        board.setGroup(group);
        board.setUser(user);
        board.setBoardLat(boardDTO.getBoardLat());
        board.setBoardLong(boardDTO.getBoardLng());
        board.setBoardView(0L);

        boardRepository.save(board);
        if(multipartFiles!=null) {


            List<Image> imageList = uploaderService.uploadFiles("board", multipartFiles);

            for (Image i : imageList) {
                BoardImage boardImage = BoardImage.builder()
                        .board(board)
                        .image(i)
                        .build();
                boardImageRepository.save(boardImage);
            }
        }
        ResponseSuccessDTO<BoardPostResponseDTO> res = responseUtil.successResponse("게시글 등록 성공", HttpStatus.OK);
        return res;
    }

    public ResponseSuccessDTO<BoardListGetResponseDTO> getBoardList(String userId , Long groupId){
        if(userId==null){
            throw new EntityNullException("유저 정보가 없습니다.");
        }
        List<List<Board>> totalBoardList = new ArrayList<>();
        List<Board> boardList = new ArrayList<>();

        if(groupId==0){
            List<UserGroup> userGroupList= userGroupRepository.getUserGroupsById(userId);
            if(userGroupList==null){
                throw new EntityNullException("유저가 가입한 그룹이 없습니다. 그룹에 가입하고 글을 작성해주세요");
            }
            for(UserGroup u : userGroupList){
                boardList = boardRepository.getBoardList(u.getGroup().getGroupId());
                totalBoardList.add(boardList);
            }

        }
        else {
            totalBoardList.add(boardRepository.getBoardList(groupId));
        }

       User user = userRepository.findById(userId).orElseThrow(()-> new EntityNullException("유저 정보가 없습니다."));
        List<BoardListDTO> boardListDTOList = new ArrayList<>();
        for(List<Board> bL : totalBoardList){
            for(Board b : bL) {
                Long boardImageId = boardImageRepository.findOneLatestImageByBoardId(b.getBoardId()).getImageId();
                Long commentCount = commentRepository.getCommentCountByBoardId(b.getBoardId());
                BoardListDTO boardListDTO = BoardListDTO.builder()
                        .boardId(b.getBoardId())
                        .boardTitle(b.getBoardTitle())
                        .boardCreatedDate(timeService.parseLocalDateTimeForMap(b.getBoardCreatedDate()))
                        .userName(b.getUser().getUserName())
                        .groupName(b.getGroup().getGroupName())
                        .boardCommentCount(commentCount)
                        .boardLoc(b.getBoardLoc())
                        .boardImage(imageRepository.findById(boardImageId).get().getUploadFileUrl())
                        .userName(b.getUser().getUserName())
                        .boardImage(b.getUser().getUserImage())
                        .boardViewCount(b.getBoardView())
                        .isBookmarked(bookmarkRepository.isBookmarked(userId, b.getBoardId()))
                        .build();
                boardListDTOList.add(boardListDTO);
            }
        }
        BoardListGetResponseDTO boardListGetResponseDTO = new BoardListGetResponseDTO();
        boardListGetResponseDTO.setBoardList(boardListDTOList);
        ResponseSuccessDTO<BoardListGetResponseDTO> res = responseUtil.successResponse(boardListGetResponseDTO, HttpStatus.OK);
        return res;
    }
    @Transactional
    public ResponseSuccessDTO<BoardDTO> getOneBoard(String userId, Long boardId){
    if(boardId==null){
        throw new EntityNullException("게시글 ID 가 없습니다,");
        }
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new EntityNullException("게시글 "+ boardId+" 이 DB에 없습니다."));
    // 조회수 증가
    if(!userId.equals(board.getUser().getUserId())){
        board.setBoardView(board.getBoardView()+1);
    }

    List<String> boardImageStringList = new ArrayList<>();
    List<BoardImage> boardImageList = boardImageRepository.findAllByBoardId(boardId);
    for(BoardImage i : boardImageList){
        boardImageStringList.add(i.getImage().getUploadFileUrl());
    }
    BoardDTO boardDTO = BoardDTO.builder()
            .boardTitle(board.getBoardTitle())
            .boardContent(board.getBoardContent())
            .userName(board.getUser().getUserName())
            .boardLoc(board.getBoardLoc())
            .userImage(board.getUser().getUserImage())
            .boardImageList(boardImageStringList).build();
    ResponseSuccessDTO<BoardDTO> res = responseUtil.successResponse(boardDTO, HttpStatus.OK);
    return res;
    }

    @Transactional
    public ResponseSuccessDTO<String> modifyBoard(BoardModifyRequestDTO boardDTO, Long boardId){
        if(boardId==null){
            throw new EntityNullException("게시글 ID 가 없습니다,");
        }
        Optional<Board> board= boardRepository.findById(boardId);
        if(!board.isPresent()){
            throw new EntityNullException("게시글 "+ boardId+" 이 DB에 없습니다.");
        }
        Board boardRes = board.get();
        // 수정
        boardRes.setBoardTitle(boardDTO.getBoardTitle());
        boardRes.setBoardContent(boardDTO.getBoardContent());

        ResponseSuccessDTO<String> res = responseUtil.successResponse("게시글"+boardId +" 번 수정 성공", HttpStatus.OK);
        return res;
    }

    @Transactional
    public ResponseSuccessDTO<String> deleteBoard(Long boardId){
        if(boardId==null){
            throw new EntityNullException("게시글 ID 가 없습니다,");
        }
        Optional<Board> board= boardRepository.findById(boardId);
        if(!board.isPresent()){
            throw new EntityNullException("게시글 "+ boardId+" 이 DB에 없습니다.");
        }
        bookmarkRepository.deleteByBoardId(boardId);
        boardRepository.deleteById(boardId);
        ResponseSuccessDTO<String> res = responseUtil.successResponse("게시글"+boardId +" 번 삭제 성공", HttpStatus.OK);
        return res;
    }


}
