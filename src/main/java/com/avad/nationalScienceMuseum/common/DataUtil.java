package com.avad.nationalScienceMuseum.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataUtil {

    private final ObjectMapper objectMapper;

    /**
     * PJH
     * - Object to Json String
     * @param obj
     * @return
     */
    public String toJSONString(Object obj) {
        try {
            if (obj == null) {
                return "";
            } else {
                return objectMapper.writeValueAsString(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
