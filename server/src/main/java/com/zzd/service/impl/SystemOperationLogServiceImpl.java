package com.zzd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemOperationLog;
import com.zzd.mapper.SystemOperationLogMapper;
import com.zzd.service.SystemOperationLogService;
import com.zzd.vo.SystemOperationLogQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志记录(SystemOperLog)表服务实现类
 *
 * @author zzd
 * @since 2022-11-28 13:09:36
 */
@Service("systemOperLogService")
public class SystemOperationLogServiceImpl extends ServiceImpl<SystemOperationLogMapper, SystemOperationLog> implements SystemOperationLogService {
    @Autowired
    private SystemOperationLogMapper systemOperationLogMapper;

    @Override
    public void saveSysLog(SystemOperationLog sysOperLog) {
        systemOperationLogMapper.insert(sysOperLog);
    }

    @Override
    public IPage<SystemOperationLog> selectPage(Page<SystemOperationLog> pageParam, SystemOperationLogQueryVo systemOperationLogQueryVo) {
        //调用mapper方法实现分页条件查询
        IPage<SystemOperationLog> sysOperLogPage = systemOperationLogMapper.selectPage(pageParam, systemOperationLogQueryVo);
        return sysOperLogPage;
    }

}

