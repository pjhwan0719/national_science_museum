package com.avad.nationalScienceMuseum.videoSupport.service;

import com.avad.nationalScienceMuseum.videoSupport.domain.VideoSupportHistDTO;

import java.util.List;

public interface VideoSupportService {

    /**
     * PJH
     * - 화상지원 이력
     */
    List<VideoSupportHistDTO> getVideoSupportHistory();

    /**
     * PJH
     * - 화상지원 이력 저장
     */
    VideoSupportHistDTO saveVideoSupportHistory(VideoSupportHistDTO videoSupportHistDTO);
}
