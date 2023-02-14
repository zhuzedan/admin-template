package com.zzd.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzd.domain.SystemLoginLog;
import com.zzd.vo.SystemLoginLogQueryVo;


/**
 * 系统访问记录(SystemLoginLog)表服务接口
 *
 * @author zzd
 * @since 2022-11-30 09:33:26
 */
public interface SystemLoginLogService extends IService<SystemLoginLog> {
    void recordLoginLog(String username, Integer status, String ipaddr, String message);

    //分页查询
    IPage<SystemLoginLog> selectPage(Page<SystemLoginLog> pageParam, SystemLoginLogQueryVo systemLoginLogQueryVo);
}

