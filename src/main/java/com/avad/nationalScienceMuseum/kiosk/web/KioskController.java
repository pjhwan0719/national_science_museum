package com.avad.nationalScienceMuseum.kiosk.web;

import com.avad.nationalScienceMuseum.common.*;
import com.avad.nationalScienceMuseum.counsel.domain.SectorDTO;
import com.avad.nationalScienceMuseum.counsel.service.SectorService;
import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoDTO;
import com.avad.nationalScienceMuseum.kiosk.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class KioskController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KioskService kioskService;
    private final SectorService sectorService;
    private final DateUtil dateUtil;
    private final DataUtil dataUtil;

    /**
     * PJH
     * - Kiosk 등록 페이지
     * @param model
     */
    @GetMapping(value = "/kiosk/new")
    public String createKioskView(Model model) {
        logger.info("[GetMapping] - /kiosk/new");
        List<SectorDTO> sectorList = sectorService.findSectorList();
        model.addAttribute("sectorList", sectorList);
        return "kiosk/kioskCreate";
    }

    /**
     * PJH
     * - KIOSK 등록
     * @param kiosk_id
     * @param kiosk_ip
     * @param sector_code
     */
    @PostMapping(value="/kiosk/new")
    @ResponseBody
    public ResponseEntity<Object> createKiosk (
            HttpServletRequest request,
            @RequestParam(value = "kiosk_id") String kiosk_id
            , @RequestParam(value = "kiosk_ip", required = false, defaultValue = "") String kiosk_ip
            , @RequestParam(value = "sector_code") String sector_code)
    {
        logger.info("[PostMapping] - /kiosk/new ## kiosk_id : "+kiosk_id+", kiosk_ip : "+kiosk_ip+", sector_code : "+sector_code);
        String currentDate = dateUtil.getFullCurrentDate();
        KioskInfoDTO kioskInfoDTO = KioskInfoDTO.KioskInfoDTOBuilder()
                .id(kiosk_id)
                .ip(kiosk_ip)
                .status("0")
                .is_del("N")
                .insert_date(currentDate)
                .update_date(currentDate)
                .sectorDTO(SectorDTO.SectorDTOBuilder().code(sector_code).build())
                .build();
        //logger.info("@@ PJH >> kioskInfoDTO : "+dataUtil.toJSONString(kioskInfoDTO));
        kioskService.createKiosk(kioskInfoDTO);
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.CREATE_KIOSK_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * PJH
     * - kiosk 정보 수정
     * @param kiosk_seq
     * @param ip
     * @param sector_code
     */
    @PostMapping(value = "/kiosk/{kiosk_seq}")
    public ResponseEntity<Object> updateKiosk (
            @PathVariable(value = "kiosk_seq")long kiosk_seq,
            @RequestParam(value = "ip", required = false, defaultValue = "") String ip,
            @RequestParam(value = "sector_code") String sector_code)
    {
        logger.info("[PostMapping] - /kiosk/{kiosk_seq} ## kiosk_seq : "+kiosk_seq+", ip : "+ip+", sector_code : "+sector_code);
        String currentDate = dateUtil.getFullCurrentDate();
        KioskInfoDTO kioskInfoDTO = KioskInfoDTO.KioskInfoDTOBuilder()
                .seq(kiosk_seq)
                .ip(ip)
                .update_date(currentDate)
                .sectorDTO(SectorDTO.SectorDTOBuilder().code(sector_code).build())
                .build();
        kioskService.saveKioskInfo(kioskInfoDTO);
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.UPDATE_KIOSK_SUCCESS), HttpStatus.OK);
    }

    /**
     * PJH
     * - Kiosk 삭제
     * @param kiosk_seq
     */
    @DeleteMapping(value = "/kiosk/{kiosk_seq}")
    public ResponseEntity<Object> deleteKiosk(@PathVariable(value = "kiosk_seq")long kiosk_seq) {
        logger.info("[DeleteMapping] - /kiosk/{kiosk_seq} ## kiosk_seq : "+kiosk_seq);
        KioskInfoDTO kioskInfoDTO = KioskInfoDTO.KioskInfoDTOBuilder()
                .seq(kiosk_seq)
                .is_del("Y")
                .update_date(dateUtil.getFullCurrentDate())
                .build();
        kioskService.delete(kioskInfoDTO);
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.DELETE_KIOSK_SUCCESS), HttpStatus.OK);
    }

    /**
     * PJH
     * - Kiosk 찾기 By sector
     * @param sector_code
     */
    @GetMapping(value = "/kiosk/list/sectors")
    public ResponseEntity<Object> findKioskListBySector(@RequestParam(name = "sector_code")String sector_code) {
        logger.info("[GetMapping] - /kiosk/list/sectors");
        List<String> sectorList = Arrays.asList(sector_code.split("@"));
        List<KioskInfoDTO> kioskInfoDTOList = kioskService.finKioskBySector(sectorList);
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.READ_SUCCESS, kioskInfoDTOList), HttpStatus.OK);
    }

    /**
     * PJH
     * - Kiosk 찾기
     */
    @GetMapping(value = "/kiosk/list")
    public ResponseEntity<Object> findkioskList() {
        logger.info("[GetMapping] - /kiosk/list/sectors");
        List<KioskInfoDTO> kioskInfoDTOList = kioskService.findKiosk();
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.READ_SUCCESS, kioskInfoDTOList), HttpStatus.OK);
    }
}