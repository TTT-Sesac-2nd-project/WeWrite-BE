package site.hyundai.wewrite.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import site.hyundai.wewrite.global.dto.ErrorContentDTO;
import site.hyundai.wewrite.global.dto.ResponseErrorDTO;
import site.hyundai.wewrite.global.dto.ResponseSuccessDTO;

import java.time.LocalDateTime;

/**
 * @author 김동욱
 */
@Component
@Slf4j
public class ResponseUtil<T> {

    @Autowired
    private TimeService timeService;

    public ResponseSuccessDTO<T> successResponse(T data, HttpStatus status) {
        ResponseSuccessDTO<T> res = ResponseSuccessDTO
                .<T>builder()
                .timeStamp(timeService.parseLocalDateTime(LocalDateTime.now()))
                .code(status.value())
                .status(status)
                .data(data)
                .build();

        return res;
    }

    public ResponseErrorDTO<ErrorContentDTO> buildErrorResponse(HttpStatus status, String message, String path) {
        ErrorContentDTO errorContentDto = ErrorContentDTO.builder()
                .message(message)
                .build();

        return ResponseErrorDTO
                .<ErrorContentDTO>builder()
                .timeStamp(timeService.parseLocalDateTime(LocalDateTime.now()))
                .code(status.value())
                .status(status)
                .path(path)
                .error(errorContentDto)
                .build();
    }

}
