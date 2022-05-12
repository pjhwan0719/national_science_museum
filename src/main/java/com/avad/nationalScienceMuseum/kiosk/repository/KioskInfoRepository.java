package com.avad.nationalScienceMuseum.kiosk.repository;

import com.avad.nationalScienceMuseum.kiosk.domain.KioskInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KioskInfoRepository extends JpaRepository<KioskInfoEntity, Long> {

    // kiosk ID 유효성 체크
    @Query(value= "SELECT ki FROM KioskInfoEntity AS ki WHERE ki.id = :id")
    Optional<KioskInfoEntity> findKioskByKioskId(@Param("id") String id);

    // Kiosk 찾기 By sector_code
    @Query(value= "SELECT ki FROM KioskInfoEntity AS ki WHERE ki.sectorEntity.code IN :sectorCodeList")
    List<KioskInfoEntity> findKioskBySector(@Param("sectorCodeList") List<String> sectorCodeList);
}
