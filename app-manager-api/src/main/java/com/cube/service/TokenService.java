/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cube.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cube.entity.TokenEntity;
import com.cube.vo.MemberVo;

/**
 * 用户Token
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface TokenService extends IService<TokenEntity> {

	TokenEntity queryByToken(String token);

	/**
	 * 生成token
	 * @param memberVo  用户ID
	 * @return        返回token信息
	 */
	TokenEntity createToken(MemberVo memberVo);

	/**
	 * 设置token过期
	 * @param userId 用户ID
	 */
	void expireToken(String userId);

}
