package com.avad.nationalScienceMuseum.counsel.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "SectorDTOBuilder")
@ToString
@Getter
@Setter
public class SectorDTO {

    private String code;
    private String name;

    /**
     * PJH
     * - SectorDTO builder
     */
    public static SectorDTOBuilder builder() {
        return SectorDTOBuilder();
    }

    /**
     * PJH
     * - Cast to {@link SectorEntity}
     */
    public SectorEntity toSectorEntity() {
        return SectorEntity.SectorEntityBuilder()
                .code(code)
                .name(name)
                .build();
    }

}