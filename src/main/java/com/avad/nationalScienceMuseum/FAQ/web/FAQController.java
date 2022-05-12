package com.avad.nationalScienceMuseum.FAQ.web;

import com.avad.nationalScienceMuseum.FAQ.domain.FAQHistDTO;
import com.avad.nationalScienceMuseum.FAQ.domain.FAQXmlDTO;
import com.avad.nationalScienceMuseum.FAQ.service.FAQService;
import com.avad.nationalScienceMuseum.common.*;
import com.avad.nationalScienceMuseum.file.service.FileService;
import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoDTO;
import com.avad.nationalScienceMuseum.kiosk.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class FAQController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${file.upload.faq.xml.path}")
    private String faqXmlPath;

    private final FAQService faqService;
    private final KioskService kioskService;
    private final FileService fileService;

    private final DateUtil dateUtil;
    private final DataUtil dataUtil;

    /**
     * PJH
     * - FAQ HIST 등록
     * @param question
     * @param language
     * @param detect_id
     * @param kiosk_id
     */
    @GetMapping(value="/faq/hist/new")
    @ResponseBody
    public ResponseEntity<Object> faqHistNew (
            @RequestParam(value = "question") String question
            , @RequestParam(value = "language") String language
            , @RequestParam(value = "detect_id", required = false, defaultValue = "0") long detect_id
            , @RequestParam(value="kiosk_id") String kiosk_id)
    {
        logger.info("[GetMapping] - /faq/hist/new");

        KioskInfoDTO kioskInfoDTO = kioskService.findKioskByKioskId(kiosk_id);
        //logger.info("@@ PJH >> kioskInfoDTO : "+dataUtil.toJSONString(kioskInfoDTO));
        FAQHistDTO faqHistDTO = FAQHistDTO.FAQHistDTOBuilder()
                .his_time(new Timestamp(dateUtil.getFullCurrentDateLong()))
                .his_question(question)
                .language(language)
                .his_detected_index(detect_id)
                .kioskInfoDTO(kioskInfoDTO)
                .build();

        faqService.saveFQAHist(faqHistDTO);
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.CREATE_FAQ_HIST_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * PJH
     * - fAQ xml 업로드
     * @param files
     * @param language
     */
    @PostMapping(value = "/faq/xml/upload")
    public ResponseEntity<Object> fileUpload
        (@RequestParam(value = "uploadFiles") MultipartFile[] files
         , @RequestParam(name = "language") String language)
    {
        logger.info("[PostMapping] - /faq/xml/upload");

        String status = fileService.fileUpload(files, faqXmlPath);
        List<FAQXmlDTO> faqXmlDTOList = new ArrayList<>();
        for (MultipartFile file : files) {
            FAQXmlDTO faqXmlDTO = FAQXmlDTO.build()
                    .xml_time(dateUtil.getFullCurrentDate())
                    .language(language)
                    .xml_path(faqXmlPath + "/" + file.getOriginalFilename())
                    .size(file.getSize())
                    .build();
            faqXmlDTOList.add(faqXmlDTO);
        }
        faqService.saveFAQXml(faqXmlDTOList);
        return new ResponseEntity<Object>(ResponseDataUtil
                .res(StatusMessage.FILE_UPLOAD_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * PJH
     * - faq xml 다운로드
     * @param filePath
     */
    @GetMapping(value = "/faq/xml/download")
    public ResponseEntity<Object> fileDownload(@RequestParam(name = "filePath") String filePath) {
        logger.info("[GetMapping] - /faq/xml/download");
        return fileService.fileDownload(filePath);
    }


    /**
     * PJH
     * - FAQ xml 최신 여부
     * @param xml_lang
     */
    @GetMapping(value = "/faq/newest/xml")
    public ResponseEntity<Object> faqCheckXml (@RequestParam(value = "xml_lang") String xml_lang)
    {
        logger.info("[GetMapping] - /faq/newest/xml");
        FAQXmlDTO faqXmlDTO = faqService.newestFAQXmlByLanguage(xml_lang);
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.READ_SUCCESS, faqXmlDTO), HttpStatus.OK);
    }
}
