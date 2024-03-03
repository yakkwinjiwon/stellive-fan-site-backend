package com.stellive.fansite.service;

import com.stellive.fansite.domain.Channel;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.stellive.fansite.utils.YoutubeApiConst.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class YoutubeApiServiceTest {

    @Autowired
    YoutubeApiService youtubeApiService;

    @Test
    void testDataInitTest() {
        channelDataInitTest();
    }

    @Test
    void updateChannelTest() {
        Channel official = youtubeApiService.updateChannel(CHANNEL_ID_OFFICIAL);

        assertThat(official.getExternalId()).isEqualTo(CHANNEL_ID_OFFICIAL);
    }

    @Test
    void findChannelByIdTest() {
        Channel official = youtubeApiService.findChannelById(7L);

        assertThat(official.getExternalId()).isEqualTo(CHANNEL_ID_OFFICIAL);
    }

    @Test
    void updateAllChannelsTest() {

    }

    private void channelDataInitTest() {
        Channel channel1 = youtubeApiService.findChannelById(1L);
        Channel channel2 = youtubeApiService.findChannelById(2L);
        Channel channel3 = youtubeApiService.findChannelById(3L);
        Channel channel4 = youtubeApiService.findChannelById(4L);
        Channel channel5 = youtubeApiService.findChannelById(5L);
        Channel channel6 = youtubeApiService.findChannelById(6L);
        Channel channel7 = youtubeApiService.findChannelById(7L);

        assertThat(channel1.getExternalId()).isEqualTo(CHANNEL_ID_KANNA);
        assertThat(channel2.getExternalId()).isEqualTo(CHANNEL_ID_YUNI);
        assertThat(channel3.getExternalId()).isEqualTo(CHANNEL_ID_HINA);
        assertThat(channel4.getExternalId()).isEqualTo(CHANNEL_ID_MASHIRO);
        assertThat(channel5.getExternalId()).isEqualTo(CHANNEL_ID_LIZE);
        assertThat(channel6.getExternalId()).isEqualTo(CHANNEL_ID_TABI);
        assertThat(channel7.getExternalId()).isEqualTo(CHANNEL_ID_OFFICIAL);
    }
}