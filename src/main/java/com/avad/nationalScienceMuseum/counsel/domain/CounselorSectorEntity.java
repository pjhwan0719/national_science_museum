package com.avad.nationalScienceMuseum.counsel.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "CounselorSectorEntityBuilder")
@ToString
@Getter
@Entity
@Table(name="videosupport_counselor_sector")
public class CounselorSectorEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "counselor_seq", referencedColumnName = "seq")
    private CounselorEntity counselorEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_code", referencedColumnName = "code")
    private SectorEntity sectorEntity;

    /**
     * PJH
     * - CounselorSectorEntity builder
     */
    public static CounselorSectorEntityBuilder builder() {
        return CounselorSectorEntityBuilder();
    }

    /**
     * PJH
     * - Cast to {@link CounselorSectorDTO}
     */
    public CounselorSectorDTO toCounselorSectorDTO() {
        return CounselorSectorDTO.CounselorSectorDTOBuilder()
                .seq(seq)
                .counselorDTO(counselorEntity.toCounselorDTO())
                .sectorDTO(sectorEntity.toSectorDTO())
                .build();
    }
}
