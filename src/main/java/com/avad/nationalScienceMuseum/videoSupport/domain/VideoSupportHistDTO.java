package com.avad.nationalScienceMuseum.videoSupport.domain;

import com.avad.nationalScienceMuseum.counsel.domain.CounselorInfoDTO;
import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "VideoSupportHistDTOBuilder")
@Getter
@Setter
public class VideoSupportHistDTO {

    private long seq;
    private CounselorInfoDTO counselorInfoDTO;
    private KioskInfoDTO kioskInfoDTO;
    private String start_date;
    private String end_date;

    /**
     * PJH
     * - VideoSupportHistDTO builder
     */
    public static VideoSupportHistDTOBuilder builder() {
        return VideoSupportHistDTOBuilder();
    }

    /**
     * PJH
     * Cast to {@link VideoSupportHistEntity}
     */
    public VideoSupportHistEntity toVideoSupportHistEntity() {
        return VideoSupportHistEntity.VideoSupportHistEntityBuilder()
                .seq(seq)
                .counselorEntity(counselorInfoDTO.toCounselorEntity())
                .kioskInfoEntity(kioskInfoDTO.toKioskInfoEntity())
                .start_date(start_date)
                .end_date(end_date)
                .build();
    }

}
