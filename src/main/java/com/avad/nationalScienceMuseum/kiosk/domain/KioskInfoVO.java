package com.avad.nationalScienceMuseum.kiosk.domain;

import com.avad.nationalScienceMuseum.counsel.domain.SectorDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder(builderMethodName = "KioskInfoVOBuilder")
public class KioskInfoVO {

    private long seq;
    private String id;
    private String ip;
    private String status;
    private String is_del;
    private String insert_date;
    private String update_date;
    private SectorDTO sectorDTO;

    /**
     * PJH
     * - KioskInfoVO Builder
     */
    public static KioskInfoVO.KioskInfoVOBuilder builder() {
        return KioskInfoVOBuilder();
    }

    /**
     * PJH
     * - Cast to {@link KioskInfoDTO}
     */
    public KioskInfoDTO toKioskInfoDTO() {
        return KioskInfoDTO.KioskInfoDTOBuilder()
                .seq(seq)
                .id(id)
                .ip(ip)
                .status(status)
                .is_del(is_del)
                .insert_date(insert_date)
                .update_date(update_date)
                .sectorDTO(sectorDTO)
                .build();
    }

}
