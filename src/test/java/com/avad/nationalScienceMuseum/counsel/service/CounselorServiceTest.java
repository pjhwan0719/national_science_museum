package com.avad.nationalScienceMuseum.counsel.service;

import com.avad.nationalScienceMuseum.common.DateUtil;
import com.avad.nationalScienceMuseum.counsel.domain.CounselorDTO;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class CounselorServiceTest {


    private final CounselorService counselorService;

    @Test
    void join() {
        CounselorDTO c = new CounselorDTO();
        c.setId("TEST");
        c.setPw("1234");
        c.setName("홍길동");
        c.setIs_main("Y");
        c.setIs_use("Y");
        c.setInsert_date(new DateUtil().getFullCurrentDate());
        c.setUpdate_date(new DateUtil().getFullCurrentDate());
        c.setSectors("sector_001@sector_002@sector_003@sector_004");

        counselorService.join(c);
    }

    @Test
    void login() {
    }
}