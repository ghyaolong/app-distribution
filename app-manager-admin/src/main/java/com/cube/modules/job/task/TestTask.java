/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.cube.modules.job.task;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
@Component("testTask")
public class TestTask implements ITask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String params){
		log.info("TestTask定时任务正在执行，参数为：{}", params);
		log.debug("TestTask定时任务正在执行，参数为：{}", params);
		logger.debug("TestTask定时任务正在执行，参数为：{}", params);
	}
}
