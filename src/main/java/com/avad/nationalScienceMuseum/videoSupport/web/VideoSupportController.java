package com.avad.nationalScienceMuseum.videoSupport.web;

import com.avad.nationalScienceMuseum.common.ResponseDataUtil;
import com.avad.nationalScienceMuseum.common.StatusMessage;
import com.avad.nationalScienceMuseum.kiosk.service.KioskService;
import com.avad.nationalScienceMuseum.videoSupport.domain.VideoSupportHistDTO;
import com.avad.nationalScienceMuseum.videoSupport.service.VideoSupportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VideoSupportController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KioskService kioskService;
    private final VideoSupportService videoSupportService;

    /**
     * PJH
     * - 화상지원 페이지 호출
     */
    @GetMapping(value = "/videoSupport")
    public String videoSupportView(Model model, @RequestParam(value = "kiosk_id")String kiosk_id) {
        logger.info("[GetMapping] - /videoSupport");
        kioskService.findKioskByKioskId(kiosk_id);
        model.addAttribute("kiosk_id", kiosk_id);
        return "videoSupport/videoSupport";
    }

    /**
     * PJH
     * - 화상지원 이력
     */
    @GetMapping(value = "/videoSupport/history")
    public ResponseEntity<Object> getVideoSupportHist() {
        logger.info("[GetMapping] - /videoSupport");
        List<VideoSupportHistDTO> videoSupportHistDTOList = videoSupportService.getVideoSupportHistory();
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.READ_SUCCESS, videoSupportHistDTOList), HttpStatus.OK);
    }


}
