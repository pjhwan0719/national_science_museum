package com.avad.nationalScienceMuseum.counsel.service;

import com.avad.nationalScienceMuseum.counsel.domain.SectorDTO;

import java.util.List;

public interface SectorService {

    /**
     * PJH
     * - Sector List
     * @return List<SectorDTO>
     */
    public List<SectorDTO> findSectorList();
}
