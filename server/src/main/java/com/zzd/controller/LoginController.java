package com.zzd.controller;

import com.zzd.domain.SystemUser;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author :zzd
 * @date : 2022/11/22
 */
@Api(tags = "用户登录接口")
@RestController
@RequestMapping("/admin/system/user")
public class LoginController {
    @Autowired
    SystemUserService systemUserService;
    // 登录
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody SystemUser systemUser) {
        return systemUserService.login(systemUser);
    }
    //退出登录
    @ApiOperation("用户退出登录")
    @PostMapping("/logout")
    public ResponseResult logout() {
        return systemUserService.logout();
    }
    //获取用户信息
    @ApiOperation("用户信息")
    @GetMapping("/info")
    public ResponseResult getInfo() {
        return systemUserService.getInfo();
    }

}
