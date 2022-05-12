package com.avad.nationalScienceMuseum.FAQ.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderMethodName = "FAQXmlEntityBuilder")
@ToString
@Getter
@Entity
@Table(name="faq_xml")
public class FAQXmlEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "xml_index")
    private long xml_index;

    @Column(name = "xml_time")
    private String xml_time;

    @Column(name = "language")
    private String language;

    @Column(name = "xml_path")
    private String xml_path;

    @Column(name = "size")
    private long size;

    /**
     * PJH
     * - FAQXmlEntity Builder
     */
    public static FAQXmlEntityBuilder build() {
        return FAQXmlEntityBuilder();
    }

    /**
     * PJH
     * - Cast to {@link FAQXmlDTO}
     */
    public FAQXmlDTO toFAQXmlDTO() {
        return FAQXmlDTO.FAQXmlDTOBuilder()
                .xml_index(xml_index)
                .xml_time(xml_time)
                .language(language)
                .xml_path(xml_path)
                .size(size)
                .build();
    }
}
