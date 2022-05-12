package com.avad.nationalScienceMuseum.counsel.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "CounselorSectorDTOBuilder")
@ToString
@Getter
@Setter
public class CounselorSectorDTO {

    private long seq;
    private CounselorDTO counselorDTO;
    private SectorDTO sectorDTO;

    /**
     * PJH
     * - CounselorSectorDTO builder
     */
    public static CounselorSectorDTOBuilder builder() {
        return CounselorSectorDTOBuilder();
    }

    /**
     * PJH
     * - Cast to {@link CounselorSectorEntity}
     */
    public CounselorSectorEntity toCounselorSectorEntity() {
        return CounselorSectorEntity.CounselorSectorEntityBuilder()
                .seq(seq)
                .sectorEntity(sectorDTO.toSectorEntity())
                .counselorEntity(counselorDTO.toCounselorEntity())
                .build();
    }
}
