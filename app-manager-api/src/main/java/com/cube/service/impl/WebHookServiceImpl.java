package com.cube.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cube.common.utils.PageUtils;
import com.cube.common.utils.Query;

import com.cube.dao.WebHookDao;
import com.cube.entity.WebHookEntity;
import com.cube.service.WebHookService;


@Service("webHookService")
public class WebHookServiceImpl extends ServiceImpl<WebHookDao, WebHookEntity> implements WebHookService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WebHookEntity> page = this.page(
                new Query<WebHookEntity>().getPage(params),
                new QueryWrapper<WebHookEntity>()
        );

        return new PageUtils(page);
    }

}
