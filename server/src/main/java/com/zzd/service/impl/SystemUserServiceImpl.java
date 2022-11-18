package com.zzd.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemUser;
import com.zzd.mapper.SystemUserMapper;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户表(SystemUser)表服务实现类
 *
 * @author zzd
 * @since 2022-11-18 16:02:54
 */
@Service("systemUserService")
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Override
    public ResponseResult login(SystemUser systemUser) {
        Map<String,String> map = new HashMap();
        map.put("token","admin-token");
        //存入redis
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
}

