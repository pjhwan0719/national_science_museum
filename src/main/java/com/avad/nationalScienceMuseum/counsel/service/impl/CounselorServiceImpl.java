package com.avad.nationalScienceMuseum.counsel.service.impl;

import com.avad.nationalScienceMuseum.common.DataUtil;
import com.avad.nationalScienceMuseum.common.DateUtil;
import com.avad.nationalScienceMuseum.counsel.domain.*;
import com.avad.nationalScienceMuseum.counsel.repository.CounselorRepository;
import com.avad.nationalScienceMuseum.counsel.repository.CounselorSectorRepository;
import com.avad.nationalScienceMuseum.counsel.repository.SectorRepository;
import com.avad.nationalScienceMuseum.counsel.service.CounselorService;
import com.avad.nationalScienceMuseum.exception.CustomException;
import com.avad.nationalScienceMuseum.exception.domain.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounselorServiceImpl implements CounselorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CounselorRepository counselorRepository;
    private final CounselorSectorRepository counselorSectorRepository;
    private final SectorRepository sectorRepository;
    private final DateUtil dateUtil;
    private final DataUtil dataUtil;

    @Transactional
    @Override
    public void join(CounselorDTO counselorDTO) {
        // 중복체크
        Optional<CounselorEntity> optCounselorEntity = counselorRepository.validateDuplicateCounselorId(counselorDTO.getId());
        if (optCounselorEntity.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_ID);
        }
        else {
            // CounselorEntity save
            CounselorEntity counselorEntity = counselorRepository.save(counselorDTO.toCounselorEntity());
            CounselorDTO savedCounselorDTO = counselorEntity.toCounselorDTO();

            // sectors split
            for (String sectorCode : counselorDTO.getSectors().split("@")) {
                // SectorDTO 객체 생성
                SectorDTO sectorDTO = new SectorDTO();
                sectorDTO.setCode(sectorCode);

                // counselorSectorDTO 객체 생성
                CounselorSectorDTO counselorSectorDTO = new CounselorSectorDTO();
                counselorSectorDTO.setCounselorDTO(savedCounselorDTO);
                counselorSectorDTO.setSectorDTO(sectorDTO);

                // counselorSectorEntity 저장
                // 저장하기전에 select 가 한번 일어난다..?
                // 찾아보니.. JPA는 update 가 따로 없어서 save 할때 insert/update를 관리한다고한다.
                // 식별자로 해당 Entity를 찾아서 Entity에 변경사항이 있으면 update, 없으면 insert를 한다고한다.
                // 근대 왜...
                // CounselorSectorEntity를 Save했는데 SectorEntity가 select 되는거지..??
                // 연관관계 때문인가..? (CounselorSectorEntity <-> SectorEntity [N:1])
                counselorSectorRepository.save(counselorSectorDTO.toCounselorSectorEntity());
            }
        }
    }

    @Transactional
    @Override
    public void update(CounselorDTO counselorDTO) {
        Optional<CounselorEntity> optCounselorEntity = counselorRepository.findById(counselorDTO.getSeq());
        if (!optCounselorEntity.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_COUNSELOR);
        }
        else {
            CounselorEntity counselorEntity = optCounselorEntity.get();
            // 상담사 정보 수정
            // 비밀번호가 ""값이 아닐때만 수정
            if (!(counselorDTO.getPw().equalsIgnoreCase(""))) {
                counselorEntity.change_pw(counselorDTO.getPw());
            }
            counselorEntity.change_name(counselorDTO.getName());
            counselorEntity.change_isMain(counselorDTO.getIs_main());
            counselorEntity.change_isUse(counselorDTO.getIs_use());
            counselorEntity.change_isLogin(counselorDTO.getIs_login());
            counselorEntity.change_updateDate(dateUtil.getFullCurrentDate());

            // 기존 상담사 sector 삭제
            counselorEntity.getCounselorSectorEntityList()
                    .forEach(csEntity -> {
                        counselorSectorRepository.deleteById(csEntity.getSeq());
                    });
            // 연관관계로 인해 counselorEntity 에서 참조하고있는 counselorSectorEntity list 를 초기화한다.
            // 이거 안하면 삭제 안됨.
            counselorEntity.getCounselorSectorEntityList().clear();

            // 새로운 sector 등록
            Arrays.stream(counselorDTO.getSectors().split("@"))
                    .forEach(sectorCode -> {
                        SectorDTO sectorDTO = new SectorDTO();
                        sectorDTO.setCode(sectorCode);

                        CounselorSectorDTO counselorSectorDTO = new CounselorSectorDTO();
                        counselorSectorDTO.setCounselorDTO(counselorEntity.toCounselorDTO());
                        counselorSectorDTO.setSectorDTO(sectorDTO);

                        counselorSectorRepository.save(counselorSectorDTO.toCounselorSectorEntity());
                    });
        }
    }

    @Transactional
    @Override
    public void delete(CounselorDTO counselorDTO) {
        // counselorRepository.deleteById(seq);
        Optional<CounselorEntity> optCounselorEntity = counselorRepository.findById(counselorDTO.getSeq());
        if(!optCounselorEntity.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_COUNSELOR);
        }
        else {
            optCounselorEntity.get().change_isDel(counselorDTO.getIs_del());
            optCounselorEntity.get().change_updateDate(dateUtil.getFullCurrentDate());
        }
    }

    @Transactional
    @Override
    public CounselorInfoDTO login(CounselorDTO counselorDTO) {
        Optional<CounselorEntity> optCounselorEntity = counselorRepository.loginCounselor(counselorDTO.toCounselorEntity());
        if (!optCounselorEntity.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_COUNSELOR);
        }
        else {
            CounselorEntity counselorEntity = optCounselorEntity.get();
            if (counselorEntity.getIs_login().equalsIgnoreCase("Y")) {
                throw new CustomException(ErrorCode.ALREADY_LOGIN_IN);
            }
            if (counselorEntity.getIs_del().equalsIgnoreCase("Y")) {
                throw new CustomException(ErrorCode.NOT_FOUND_COUNSELOR);
            }
            else {
                counselorEntity.change_isLogin("Y");
                return counselorEntity.toCounselorInfoDTO();
            }
        }
    }

    @Transactional
    @Override
    public void logout(String id) {
        Optional<CounselorEntity> optionalCounselorEntity = counselorRepository.findCounselorByCounselorId(id);
        if (!optionalCounselorEntity.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_COUNSELOR);
        }
        else {
            CounselorEntity counselorEntity = optionalCounselorEntity.get();
            counselorEntity.change_isLogin("N");
        }
    }

    @Transactional
    @Override
    public List<CounselorInfoDTO> counselorList() {
        return counselorRepository.findAll()
                .stream()
                .filter(counselorEntity -> counselorEntity.getIs_del().equalsIgnoreCase("N"))
                .map(CounselorEntity::toCounselorInfoDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CounselorInfoDTO findCounselorByCounselorId(String counselor_id) {
        Optional<CounselorEntity> optCounselorEntity = counselorRepository.findCounselorByCounselorId(counselor_id);
        return optCounselorEntity
                .map(CounselorEntity::toCounselorInfoDTO)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_COUNSELOR));
    }
}