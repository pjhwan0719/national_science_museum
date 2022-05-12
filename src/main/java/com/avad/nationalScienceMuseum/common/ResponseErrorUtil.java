package com.avad.nationalScienceMuseum.common;

import com.avad.nationalScienceMuseum.exception.domain.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ResponseErrorUtil {

    //private final LocalDateTime timestamp = LocalDateTime.now();
    // private final int status;
    // private final String error;
    // private final String code;
    private final String msg;

    public static ResponseEntity<ResponseErrorUtil> toResponseError(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ResponseErrorUtil.builder()
                        //.status(errorCode.getHttpStatus().value())
                        //.error(errorCode.getHttpStatus().name())
                        //.code(errorCode.name())
                        .msg(errorCode.getDetail())
                        .build()
                );
    }
}
