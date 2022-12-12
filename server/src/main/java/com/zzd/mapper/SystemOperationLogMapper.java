package com.zzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzd.domain.SystemOperationLog;
import com.zzd.vo.SystemOperationLogQueryVo;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志记录(SystemOperLog)表数据库访问层
 *
 * @author zzd
 * @since 2022-11-28 13:09:27
 */
public interface SystemOperationLogMapper extends BaseMapper<SystemOperationLog> {

    IPage<SystemOperationLog> selectPage(Page<SystemOperationLog> page, @Param("vo") SystemOperationLogQueryVo sysOperLogQueryVo);
}

