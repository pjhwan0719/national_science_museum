package com.avad.nationalScienceMuseum.counsel.service.impl;

import com.avad.nationalScienceMuseum.counsel.domain.SectorDTO;
import com.avad.nationalScienceMuseum.counsel.domain.SectorEntity;
import com.avad.nationalScienceMuseum.counsel.repository.SectorRepository;
import com.avad.nationalScienceMuseum.counsel.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;

    @Override
    public List<SectorDTO> findSectorList() {
        List<SectorEntity> sectorEntityList = sectorRepository.findAll();
        return sectorEntityList.stream()
                .map(SectorEntity::toSectorDTO)
                .collect(Collectors.toList());
    }
}
