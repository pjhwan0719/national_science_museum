package com.avad.nationalScienceMuseum.FAQ.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(builderMethodName = "FAQXmlDTOBuilder")
@Getter
@Setter
public class FAQXmlDTO {

    private long xml_index;
    private String xml_time;
    private String language;
    private String xml_path;
    private long size;

    /**
     * PJH
     * - FAQXmlDTO Builder
     */
    public static FAQXmlDTOBuilder build() {
        return FAQXmlDTOBuilder();
    }

    /**
     * PJH
     * - Cast to {@link FAQXmlEntity}
     */
    public FAQXmlEntity toFAQXmlEntity() {
        return FAQXmlEntity.FAQXmlEntityBuilder()
                .xml_index(xml_index)
                .xml_time(xml_time)
                .language(language)
                .xml_path(xml_path)
                .size(size)
                .build();
    }
}
