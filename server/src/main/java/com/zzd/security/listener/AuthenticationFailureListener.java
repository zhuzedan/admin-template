package com.zzd.security.listener;

import com.zzd.domain.SystemLoginLog;
import com.zzd.security.service.SystemLoginLogService;
import com.zzd.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录失败监听器事件
 * 用于记录登录日志
 *
 * @author chqiuu
 */
@Slf4j
@Component
public class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {
    @Autowired
    private SystemLoginLogService userLoginLogService;

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        String message;
        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            //提供的凭据是错误的，用户名或者密码错误
            message = "提供的凭据是错误的，用户名或者密码错误";
        } else if (event instanceof AuthenticationFailureCredentialsExpiredEvent) {
            //验证通过，但是密码过期
            message = "验证通过，但是密码过期";
        } else if (event instanceof AuthenticationFailureDisabledEvent) {
            //验证过了但是账户被禁用
            message = "验证过了但是账户被禁用";
        } else if (event instanceof AuthenticationFailureExpiredEvent) {
            //验证通过了，但是账号已经过期
            message = "验证通过了，但是账号已经过期";
        } else if (event instanceof AuthenticationFailureLockedEvent) {
            //账户被锁定
            message = "账户被锁定";
        } else if (event instanceof AuthenticationFailureProviderNotFoundEvent) {
            //配置错误，没有合适的AuthenticationProvider来处理登录验证
            message = "配置错误";
        } else if (event instanceof AuthenticationFailureProxyUntrustedEvent) {
            // 代理不受信任，用于Oauth、CAS这类三方验证的情形，多属于配置错误
            message = "代理不受信任";
        } else if (event instanceof AuthenticationFailureServiceExceptionEvent) {
            // 其他任何在AuthenticationManager中内部发生的异常都会被封装成此类
            message = "内部发生的异常";
        } else {
            message = "其他未知错误";
        }
        // 登录账号
        Object username = event.getAuthentication().getPrincipal();
        // 登录密码
        Object credentials = event.getAuthentication().getCredentials();
        SystemLoginLog loginLog = new SystemLoginLog();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        loginLog.setIpaddr( IpUtil.getIpAddress(request));
        loginLog.setStatus(0);
        loginLog.setMsg(String.format("username:%s; pass:%s; message:%s", username, credentials, message));
        userLoginLogService.save(loginLog);
    }
}
