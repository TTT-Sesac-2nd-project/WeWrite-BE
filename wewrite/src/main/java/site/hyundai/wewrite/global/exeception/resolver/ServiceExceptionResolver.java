package site.hyundai.wewrite.global.exeception.resolver;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import site.hyundai.wewrite.global.dto.ResponseErrorDTO;
import site.hyundai.wewrite.global.exeception.service.*;
import site.hyundai.wewrite.global.util.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ServiceExceptionResolver {

    private final ResponseUtil responseUtil;

    @ExceptionHandler(value = Exception.class)
    public ResponseErrorDTO<?> handle(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        return responseUtil.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),
                request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadVariableRequestException.class)
    public ResponseErrorDTO<?> handle(BadVariableRequestException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = EntityNullException.class)
    public ResponseErrorDTO<?> handle(EntityNullException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseErrorDTO<?> handle(MaxUploadSizeExceededException e, HttpServletRequest request) {

        String errorMessage = "업로드 용량 크기를 초과하였습니다.";
        return responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = NotAuthorizedUserException.class)
    public ResponseErrorDTO<?> handle(NotAuthorizedUserException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DecryptionFailedException.class)
    public ResponseErrorDTO<?> handle(DecryptionFailedException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseErrorDTO<?> handle(UnAuthorizedException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DuplicateRequestException.class)
    public ResponseErrorDTO<?> handle(DuplicateRequestException e, HttpServletRequest request) {
        return responseUtil.buildErrorResponse(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI());
    }

    private String getParams(HttpServletRequest req) {
        StringBuilder params = new StringBuilder();
        Enumeration<String> keys = req.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            params.append("- ").append(key).append(" : ").append(req.getParameter(key)).append('\n');
        }
        return params.toString();
    }


}
