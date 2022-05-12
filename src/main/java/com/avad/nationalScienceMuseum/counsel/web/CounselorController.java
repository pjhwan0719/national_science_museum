package com.avad.nationalScienceMuseum.counsel.web;

import com.avad.nationalScienceMuseum.common.*;
import com.avad.nationalScienceMuseum.counsel.domain.CounselorDTO;
import com.avad.nationalScienceMuseum.counsel.domain.CounselorInfoDTO;
import com.avad.nationalScienceMuseum.counsel.domain.CounselorVO;
import com.avad.nationalScienceMuseum.counsel.domain.SectorDTO;
import com.avad.nationalScienceMuseum.counsel.service.CounselorService;

import com.avad.nationalScienceMuseum.counsel.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CounselorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CounselorService counselorService;
    private final SectorService sectorService;
    private final DateUtil dateUtil;
    private final DataUtil dataUtil;


    /**
     * PJH
     * - 상담사 등록
     */
    @PostMapping(value = "/counselor/new")
    public ResponseEntity<Object> createCounselor (
            @RequestParam(name = "id") String id
            , @RequestParam(name = "pw") String pw
            , @RequestParam(name = "name") String name
            , @RequestParam(name = "is_main") String is_main
            , @RequestParam(name = "sectors") String sectors)
    {
        logger.info("[PostMapping] - /counselor/new");
        String currentDate = dateUtil.getFullCurrentDate();
        CounselorVO counselorVO = CounselorVO.CounselorVOBuilder()
                .id(id)
                .pw(pw)
                .name(name)
                .is_main(is_main)
                .is_use("Y")
                .is_login("N")
                .is_del("N")
                .insert_date(currentDate)
                .update_date(currentDate)
                .sectors(sectors)
                .build();
        counselorService.join(counselorVO.toCounselorDTO());
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.CREATED_COUNSELOR_SUCCESS), HttpStatus.CREATED);
    }

    /**
     * PJH
     * - 상담사 정보 수정
     * @param counselorVO
     */
    @PostMapping(value = "/counselor/update")
    public ResponseEntity<Object> updateCounselor(@ModelAttribute CounselorVO counselorVO)
    {
        logger.info("[PostMapping] - /counselor/update");
        counselorService.update(counselorVO.toCounselorDTO());
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.UPDATE_USER), HttpStatus.CREATED);
    }

    /**
     * PJH
     * - 상담사 삭제
     * @param counselor_seq
     */
    @DeleteMapping(value = "/counselor/{counselor_seq}")
    public ResponseEntity<Object> deleteCounselor(@PathVariable(value = "counselor_seq") long counselor_seq) {
        logger.info("[DeleteMapping] - /counselor/{counselor_seq} ## counselor_seq : "+counselor_seq);
        CounselorDTO counselorDTO = CounselorDTO.CounselorDTOBuilder()
                .seq(counselor_seq)
                .is_del("Y")
                .update_date(dateUtil.getFullCurrentDate())
                .build();
        counselorService.delete(counselorDTO);
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.DELETE_USER), HttpStatus.CREATED);
    }


    /**
     * PJH
     * - 상담사 로그인
     * @param id
     * @param pw
     */
    @PostMapping(value = "/counselor/login")
    public ResponseEntity<Object> login(@RequestParam(value="id") String id, @RequestParam(value="pw") String pw) {
        logger.info("[PostMapping] - /counselor/login");

        CounselorDTO counselorDTO = CounselorDTO.CounselorDTOBuilder()
                .id(id)
                .pw(pw)
                .build();
        CounselorInfoDTO counselorInfoDTO = counselorService.login(counselorDTO);
        // logger.info("@@ PJH >> "+dataUtil.toJSONString(counselorInfoDTO));
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.READ_SUCCESS, counselorInfoDTO), HttpStatus.OK);
    }

    /**
     * PJH
     * - 상담사 로그아웃
     * @param id
     */
    @GetMapping(value = "/counselor/logout")
    public ResponseEntity<Object> logout(@RequestParam(value="id") String id) {
        logger.info("[GetMapping] - /counselor/logout");
        counselorService.logout(id);
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.LOGOUT_SUCCESS), HttpStatus.OK);
    }

    /**
     * PJH
     * - 전체 관 정보 가져오기
     */
    @GetMapping(value = "/sector/list")
    public ResponseEntity<Object> sectorList() {
        logger.info("[GetMapping] - /sectorList");
        List<SectorDTO> sectorList = sectorService.findSectorList();
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.READ_SUCCESS, sectorList), HttpStatus.OK);
    }

    /**
     * PJH
     * - 전체 상담사 조회
     */
    @GetMapping(value = "/counselor/list")
    public ResponseEntity<Object> counselorList() {
        logger.info("[GetMapping] - /counselor/list");
        List<CounselorInfoDTO> counselorList = counselorService.counselorList();
        return new ResponseEntity<Object>(ResponseDataUtil.res(StatusMessage.READ_SUCCESS, counselorList), HttpStatus.OK);
    }
}
