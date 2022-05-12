package com.avad.nationalScienceMuseum.FAQ.service.Impl;

import com.avad.nationalScienceMuseum.FAQ.domain.FAQHistDTO;
import com.avad.nationalScienceMuseum.FAQ.domain.FAQXmlDTO;
import com.avad.nationalScienceMuseum.FAQ.domain.FAQXmlEntity;
import com.avad.nationalScienceMuseum.FAQ.repository.FAQHistRepository;
import com.avad.nationalScienceMuseum.FAQ.repository.FAQXmlRepository;
import com.avad.nationalScienceMuseum.FAQ.service.FAQService;
import com.avad.nationalScienceMuseum.common.DataUtil;
import com.avad.nationalScienceMuseum.exception.CustomException;
import com.avad.nationalScienceMuseum.exception.domain.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FAQHistRepository faqHistRepository;
    private final FAQXmlRepository faqXmlRepository;

    private final DataUtil dataUtil;

    @Transactional
    @Override
    public void saveFQAHist(FAQHistDTO faqHistDTO) {
        faqHistRepository.save(faqHistDTO.toFAQHistEntity());
    }

    @Transactional
    @Override
    public FAQXmlDTO newestFAQXmlByLanguage(String language) {
        List<FAQXmlEntity> newestXml = faqXmlRepository.findNewestXmlByLanguage(language, Pageable.ofSize(1));
        // newestXml.stream().forEach(s->logger.info(dataUtil.toJSONString(s.toFAQXmlDTO())));
        if (newestXml.isEmpty()) {
            throw new CustomException(ErrorCode.FILE_EMPTY);
        }
        else {
            return newestXml.get(0).toFAQXmlDTO();
        }
    }

    @Override
    @Transactional
    public void saveFAQXml(List<FAQXmlDTO> faqXmlDTOList) {
        faqXmlDTOList.forEach(faqXmlDTO -> faqXmlRepository.save(faqXmlDTO.toFAQXmlEntity()));
    }
}
