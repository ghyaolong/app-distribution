package com.cube.dao;

import com.cube.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员表
 * 
 * @author tututu
 * @email 289911401@qq.com
 * @date 2019-11-20 17:42:17
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
