package com.zzd.controller;

import com.zzd.domain.SystemUser;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 用户表(SystemUser)表控制层
 *
 * @author zzd
 * @since 2022-11-10 14:21:47
 */
@Api(tags = "用户登录接口")
@RestController
public class SystemUserController {
    @Autowired
    SystemUserService systemUserService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody SystemUser systemUser) {
        return systemUserService.login(systemUser);
    }
    @PostMapping("/user/logout")
    public ResponseResult logout() {
        return systemUserService.logout();
    }
    @GetMapping("/user/info")
    public ResponseResult getInfo() {
        return systemUserService.getInfo();
    }

}

