package com.zzd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzd.domain.SystemRole;
import com.zzd.vo.SystemRoleQueryVo;


/**
 * 角色(SystemRole)表服务接口
 *
 * @author zzd
 * @since 2022-11-17 22:03:07
 */
public interface SystemRoleService extends IService<SystemRole> {

    IPage<SystemRole> selectPage(Page<SystemRole> pageParam, SystemRoleQueryVo systemRoleQueryVo);
    // ResponseResult selectPageRole(Long page, Long limit);

}

