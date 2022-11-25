package com.zzd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemRole;
import com.zzd.domain.SystemUser;
import com.zzd.dto.LoginUser;
import com.zzd.mapper.SystemRoleMapper;
import com.zzd.mapper.SystemUserMapper;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemUserService;
import com.zzd.utils.JwtUtil;
import com.zzd.utils.RedisCache;
import com.zzd.vo.SystemRoleQueryVo;
import com.zzd.vo.SystemUserQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    @Override
    public ResponseResult login(SystemUser systemUser) {
        //3使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(systemUser.getUsername(),systemUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //校验失败了
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误！");
        }
        //生成自己jwt给前端
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSystemUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        Map<String,String> map = new HashMap();
        map.put("token",jwt);
        //存入redis
        redisCache.setCacheObject("zzdlogin:"+userId,loginUser);
        return new ResponseResult(200,"登录成功",map);
    }

    @Override
    public ResponseResult getInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","朱泽丹");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return new ResponseResult(200,"获取个人信息成功",map);
    }

    @Override
    public ResponseResult logout() {
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

