package com.avad.nationalScienceMuseum.counsel.repository;

import com.avad.nationalScienceMuseum.counsel.domain.CounselorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CounselorRepository extends JpaRepository<CounselorEntity, Long> {

    // 상담사 ID 유효성 체크
    @Query(value= "SELECT c FROM CounselorEntity AS c WHERE c.id = :id")
    Optional<CounselorEntity> validateDuplicateCounselorId(@Param("id") String id);

    // 상담사 찾기 (ID)
    @Query(value= "SELECT c FROM CounselorEntity AS c WHERE c.id = :id")
    Optional<CounselorEntity> findCounselorByCounselorId(@Param("id") String id);

    // 상담사 계정 조회 by- ID, PW
    @Query(value= "SELECT DISTINCT c FROM CounselorEntity AS c JOIN FETCH c.counselorSectorEntityList WHERE c.id = :#{#counselorEntity.id} AND c.pw = :#{#counselorEntity.pw}")
    Optional<CounselorEntity> loginCounselor(@Param("counselorEntity") CounselorEntity counselorEntity);

}
