package com.zzd.handler;

import com.alibaba.fastjson.JSON;
import com.zzd.result.ResponseResult;
import com.zzd.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author :zzd
 * @date : 2022/11/6
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //给ResponseResult的json串
        ResponseResult responseResult = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "登录认证失败，请重新登录");
        String json = JSON.toJSONString(responseResult);
        WebUtils.renderString(response,json);
    }
}
