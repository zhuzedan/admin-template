package com.zzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzd.domain.SystemRole;
import com.zzd.result.ResponseResult;
import com.zzd.vo.SystemRoleQueryVo;
import org.apache.ibatis.annotations.Param;


/**
 * 角色(SystemRole)表数据库访问层
 *
 * @author zzd
 * @since 2022-11-17 22:03:04
 */
public interface SystemRoleMapper extends BaseMapper<SystemRole> {
    IPage<SystemRole> selectPage(Page<SystemRole> page, @Param("vo") SystemRoleQueryVo roleQueryVo);
}

