package com.avad.nationalScienceMuseum.FAQ.service;

import com.avad.nationalScienceMuseum.FAQ.domain.FAQHistDTO;
import com.avad.nationalScienceMuseum.FAQ.domain.FAQXmlDTO;

import java.util.List;

public interface FAQService {

    /**
     * PJH
     * - FAQ hist 저장
     * @param faqHistDTO
     */
    public void saveFQAHist(FAQHistDTO faqHistDTO);

    /**
     * PJH
     * - 최신 FAQ xml 파일 가져오기
     * @param language
     */
    public FAQXmlDTO newestFAQXmlByLanguage(String language);

    /**
     * PJH
     * - FAQ xml 저장
     * @param faqXmlDTOList
     */
    public void saveFAQXml(List<FAQXmlDTO> faqXmlDTOList);
}
