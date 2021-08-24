package com.yunlian.oauth.service.impl;

import com.changgou.entity.Result;
import com.changgou.user.feign.UserFeign;
import com.yunlian.oauth.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;

public class OauthServiceImpl implements OauthService {
    @Autowired
    private UserFeign userFeign;
    @Override
    public Result getAll() {
        return userFeign.getAll();
    }
}
