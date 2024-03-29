package com.zzd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemUser;
import com.zzd.exception.ResponseException;
import com.zzd.mapper.SystemUserMapper;
import com.zzd.result.ResponseResult;
import com.zzd.result.ResultCodeEnum;
import com.zzd.security.dto.LoginUser;
import com.zzd.service.SystemUserService;
import com.zzd.utils.JwtUtil;
import com.zzd.utils.RedisCache;
import com.zzd.vo.SystemUserQueryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.zzd.security.constants.SecurityConstants.TOKEN_PREFIX;

/**
 * 用户表(SystemUser)表服务实现类
 *
 * @author zzd
 * @since 2022-11-18 16:02:54
 */
@Service("systemUserService")
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisCache redisCache;
    private Logger logger = LoggerFactory.getLogger(SystemUserServiceImpl.class);
    @Override
    public ResponseResult login(SystemUser systemUser) {
        //3使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(systemUser.getUsername(),systemUser.getPassword());
        // Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        org.springframework.security.core.Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //校验失败了
        if(Objects.isNull(authenticate)){
            throw new ResponseException(ResultCodeEnum.LOGIN_ERROR.getCode(), ResultCodeEnum.LOGIN_ERROR.getMessage());
        }
        //生成自己jwt给前端
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSystemUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String,String> map = new HashMap();
        map.put("token",jwt);
        map.put("tokenHead",TOKEN_PREFIX);
        //存入redis
        redisCache.setCacheObject("zzdlogin:"+userId,loginUser);
        logger.info("successsuccess");
        return ResponseResult.success("登录成功",map);
    }

    @Override
    public ResponseResult getInfo() {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        SystemUser systemUser = loginUser.getSystemUser();
        systemUser.setPassword(null);
        return ResponseResult.success(systemUser);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getSystemUser().getId();
        redisCache.deleteObject("zzdlogin:"+userId);

        return new ResponseResult(200,"退出成功");
    }

    @Override
    public IPage<SystemUser> selectPage(Page<SystemUser> pageParam, SystemUserQueryVo systemUserQueryVo) {
        return systemUserMapper.selectUserPage(pageParam,systemUserQueryVo);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SystemUser systemUser = systemUserMapper.selectById(id);
        systemUser.setStatus(status);
        systemUserMapper.updateById(systemUser);
    }


}

