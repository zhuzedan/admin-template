package com.zzd.filter;

import com.zzd.dto.LoginUser;
import com.zzd.utils.JwtUtil;
import com.zzd.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author :zzd
 * @date : 2022/11/6
 * OncePerRequestFilter 只走一次，请求前
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    //OncePerRequestFilter只走一次，在请求前
    @Autowired
    RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1获取token  header的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //放行，让后面的过滤器执行
            filterChain.doFilter(request, response);
            return;
        }
        //2解析token
        String userId;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            throw new RuntimeException("token不合法！");
        }

        //3获取userId, redis获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("zzdlogin:" + userId);
        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("当前用户未登录！");
        }

        //4封装Authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, null);

        //5存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //放行，让后面的过滤器执行
        filterChain.doFilter(request, response);
    }
}