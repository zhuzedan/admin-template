package com.zzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzd.domain.SystemLoginLog;
import com.zzd.domain.SystemOperLog;
import com.zzd.vo.SystemLoginLogQueryVo;
import com.zzd.vo.SystemOperLogQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;


/**
 * 系统访问记录(SystemLoginLog)表数据库访问层
 *
 * @author zzd
 * @since 2022-11-29 10:33:32
 */
@Repository
@Mapper
public interface SystemLoginLogMapper extends BaseMapper<SystemLoginLog> {
    IPage<SystemLoginLog> selectPage(Page<SystemLoginLog> page, @Param("vo") SystemLoginLogQueryVo systemLoginLogQueryVo);
}

