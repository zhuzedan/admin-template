package com.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemOperLog;
import com.zzd.mapper.SystemOperLogMapper;
import com.zzd.service.SystemOperLogService;
import com.zzd.vo.SystemOperLogQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 操作日志记录(SystemOperLog)表服务实现类
 *
 * @author zzd
 * @since 2022-11-28 13:09:36
 */
@Service("systemOperLogService")
public class SystemOperLogServiceImpl extends ServiceImpl<SystemOperLogMapper, SystemOperLog> implements SystemOperLogService {
    @Autowired
    private SystemOperLogMapper systemOperLogMapper;

    @Override
    public void saveSysLog(SystemOperLog sysOperLog) {
        systemOperLogMapper.insert(sysOperLog);
    }

    @Override
    public IPage<SystemOperLog> selectPage(Page<SystemOperLog> pageParam, SystemOperLogQueryVo systemOperLogQueryVo) {
        //调用mapper方法实现分页条件查询
        IPage<SystemOperLog> sysOperLogPage = systemOperLogMapper.selectPage(pageParam, systemOperLogQueryVo);
        return sysOperLogPage;
    }

}

