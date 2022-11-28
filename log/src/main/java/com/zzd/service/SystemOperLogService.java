package com.zzd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzd.domain.SystemOperLog;
import com.zzd.vo.SystemOperLogQueryVo;


/**
 * 操作日志记录(SystemOperLog)表服务接口
 *
 * @author zzd
 * @since 2022-11-28 13:09:36
 */
public interface SystemOperLogService extends IService<SystemOperLog> {

    void saveSysLog(SystemOperLog sysOperLog);

    //操作日志分页查询
    IPage<SystemOperLog> selectPage(Page<SystemOperLog> pageParam, SystemOperLogQueryVo sysOperLogQueryVo);

}

