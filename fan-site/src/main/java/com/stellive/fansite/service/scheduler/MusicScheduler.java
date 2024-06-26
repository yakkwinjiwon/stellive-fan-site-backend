package com.stellive.fansite.service.scheduler;

import com.stellive.fansite.api.Youtube.VideoFetcher;
import com.stellive.fansite.domain.Video;
import com.stellive.fansite.domain.VideoType;
import com.stellive.fansite.repository.Video.VideoRepo;
import com.stellive.fansite.scraper.MusicScraper;
import com.stellive.fansite.utils.ScraperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicScheduler {

    private final MusicScraper musicScraper;
    private final VideoFetcher videoFetcher;

    private final VideoRepo videoRepo;

    public List<Video> updateMusics(WebDriver driver,
                                    WebDriverWait wait,
                                    Integer limit) {
        log.info("Update Musics");

        List<String> scrapedMusicIds = new ArrayList<>();
        ScraperUtils.executeWithHandling(() -> {
            scrapedMusicIds.addAll(musicScraper.scrapeMusicIds(driver, wait, limit));
        });
        log.info("scraped MusicIds={}", scrapedMusicIds);

        List<Video> fetchedVideos = videoFetcher.fetchVideos(scrapedMusicIds, VideoType.MUSIC);
        log.info("fetched Videos={}, size={}", fetchedVideos.getFirst(), fetchedVideos.size());

        List<Video> updatedMusics = videoRepo.save(fetchedVideos);
        log.info("updated Musics={}, size={}", updatedMusics.getFirst(), updatedMusics.size());
        return updatedMusics;

    }
}
