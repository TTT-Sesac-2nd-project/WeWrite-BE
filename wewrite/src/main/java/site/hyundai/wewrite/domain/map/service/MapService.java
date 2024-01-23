package site.hyundai.wewrite.domain.map.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.auth.repository.UserRepository;
import site.hyundai.wewrite.domain.board.dto.BoardListDTO;
import site.hyundai.wewrite.domain.board.repository.BoardImageRepository;
import site.hyundai.wewrite.domain.board.repository.BoardRepository;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.Image;
import site.hyundai.wewrite.domain.entity.User;
import site.hyundai.wewrite.domain.entity.UserGroup;
import site.hyundai.wewrite.domain.group.repository.GroupRepository;
import site.hyundai.wewrite.domain.group.repository.UserGroupRepository;
import site.hyundai.wewrite.domain.image.repository.ImageRepository;
import site.hyundai.wewrite.domain.map.dto.MapBoardDTO;
import site.hyundai.wewrite.domain.map.dto.response.MapGetResponseDTO;
import site.hyundai.wewrite.domain.map.dto.response.MapListGetResponseDTO;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;
import site.hyundai.wewrite.global.util.ResponseUtil;
import site.hyundai.wewrite.global.util.TimeService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MapService {
    private final ResponseUtil responseUtil;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardImageRepository boardImageRepository;
    private final TimeService timeService;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final ImageRepository imageRepository;

    public ResponseSuccessDTO<MapListGetResponseDTO> getMapList(String userId , Long groupId){
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
        List<MapBoardDTO> boardListDTOList = new ArrayList<>();
        for(List<Board> bL : totalBoardList){
            for(Board b : bL) {
                Long boardImageId = boardImageRepository.findOneLatestImageByBoardId(b.getBoardId()).getImageId();
                MapBoardDTO mapBoardDTO = MapBoardDTO.builder()
                        .boardId(b.getBoardId())
                        .boardImage(imageRepository.findById(boardImageId).get().getUploadFileUrl())
                        .boardLng(b.getBoardLong())
                        .boardLat(b.getBoardLat())
                        .build();
                boardListDTOList.add(mapBoardDTO);
            }
        }
        ResponseSuccessDTO<MapListGetResponseDTO>  res = responseUtil.successResponse(boardListDTOList,HttpStatus.OK);
    return res;
    }
    public ResponseSuccessDTO<MapGetResponseDTO> getMap(Long boardId) {
        if(boardId==null){
            throw new EntityNullException("boarId가 NULL입니다.");
        }
    Board board = boardRepository.findById(boardId).orElseThrow(()-> new EntityNullException("게시글이 DB에 없습니다."));
    Image image = boardImageRepository.findOneLatestImageByBoardId(boardId);
    if(image==null){
        throw new EntityNullException("image가 NULL입니다. 게시글에 필수로 넣어주세요");
    }
        MapGetResponseDTO mapBoardDTO = MapGetResponseDTO.builder()
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardCreatedDate(timeService.parseLocalDateTimeForMap(board.getBoardCreatedDate()))
                .groupName(board.getGroup().getGroupName())
                .boardImage(image.getUploadFileUrl())
                .build();


        ResponseSuccessDTO<MapGetResponseDTO>  res = responseUtil.successResponse(mapBoardDTO,HttpStatus.OK);
        return res;
    }
}