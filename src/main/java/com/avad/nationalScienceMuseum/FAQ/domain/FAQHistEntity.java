package com.avad.nationalScienceMuseum.FAQ.domain;

import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoEntity;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "FAQHistEntityBuilder")
@ToString
@Getter
@Entity
@Table(name="faq_history")
public class FAQHistEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "his_index")
    private long his_index;

    @Column(name = "his_time")
    private Timestamp his_time;

    @Column(name = "his_question")
    private String his_question;

    @Column(name = "language")
    private String language;

    @Column(name = "his_added_index")
    private long his_added_index;

    @Column(name = "his_detected_index")
    private long his_detected_index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="kiosk_id", referencedColumnName = "id")
    private KioskInfoEntity kioskInfoEntity;

    /**
     * PJH
     * - FAQHistEntity Builder
     */
    public static FAQHistEntityBuilder builder() {
        return FAQHistEntityBuilder();
    }

    /**
     * PJH
     * - Cast to {@link FAQHistDTO}
     */
    public FAQHistDTO toFAQHistDTO(){
        return FAQHistDTO.FAQHistDTOBuilder()
                .his_index(his_index)
                .his_time(his_time)
                .his_question(his_question)
                .language(language)
                .his_added_index(his_added_index)
                .his_detected_index(his_detected_index)
                .kioskInfoDTO(kioskInfoEntity.toKioskInfoDTO())
                .build();
    }
}
