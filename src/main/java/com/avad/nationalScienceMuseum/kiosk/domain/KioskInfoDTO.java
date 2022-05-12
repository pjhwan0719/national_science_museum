package com.avad.nationalScienceMuseum.kiosk.domain;

import com.avad.nationalScienceMuseum.counsel.domain.SectorDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(builderMethodName = "KioskInfoDTOBuilder")
public class KioskInfoDTO {

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
     * - KioskInfoDTO Builder
     */
    public static KioskInfoDTOBuilder builder() {
        return KioskInfoDTOBuilder();
    }

    /**
     * PJH
     * - Cast to {@link KioskInfoEntity}
     */
    public KioskInfoEntity toKioskInfoEntity() {
        return KioskInfoEntity.KioskInfoEntityBuilder()
                .seq(seq)
                .id(id)
                .ip(ip)
                .status(status)
                .is_del(is_del)
                .insert_date(insert_date)
                .update_date(update_date)
                .sectorEntity(sectorDTO.toSectorEntity())
                .build();
    }
}
