package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.common.utils.PageUtils;
import com.cube.entity.MemberEntity;

import java.util.Map;

/**
 * 会员表
 *
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

