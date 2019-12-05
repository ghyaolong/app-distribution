package com.cube.modules.app.dao;

import com.cube.modules.app.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员管理
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-12-05 10:57:43
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
