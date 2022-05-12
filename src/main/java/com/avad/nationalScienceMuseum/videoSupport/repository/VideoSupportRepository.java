package com.avad.nationalScienceMuseum.videoSupport.repository;

import com.avad.nationalScienceMuseum.videoSupport.domain.VideoSupportHistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoSupportRepository extends JpaRepository<VideoSupportHistEntity, Long> {

}
