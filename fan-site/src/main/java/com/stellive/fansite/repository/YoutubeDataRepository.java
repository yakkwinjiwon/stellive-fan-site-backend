package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Channel;
import com.stellive.fansite.domain.ChannelId;
import com.stellive.fansite.domain.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class YoutubeDataRepository implements YoutubeRepository {

    private final YoutubeChannelDataRepository channelRepository;
    private final YoutubeVideoDataRepository videoRepository;

    @Override
    public Channel saveChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public Optional<Channel> findChannelById(Long id) {
        return channelRepository.findById(id);
    }

    @Override
    public List<Channel> findAllChannels() {
        return channelRepository.findAll();
    }

    @Override
    public List<Video> saveVideos(List<Video> videos) {
        return videoRepository.saveAll(videos);
    }

    @Override
    public List<Video> findVideosByChannelId(Long id) {
        return videoRepository.findByChannelId(id);
    }


}
