package com.avad.nationalScienceMuseum.videoSupport.domain;

import com.avad.nationalScienceMuseum.counsel.domain.CounselorEntity;
import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "VideoSupportHistEntityBuilder")
@ToString
@Getter
@Entity
@Table(name = "videosupport_history")
public class VideoSupportHistEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counselor_seq", referencedColumnName = "seq")
    private CounselorEntity counselorEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kiosk_seq", referencedColumnName = "seq")
    private KioskInfoEntity kioskInfoEntity;

    @Column(name = "start_date")
    private String start_date;

    @Column(name = "end_date")
    private String end_date;

    /**
     * PJH
     * - VideoSupportHistEntity builder
     */
    public static VideoSupportHistEntityBuilder builder() {
        return VideoSupportHistEntityBuilder();
    }

    /**
     * PJH
     * - Cast to {@link VideoSupportHistDTO}
     */
    public VideoSupportHistDTO toVideoSupportHistDTO() {
        return VideoSupportHistDTO.VideoSupportHistDTOBuilder()
                .seq(seq)
                .counselorInfoDTO(counselorEntity.toCounselorInfoDTO())
                .kioskInfoDTO(kioskInfoEntity.toKioskInfoDTO())
                .start_date(start_date)
                .end_date(end_date)
                .build();
    }
}
