/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.cube.service.impl;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.dao.TokenDao;
import com.cube.entity.TokenEntity;
import com.cube.service.TokenService;
import com.cube.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("tokenService")
public class TokenServiceImpl extends ServiceImpl<TokenDao, TokenEntity> implements TokenService {
    /**
     * 12小时后过期
     */
    private final static int EXPIRE = 3600 * 12;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public TokenEntity queryByToken(String token) {
        return this.getOne(new QueryWrapper<TokenEntity>().eq("token", token));
    }

    @Override
    public TokenEntity createToken(MemberVo memberVo) {
        //当前时间
        //Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        String email = memberVo.getUserName();
        TokenEntity tokenEntity = new TokenEntity();

        String tokenResult = (String) redisTemplate.boundValueOps("token:" + email).get();
        if (StringUtils.isEmpty(tokenResult)) {
            String token = JWT.create().withAudience(JSON.toJSONString(memberVo)).sign(Algorithm.HMAC256(memberVo.getUserName()));
            //保存或更新用户token
            tokenEntity.setUserId(email);
            tokenEntity.setToken(token);
            redisTemplate.boundValueOps("token:" + email).set(token, EXPIRE, TimeUnit.SECONDS);
            log.info("{}的token过期时间{}秒[{}],", email, EXPIRE, token);
        } else {

            redisTemplate.boundValueOps("token:" + email).set(tokenResult, EXPIRE, TimeUnit.SECONDS);
            log.info("{}的token过期时间{}秒[{}],", email, EXPIRE, tokenResult);
        }
        return tokenEntity;
    }

    @Override
    public void expireToken(String userId) {
        Date now = new Date();

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(now);
        this.saveOrUpdate(tokenEntity);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
