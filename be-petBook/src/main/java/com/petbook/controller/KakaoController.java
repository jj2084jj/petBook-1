package com.petbook.controller;


import com.petbook.config.ConfigUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
@Log4j2
public class KakaoController {

    private final ConfigUtils configUtils;

    public KakaoController(ConfigUtils configUtils) {
        this.configUtils = configUtils;
    }

    @GetMapping(value = "/kakao/login")
    public ResponseEntity<Object> moveKakaoInitUrl() {
        String authUrl = configUtils.kakaoInitUrl();
        URI redirectUri = null;
        try {
            redirectUri = new URI(authUrl);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/kakao/login/oauth2")
    public ResponseEntity<Object> redirectKakaoLogin(
            @RequestParam(value = "code") String authCode
    ){
        log.info("kakaoCode : "+ authCode);

        return ResponseEntity.ok().body(authCode);
    }
}
