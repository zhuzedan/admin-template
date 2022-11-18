package com.zzd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemRole;
import com.zzd.mapper.SystemRoleMapper;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色(SystemRole)表服务实现类
 *
 * @author zzd
 * @since 2022-11-17 22:03:07
 */
@Service("systemRoleService")
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {
    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Override
    public ResponseResult selectPageRole(Long page, Long limit) {
        IPage<SystemRole> systemRoleIPage = new Page<>(page,limit);
        IPage<SystemRole> systemRoleIPage1 = systemRoleMapper.selectPage(systemRoleIPage, null);
        return new ResponseResult(200,"success",systemRoleIPage1);
    }


}

