package com.avad.nationalScienceMuseum.counsel.service;

import com.avad.nationalScienceMuseum.counsel.domain.CounselorDTO;
import com.avad.nationalScienceMuseum.counsel.domain.CounselorInfoDTO;

import java.util.List;

public interface CounselorService {

    /**
     * PJH
     * - 상담사 등록
     * @param counselorDTO
     * @return
     */
    public void join(CounselorDTO counselorDTO);

    /**
     * PJH
     * - 상담사 정보 수정
     * @param counselorDTO
     */
    public void update(CounselorDTO counselorDTO);

    /**
     * PJH
     * - 상담사 삭제
     * @param counselorDTO
     */
    public void delete(CounselorDTO counselorDTO);

    /**
     * PJH
     * - 상담사 로그인
     * @param counselorDTO
     */
    public CounselorInfoDTO login(CounselorDTO counselorDTO);

    /**
     * PJH
     * - 상담사 로그아웃
     * @param id
     */
    public void logout(String id);

    /**
     * PJH
     * - 전체 상담사 조회
     */
    public List<CounselorInfoDTO> counselorList();

    /**
     * PJH
     * - 상담사 조회
     * @param counselor_id
     */
    public CounselorInfoDTO findCounselorByCounselorId(String counselor_id);
}
