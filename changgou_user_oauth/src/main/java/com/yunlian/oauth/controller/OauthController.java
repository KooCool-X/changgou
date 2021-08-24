package com.yunlian.oauth.controller;

import com.yunlian.oauth.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/oauth")
public class OauthController {
    @Autowired
    private OauthService oauthService;
}
