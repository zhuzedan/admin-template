package com.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemRole;
import com.zzd.domain.SystemUserRole;
import com.zzd.mapper.SystemRoleMapper;
import com.zzd.mapper.SystemUserRoleMapper;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemRoleService;
import com.zzd.vo.AssginRoleVo;
import com.zzd.vo.SystemRoleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Override
    public IPage<SystemRole> selectPage(Page<SystemRole> pageParam, SystemRoleQueryVo systemRoleQueryVo) {
        return systemRoleMapper.selectPage(pageParam,systemRoleQueryVo);
    }

    @Override
    public ResponseResult getRolesByUserId(Long userId) {
        //获取所有角色
        List<SystemRole> roles = systemRoleMapper.selectList(null);
        //根据用户id查询
        QueryWrapper<SystemUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        //获取用户已分配的角色
        List<SystemUserRole> userRoles = systemUserRoleMapper.selectList(queryWrapper);
        //获取用户已分配的角色id
        List<Long> userRoleIds = new ArrayList<>();
        for (SystemUserRole userRole : userRoles) {
            userRoleIds.add(userRole.getRoleId());
        }
        //创建返回的Map
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",roles);
        returnMap.put("userRoleIds",userRoleIds);
        return new ResponseResult(200,"success",returnMap);
    }

    @Override
    public ResponseResult doAssign(AssginRoleVo assginRoleVo) {
        //根据用户id删除原来分配的角色
        QueryWrapper<SystemUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",assginRoleVo.getUserId());
        systemUserRoleMapper.delete(queryWrapper);
        //获取所有的角色id
        List<String> roleIdList = assginRoleVo.getRoleIdList();
        for (String roleId : roleIdList) {
            if(roleId != null){
                SystemUserRole sysUserRole = new SystemUserRole();
                sysUserRole.setUserId(Long.valueOf(assginRoleVo.getUserId()));
                sysUserRole.setRoleId(Long.valueOf(roleId));
                //保存
                systemUserRoleMapper.insert(sysUserRole);
            }
        }
        return new ResponseResult(200,"分配成功");
    }

}

