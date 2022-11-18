package com.zzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzd.domain.SystemUser;
import com.zzd.result.ResponseResult;


/**
 * 用户表(SystemUser)表服务接口
 *
 * @author zzd
 * @since 2022-11-18 16:02:54
 */
public interface SystemUserService extends IService<SystemUser> {

    ResponseResult login(SystemUser systemUser);

    ResponseResult getInfo();

    ResponseResult logout();
}

