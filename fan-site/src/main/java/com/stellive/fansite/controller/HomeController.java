package com.stellive.fansite.controller;

import com.stellive.fansite.service.YoutubeApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/")
public class HomeController {

    private final YoutubeApiService youtubeApiService;

    @GetMapping("")
    public String home() {
        log.info("home");
        youtubeApiService.test();
        return "ok";
    }
}