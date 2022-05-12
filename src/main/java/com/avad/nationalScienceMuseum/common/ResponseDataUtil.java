package com.avad.nationalScienceMuseum.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class ResponseDataUtil<T> {

    private String msg;
    private T data;

    public ResponseDataUtil(final String responseMessage) {
        this.msg = responseMessage;
        this.data = null;
    }

    public static<T> ResponseDataUtil<T> res(final String responseMessage) {
        return res(responseMessage, null);
    }

    public static<T> ResponseDataUtil<T> res(final String responseMessage, final T t) {
            return ResponseDataUtil.<T>builder()
                    .msg(responseMessage)
                    .data(t)
                    .build();
    }
}
