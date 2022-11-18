package com.zzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzd.domain.SystemRole;
import com.zzd.result.ResponseResult;


/**
 * 角色(SystemRole)表服务接口
 *
 * @author zzd
 * @since 2022-11-17 22:03:07
 */
public interface SystemRoleService extends IService<SystemRole> {

    // ResponseResult selectPageRole(Long page, Long limit, SystemRole systemRole);
    ResponseResult selectPageRole(Long page, Long limit);

}

