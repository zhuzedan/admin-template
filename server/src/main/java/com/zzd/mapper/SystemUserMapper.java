package com.zzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzd.domain.SystemRole;
import com.zzd.domain.SystemUser;
import com.zzd.vo.SystemUserQueryVo;
import org.apache.ibatis.annotations.Param;


/**
 * 用户表(SystemUser)表数据库访问层
 *
 * @author zzd
 * @since 2022-11-18 16:02:54
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    IPage<SystemUser> selectUserPage(Page<SystemUser> pageParam, @Param("vo") SystemUserQueryVo systemUserQueryVo);
}

