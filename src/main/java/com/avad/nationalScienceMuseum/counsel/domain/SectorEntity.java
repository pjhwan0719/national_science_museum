package com.avad.nationalScienceMuseum.counsel.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "SectorEntityBuilder")
@ToString
@Getter
@Entity
@Table(name="videosupport_sector")
public class SectorEntity implements Serializable {

    @Id
    @Column(name = "code")
    private String code ;

    @Column(name = "name")
    private String name ;

    /**
     * PJH
     * - SectorEntity builder
     */
    public static SectorEntityBuilder builder() {
        return SectorEntityBuilder();
    }

    /**
     * PJH
     * - Cast to {@link SectorDTO}
     */
    public SectorDTO toSectorDTO() {
        return SectorDTO.SectorDTOBuilder()
                .code(code)
                .name(name)
                .build();
    }
}