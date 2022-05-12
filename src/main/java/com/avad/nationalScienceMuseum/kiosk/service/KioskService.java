package com.avad.nationalScienceMuseum.kiosk.service;

import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoDTO;

import java.util.List;

public interface KioskService {

    /**
     * PJH
     * - Kiosk 등록
     * @param kioskInfoDTO
     */
    public void createKiosk(KioskInfoDTO kioskInfoDTO);

    /**
     * PJH
     * - Kiosk 화상지원 상태값 수정
     * @param kioskInfoDTO
     */
    public void saveKioskVideoSupportStatus(KioskInfoDTO kioskInfoDTO);

    /**
     * PJH
     * - Kiosk 찾기 (ID)
     */
    public KioskInfoDTO findKioskByKioskId(String kiosk_id);

    /**
     * PJH
     * - Kiosk 정보 수정
     * @param kioskInfoDTO
     */
    public void saveKioskInfo (KioskInfoDTO kioskInfoDTO);

    /**
     * PJH
     * - Kiosk 정보 삭제
     * @param kioskInfoDTO
     */
    public void delete(KioskInfoDTO kioskInfoDTO);

    /**
     * PJH
     * - Kiosk 찾기 By Sector
     * @param sectorCodeList
     */
    public List<KioskInfoDTO> finKioskBySector(List<String> sectorCodeList);

    /**
     * PJH
     * - Kiosk 찾기
     * @return
     */
    public List<KioskInfoDTO> findKiosk();
}
