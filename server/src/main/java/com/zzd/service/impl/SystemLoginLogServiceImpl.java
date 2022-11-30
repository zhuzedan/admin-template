package com.zzd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemLoginLog;
import com.zzd.mapper.SystemLoginLogMapper;
import com.zzd.service.SystemLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.stereotype.Service;

/**
 * 系统访问记录(SystemLoginLog)表服务实现类
 *
 * @author zzd
 * @since 2022-11-30 09:34:40
 */
@Service("systemLoginLogService")
public class SystemLoginLogServiceImpl extends ServiceImpl<SystemLoginLogMapper, SystemLoginLog> implements SystemLoginLogService {

    @Autowired
    private SystemLoginLogMapper systemLoginLogMapper;

    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {
            SystemLoginLog sysLoginLog = new SystemLoginLog();
            sysLoginLog.setUsername(username);
            sysLoginLog.setIpaddr(ipaddr);
            sysLoginLog.setMsg(message);
            sysLoginLog.setStatus(status);
            systemLoginLogMapper.insert(sysLoginLog);
    }
}

