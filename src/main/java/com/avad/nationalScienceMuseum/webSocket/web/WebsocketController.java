package com.avad.nationalScienceMuseum.webSocket.web;

import com.avad.nationalScienceMuseum.common.DataUtil;
import com.avad.nationalScienceMuseum.common.DateUtil;
import com.avad.nationalScienceMuseum.counsel.domain.CounselorInfoDTO;
import com.avad.nationalScienceMuseum.counsel.service.CounselorService;
import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoDTO;
import com.avad.nationalScienceMuseum.kiosk.service.KioskService;
import com.avad.nationalScienceMuseum.videoSupport.domain.VideoSupportHistDTO;
import com.avad.nationalScienceMuseum.videoSupport.service.VideoSupportService;
import com.avad.nationalScienceMuseum.webSocket.domain.SocketVO;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SimpMessagingTemplate template;
    private final KioskService kioskService;
    private final CounselorService counselorService;
    private final VideoSupportService videoSupportService;
    private final DateUtil dateUtil;
    private final DataUtil dataUtil;

    /**
     * PJH
     * - Client(Kiosk)에서의 요청
     * - 여기서는 Kiosk가 화상지원을 요청하는 시점이다.
     * @param socketVO
     */
    @MessageMapping(value = "/connect/kiosk")
    public void kioskToCounselor(@ModelAttribute SocketVO socketVO)
    {
        logger.info("[MessageMapping] - /connect/server ## socketVO.toString() : "+socketVO.toString());
        KioskInfoDTO kioskInfoDTO = change_kioskStatus(socketVO);
        template.convertAndSend("/counselor/" + kioskInfoDTO.getSectorDTO().getCode(), socketVO);
    }

    /**
     * PJH
     * - Client(WPF)에서의 요청
     * - 여기서는 상담사가 연결하는 시점이다.
     * @param socketVO
     */
    @MessageMapping(value = "/connect/counselor")
    public void counselorToKiosk(@ModelAttribute SocketVO socketVO)
    {
        logger.info("[MessageMapping] - /connect/counselor ## socketVO.toString() : "+socketVO.toString());
        KioskInfoDTO kioskInfoDTO = change_kioskStatus(socketVO);

        socketVO.setStart_date(dateUtil.getFullCurrentDate());
        VideoSupportHistDTO videoSupportHistDTO = saveVideoSupportHistory(socketVO);
        socketVO.setHist_seq(videoSupportHistDTO.getSeq());

        template.convertAndSend("/counselor/" + kioskInfoDTO.getSectorDTO().getCode(), socketVO);
        template.convertAndSend("/kiosk/" + socketVO.getKiosk_id(), socketVO);
    }

    /**
     * PJH
     * - Client(Counselor)에서의 요청
     * - 여기서는 Kiosk <-> Counselor 화상지원이 끝날때 시점이다.
     * @param socketVO
     */
    @MessageMapping(value = "/disconnect/counselor")
    public void counselorToCallEnd(@ModelAttribute SocketVO socketVO)
    {
        logger.info("[MessageMapping] - /disconnect/counselor ## socketVO.toString() : "+socketVO.toString());
        KioskInfoDTO kioskInfoDTO = change_kioskStatus(socketVO);

        socketVO.setEnd_date(dateUtil.getFullCurrentDate());
        VideoSupportHistDTO videoSupportHistDTO = saveVideoSupportHistory(socketVO);
        socketVO.setHist_seq(videoSupportHistDTO.getSeq());

        template.convertAndSend("/counselor/" + kioskInfoDTO.getSectorDTO().getCode(), socketVO);
        template.convertAndSend("/kiosk/" + socketVO.getKiosk_id(), socketVO);
    }

    /**
     * PJH
     * - Client(Kiosk)에서의 요청
     * - 여기서는 Kiosk <-> Counselor 화상지원이 끝날때 시점이다.
     * @param socketVO
     */
    @MessageMapping(value = "/disconnect/kiosk")
    public void kioskToCallEnd(@ModelAttribute SocketVO socketVO)
    {
        logger.info("[MessageMapping] - /disconnect/kiosk ## socketVO.toString() : "+socketVO.toString());
        KioskInfoDTO kioskInfoDTO = change_kioskStatus(socketVO);

        socketVO.setEnd_date(dateUtil.getFullCurrentDate());
        VideoSupportHistDTO videoSupportHistDTO = saveVideoSupportHistory(socketVO);
        socketVO.setHist_seq(videoSupportHistDTO.getSeq());

        template.convertAndSend("/counselor/" + kioskInfoDTO.getSectorDTO().getCode(), socketVO);
        template.convertAndSend("/kiosk/" + socketVO.getKiosk_id(), socketVO);
    }

    /**
     * PJH
     * - 상담사 부재중일때
     * @param socketVO
     */
    @MessageMapping(value = "/notConnect/counselor")
    public void notConnectedCounselor(@ModelAttribute SocketVO socketVO)
    {
        logger.info("[MessageMapping] - /notConnect/counselor ## socketVO.toString() : "+socketVO.toString());
        KioskInfoDTO kioskInfoDTO = change_kioskStatus(socketVO);
        template.convertAndSend("/counselor/" + kioskInfoDTO.getSectorDTO().getCode(), socketVO);
    }

    /**
     * PJH
     * - 이미 연결중일때 나중에 연결하려는 상담사는 연결을 취소한다.
     * @param socketVO
     */
    @MessageMapping(value = "/alreadyConnect/counselor")
    public void alraedyConnected(@ModelAttribute SocketVO socketVO)
    {
        logger.info("[MessageMapping] - /alreadyConnect/counselor ## socketVO.toString() : "+socketVO.toString());
        KioskInfoDTO kioskInfoDTO = change_kioskStatus(socketVO);
        template.convertAndSend("/counselor/" + kioskInfoDTO.getSectorDTO().getCode(), socketVO);
    }

    /**
     * PJH
     * - 서버 내부오류로 연결 실패 시
     * @param socketVO
     */
    @MessageMapping(value = "/internal/error")
    public void internalServerError(@ModelAttribute SocketVO socketVO) {
        VideoSupportHistDTO videoSupportHistDTO = saveVideoSupportHistory(socketVO);
    }

    /* ********************************************************************************************************** */

    /**
     * PJH
     * - 화상지원 이력 저장
     * @param socketVO
     */
    public VideoSupportHistDTO saveVideoSupportHistory(SocketVO socketVO)
    {
        CounselorInfoDTO counselorInfoDTO = counselorService.findCounselorByCounselorId(socketVO.getCounselor_id());
        KioskInfoDTO kioskInfoDTO = kioskService.findKioskByKioskId(socketVO.getKiosk_id());
        VideoSupportHistDTO videoSupportHistDTO = VideoSupportHistDTO.VideoSupportHistDTOBuilder()
                .seq(socketVO.getHist_seq())
                .counselorInfoDTO(counselorInfoDTO)
                .kioskInfoDTO(kioskInfoDTO)
                .start_date(socketVO.getStart_date())
                .end_date((socketVO.getEnd_date()))
                .build();
        return videoSupportService.saveVideoSupportHistory(videoSupportHistDTO);
    }

    /**
     * PJH
     * - Kiosk 상태값 변경
     * @param socketVO
     */
    public KioskInfoDTO change_kioskStatus(SocketVO socketVO)
    {
        KioskInfoDTO kioskInfoDTO = kioskService.findKioskByKioskId(socketVO.getKiosk_id());
        kioskInfoDTO.setStatus(socketVO.getStatus());
        kioskInfoDTO.setUpdate_date(dateUtil.getFullCurrentDate());
        kioskService.saveKioskVideoSupportStatus(kioskInfoDTO);
        return kioskInfoDTO;
    }

}