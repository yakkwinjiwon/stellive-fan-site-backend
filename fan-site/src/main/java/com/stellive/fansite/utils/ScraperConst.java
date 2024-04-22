package com.stellive.fansite.utils;

import java.time.Duration;
import java.util.regex.Pattern;

public class ScraperConst {

    /**
     * Driver config
     */
    public static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    public static final String CHROME_DRIVER_PATH = "C:\\chromedriver-win32\\chromedriver.exe";
    public static final Duration WAIT_TIMEOUT = Duration.ofSeconds(5);

    /**
     * URL
     */
    public static final String URL_NEWS = "https://stellive.me/news";
    public static final String URL_MUSIC = "https://stellive.me/music";

    /**
     * CSS SELECTOR
     */
    public static final String CSS_SELECTOR_NEWS_LIST = ".bh.bh_item.item";
    public static final String CSS_SELECTOR_NEWS_URL = ".bh_img_content a";
    public static final String CSS_SELECTOR_NEWS_IMG_URL = ".bh_img_content a img";
    public static final String CSS_SELECTOR_NEWS_TITLE = ".bh_title.ff-nn .title span";
    public static final String CSS_SELECTOR_NEWS_PUBLISH_TIME = ".bh_content .ff-nn";
    public static final String CSS_SELECTOR_MUSIC_LIST = ".bh.bh_item.item a";
    public static final String CSS_SELECTOR_MUSIC_MORE = ".more_btn.ds-f.jc-c.hide";
    public static final String CSS_SELECTOR_MUSIC_IMG = ".youtube_converted img";

    /**
     * Attribute
     */
    public static final String ATTRIBUTE_SRC = "src";
    public static final String ATTRIBUTE_HREF = "href";

    /**
     * Value
     */
    public static final Integer NEWS_LIMIT = 8;

    /**
     * Pattern
     */

    public static final Pattern PATTERN_MUSIC_ID = Pattern.compile("https://img.youtube.com/vi/[a-zA-Z0-9]+/mqdefault.jpg");
}