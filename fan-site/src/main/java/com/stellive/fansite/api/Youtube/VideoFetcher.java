package com.stellive.fansite.api.Youtube;

import com.stellive.fansite.domain.*;
import com.stellive.fansite.dto.etc.VideoResult;
import com.stellive.fansite.dto.video.*;
import com.stellive.fansite.repository.Channel.ChannelRepo;
import com.stellive.fansite.utils.ApiConst;
import com.stellive.fansite.utils.AppConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.stellive.fansite.utils.ApiConst.*;
import static com.stellive.fansite.utils.AppConst.*;

@Service
@RequiredArgsConstructor
public class VideoFetcher {

    private final VideoConnector videoConnector;

    private final ChannelRepo channelRepo;


    public List<Video> fetchVideos(List<String> externalIds,
                                   VideoType videoType){
        List<Video> videos = new ArrayList<>();

        for(int i = 0; i < externalIds.size(); i += MAX_RESULTS_ALL){
            List<String> subList = externalIds.subList(i, Math.min(i + MAX_RESULTS_ALL, externalIds.size()));
            List<Video> fetchedVideos = fetchVideo(subList, videoType);
            videos.addAll(fetchedVideos);
        }

        return videos;
    }
    public List<Video> fetchVideo(List<String> externalIds,
                                  VideoType videoType) {
        VideoList list = videoConnector.callVideo(externalIds);
        return buildVideo(list, videoType);
    }

    private List<Video> buildVideo(VideoList list,
                                   VideoType videoType) {
        return getItems(list).stream()
                .map(item -> Video.builder()
                        .externalId(getId(item))
                        .channel(getChannel(item))
                        .duration(getDuration(item))
                        .scheduledStartTime(getScheduledStartTime(item))
                        .publishTime(getPublishTime(item))
                        .title(getTitle(item))
                        .liveStatus(getLiveStatus(item))
                        .thumbnailUrl(getThumbnailUrl(item))
                        .viewCount(getViewCount(item))
                        .videoType(determineVideoType(item, videoType))
                        .build())
                .toList();

    }

    private String getId(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getId)
                .orElse(STRING_DEFAULT);
    }

    private Channel getChannel(VideoItem item) {
        String channelId = Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getChannelId)
                .orElse(STRING_DEFAULT);
        return channelRepo.findByExternalId(channelId).orElse(null);
    }

    private List<VideoItem> getItems(VideoList list) {
        return Optional.ofNullable(list)
                .map(VideoList::getItems)
                .orElse(new ArrayList<>());
    }

    private Long getDuration(VideoItem item) {
        String duration = Optional.ofNullable(item)
                .map(VideoItem::getContentDetails)
                .map(VideoContentDetails::getDuration)
                .orElse(DURATION_DEFAULT);
        return Duration.parse(duration).getSeconds();
    }

    private Instant getScheduledStartTime(VideoItem item) {
        String scheduledStartTime = Optional.ofNullable(item)
                .map(VideoItem::getLiveStreamingDetails)
                .map(VideoLiveStreamingDetails::getScheduledStartTime)
                .orElse(INSTANT_DEFAULT);
        return Instant.parse(scheduledStartTime);
    }

    private Instant getPublishTime(VideoItem item) {
        String publishedAt = Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getPublishedAt)
                .orElse(INSTANT_DEFAULT);
        return Instant.parse(publishedAt);
    }

    private String getChannelId(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getChannelId)
                .orElse(STRING_DEFAULT);
    }

    private String getTitle(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getTitle)
                .orElse(STRING_DEFAULT);
    }

    private LiveStatus getLiveStatus(VideoItem item) {
        String liveBroadcastContent = Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getLiveBroadcastContent)
                .orElse(STRING_DEFAULT);
        return LiveStatus.fromString(liveBroadcastContent);
    }

    private String getThumbnailUrl(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getSnippet)
                .map(VideoSnippet::getThumbnails)
                .map(VideoThumbnails::getHigh)
                .map(VideoThumbnail::getUrl)
                .orElse(STRING_DEFAULT);
    }

    private Integer getViewCount(VideoItem item) {
        return Optional.ofNullable(item)
                .map(VideoItem::getStatistics)
                .map(VideoStatistics::getViewCount)
                .orElse(0);
    }

    private VideoType determineVideoType(VideoItem item,
                                         VideoType videoType) {
        if (videoType == VideoType.UNKNOWN) {
            return (getDuration(item) <= 60) ? VideoType.SHORTS : VideoType.VIDEO;
        }
        return videoType;
    }
}
