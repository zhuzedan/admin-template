package com.zzd.security.listener;

import com.zzd.domain.SystemLoginLog;
import com.zzd.security.dto.LoginUser;
import com.zzd.security.service.SystemLoginLogService;
import com.zzd.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录成功监听器事件
 *
 * @author chqiuu
 */
@Slf4j
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private SystemLoginLogService userLoginLogService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        //用户通过输入用户名和密码登录成功
        // 登录账号
        LoginUser userOnline = (LoginUser) event.getAuthentication().getPrincipal();
        SystemLoginLog loginLog = new SystemLoginLog();
        loginLog.setUsername(String.valueOf(userOnline.getUsername()));
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        loginLog.setIpaddr( IpUtil.getIpAddress(request));
        loginLog.setStatus(1);
        loginLog.setMsg("登录成功");
        userLoginLogService.save(loginLog);
    }
}
