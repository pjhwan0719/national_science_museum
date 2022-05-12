package com.avad.nationalScienceMuseum.videoSupport.service.impl;

import com.avad.nationalScienceMuseum.common.DataUtil;
import com.avad.nationalScienceMuseum.counsel.repository.CounselorRepository;
import com.avad.nationalScienceMuseum.kiosk.repository.KioskInfoRepository;
import com.avad.nationalScienceMuseum.videoSupport.domain.VideoSupportHistDTO;
import com.avad.nationalScienceMuseum.videoSupport.domain.VideoSupportHistEntity;
import com.avad.nationalScienceMuseum.videoSupport.repository.VideoSupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.avad.nationalScienceMuseum.videoSupport.service.VideoSupportService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoSupportServiceImpl implements VideoSupportService{

    private final VideoSupportRepository videoSupportRepository;
    private final CounselorRepository counselorRepository;
    private final KioskInfoRepository kioskInfoRepository;
    private final DataUtil dataUtil;

    @Transactional
    @Override
    public List<VideoSupportHistDTO> getVideoSupportHistory() {
        return videoSupportRepository.findAll()
                .stream()
                .map(VideoSupportHistEntity::toVideoSupportHistDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public VideoSupportHistDTO saveVideoSupportHistory(VideoSupportHistDTO videoSupportHistDTO) {
        VideoSupportHistEntity videoSupportHistEntity = videoSupportRepository.save(videoSupportHistDTO.toVideoSupportHistEntity());
        videoSupportHistDTO.setSeq(videoSupportHistEntity.getSeq());
        return videoSupportHistDTO;
//        return videoSupportRepository
//                .save(videoSupportHistDTO.toVideoSupportHistEntity())
//                .toVideoSupportHistDTO();
    }
}
