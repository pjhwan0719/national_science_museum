package com.avad.nationalScienceMuseum.FAQ.domain;

import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder(builderMethodName = "FAQHistDTOBuilder")
public class FAQHistDTO {

    private long his_index;
    private Timestamp his_time;
    private String his_question;
    private String language;
    private long his_added_index;
    private long his_detected_index;
    private KioskInfoDTO kioskInfoDTO;

    /**
     * PJH
     * - FAQHistDTO Builder
     */
    public static FAQHistDTOBuilder builder() {
        return FAQHistDTOBuilder();
    }

    /**
     * PJH
     * - Cast to {@link FAQHistEntity}
     */
    public FAQHistEntity toFAQHistEntity(){
        return FAQHistEntity.FAQHistEntityBuilder()
                .his_index(his_index)
                .his_time(his_time)
                .his_question(his_question)
                .language(language)
                .his_added_index(his_added_index)
                .his_detected_index(his_detected_index)
                .kioskInfoEntity(kioskInfoDTO.toKioskInfoEntity())
                .build();
    }
}
