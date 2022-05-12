package com.avad.nationalScienceMuseum.exception;

import com.avad.nationalScienceMuseum.exception.domain.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
