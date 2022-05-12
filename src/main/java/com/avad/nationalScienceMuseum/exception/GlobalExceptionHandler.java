package com.avad.nationalScienceMuseum.exception;

import com.avad.nationalScienceMuseum.common.ResponseErrorUtil;
import com.avad.nationalScienceMuseum.exception.domain.ErrorCode;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ResponseErrorUtil> handleDataException() {
        logger.error("handleDataException throw Exception : {}", ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseErrorUtil.toResponseError(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ResponseErrorUtil> handleCustomException(CustomException e) {
        logger.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ResponseErrorUtil.toResponseError(e.getErrorCode());
    }
}
