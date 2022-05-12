package com.avad.nationalScienceMuseum.kiosk.domain;

import com.avad.nationalScienceMuseum.counsel.domain.SectorEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "KioskInfoEntityBuilder")
@ToString
@Getter
@Entity
@Table(name = "kiosk_info")
public class KioskInfoEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;

    @Column(name = "id")
    private String id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "status")
    private String status;

    @Column(name = "is_del")
    private String is_del;

    @Column(name = "insert_date")
    private String insert_date;

    @Column(name = "update_date")
    private String update_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_code", referencedColumnName = "code")
    private SectorEntity sectorEntity;


    /**
     * PJH
     * - KioskEntity Builder
     */
    public static KioskInfoEntityBuilder builder() {
        return KioskInfoEntityBuilder();
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
                .sectorDTO(sectorEntity.toSectorDTO())
                .build();
    }

    public void change_ip(String ip) {
        this.ip = ip;
    }

    public void change_isDel(String is_del) {
        this.is_del = is_del;
    }

    public void change_updateDate(String update_date) {
        this.update_date = update_date;
    }

    public void change_sectorEntity(SectorEntity sectorEntity) {
        this.sectorEntity = sectorEntity;
    }
}
