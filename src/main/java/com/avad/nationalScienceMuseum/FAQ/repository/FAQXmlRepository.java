package com.avad.nationalScienceMuseum.FAQ.repository;

import com.avad.nationalScienceMuseum.FAQ.domain.FAQXmlEntity;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FAQXmlRepository extends JpaRepository<FAQXmlEntity, Long> {

    // 최신 xml 파일 찾기
    @Query(value= "SELECT fx FROM FAQXmlEntity AS fx WHERE fx.language = :language ORDER BY fx.xml_index DESC")
    List<FAQXmlEntity> findNewestXmlByLanguage(@Param("language") String language, Pageable pageable);
}
