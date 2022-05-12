package com.avad.nationalScienceMuseum.kiosk.service.Impl;

import com.avad.nationalScienceMuseum.common.DataUtil;
import com.avad.nationalScienceMuseum.exception.CustomException;
import com.avad.nationalScienceMuseum.exception.domain.ErrorCode;
import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoDTO;
import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoEntity;
import com.avad.nationalScienceMuseum.kiosk.repository.KioskInfoRepository;
import com.avad.nationalScienceMuseum.kiosk.service.KioskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class kioskServiceImpl implements KioskService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KioskInfoRepository kioskInfoRepository;
    private final DataUtil dataUtil;

    @Transactional
    @Override
    public void createKiosk(KioskInfoDTO kioskInfoDTO) {
        Optional<KioskInfoEntity> kioskInfoEntity = kioskInfoRepository.findKioskByKioskId(kioskInfoDTO.getId());
        if (kioskInfoEntity.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_ID);
        }
        else {
            kioskInfoRepository.save(kioskInfoDTO.toKioskInfoEntity());
        }
    }

    @Transactional
    @Override
    public void saveKioskVideoSupportStatus(KioskInfoDTO kioskInfoDTO) {
        kioskInfoRepository.save(kioskInfoDTO.toKioskInfoEntity());
    }

    @Transactional
    @Override
    public KioskInfoDTO findKioskByKioskId(String kiosk_id) {
        Optional<KioskInfoEntity> kioskInfoEntity = kioskInfoRepository.findKioskByKioskId(kiosk_id);
        return kioskInfoEntity
                .map(KioskInfoEntity::toKioskInfoDTO)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_KIOSK));
    }

    @Transactional
    @Override
    public void saveKioskInfo(KioskInfoDTO kioskInfoDTO) {
        Optional<KioskInfoEntity> optKioskInfoEntity = kioskInfoRepository.findById(kioskInfoDTO.getSeq());
        if (!optKioskInfoEntity.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_KIOSK);
        }
        else {
            optKioskInfoEntity.get().change_ip(kioskInfoDTO.getIp());
            optKioskInfoEntity.get().change_sectorEntity(kioskInfoDTO.getSectorDTO().toSectorEntity());
            optKioskInfoEntity.get().change_updateDate(kioskInfoDTO.getUpdate_date());
        }
    }

    @Override
    public void delete(KioskInfoDTO kioskInfoDTO) {
        Optional<KioskInfoEntity> optKioskInfoEntity = kioskInfoRepository.findById(kioskInfoDTO.getSeq());
        if (!optKioskInfoEntity.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_KIOSK);
        }
        else {
            optKioskInfoEntity.get().change_isDel(kioskInfoDTO.getIs_del());
            optKioskInfoEntity.get().change_updateDate(kioskInfoDTO.getUpdate_date());
        }
    }

    @Transactional
    @Override
    public List<KioskInfoDTO> finKioskBySector(List<String> sectorCodeList) {
        List<KioskInfoEntity> kioskInfoEntityList = kioskInfoRepository.findKioskBySector(sectorCodeList);
        if (kioskInfoEntityList.size() < 1) {
            throw new CustomException(ErrorCode.NOT_FOUND_KIOSK);
        }
        else {
             return kioskInfoEntityList.stream().map(KioskInfoEntity::toKioskInfoDTO).collect(Collectors.toList());
        }
    }

    @Override
    public List<KioskInfoDTO> findKiosk() {
        return kioskInfoRepository.findAll()
                .stream()
                .filter(kiosk -> kiosk.getIs_del().equalsIgnoreCase("N"))
                .map(KioskInfoEntity::toKioskInfoDTO)
                .collect(Collectors.toList());
    }
}