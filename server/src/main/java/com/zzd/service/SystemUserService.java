package com.zzd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzd.domain.SystemRole;
import com.zzd.domain.SystemUser;
import com.zzd.result.ResponseResult;
import com.zzd.vo.SystemUserQueryVo;


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

    IPage<SystemUser> selectPage(Page<SystemUser> pageParam, SystemUserQueryVo systemUserQueryVo);

    void updateStatus(Long id, Integer status);
}

