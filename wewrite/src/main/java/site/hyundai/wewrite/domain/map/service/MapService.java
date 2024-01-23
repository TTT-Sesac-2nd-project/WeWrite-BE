package site.hyundai.wewrite.domain.map.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.hyundai.wewrite.domain.auth.repository.UserRepository;
import site.hyundai.wewrite.domain.board.repository.BoardImageRepository;
import site.hyundai.wewrite.domain.board.repository.BoardRepository;
import site.hyundai.wewrite.domain.entity.Board;
import site.hyundai.wewrite.domain.entity.Image;
import site.hyundai.wewrite.domain.group.repository.GroupRepository;
import site.hyundai.wewrite.domain.map.dto.MapBoardDTO;
import site.hyundai.wewrite.domain.map.dto.response.MapBoardGetResponseDTO;
import site.hyundai.wewrite.domain.map.dto.response.MapGetListResponseDTO;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;
import site.hyundai.wewrite.global.exeception.service.EntityNullException;
import site.hyundai.wewrite.global.util.ResponseUtil;
import site.hyundai.wewrite.global.util.TimeService;

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
    public ResponseSuccessDTO<MapBoardGetResponseDTO> getMap(Long boardId) {
        if(boardId==null){
            throw new EntityNullException("boarId가 NULL입니다.");
        }
    Board board = boardRepository.findById(boardId).orElseThrow(()-> new EntityNullException("게시글이 DB에 없습니다."));
    Image image = boardImageRepository.findOneLatestImageByBoardId(boardId);
    if(image==null){
        throw new EntityNullException("image가 NULL입니다. 게시글에 필수로 넣어주세요");
    }
        MapBoardGetResponseDTO mapBoardDTO = MapBoardGetResponseDTO.builder()
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardCreatedDate(timeService.parseLocalDateTimeForMap(board.getBoardCreatedDate()))
                .groupName(board.getGroup().getGroupName())
                .boardImage(image.getUploadFileUrl())
                .build();


        ResponseSuccessDTO<MapBoardGetResponseDTO>  res = responseUtil.successResponse(mapBoardDTO,HttpStatus.OK);
        return res;
    }
}
