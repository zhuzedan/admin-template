package com.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemMenu;
import com.zzd.domain.SystemRoleMenu;
import com.zzd.exception.GuiguException;
import com.zzd.mapper.SystemMenuMapper;
import com.zzd.mapper.SystemRoleMenuMapper;
import com.zzd.result.ResponseResult;
import com.zzd.result.ResultCodeEnum;
import com.zzd.service.SystemMenuService;
import com.zzd.utils.MenuHelper;
import com.zzd.vo.AssginMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表(SystemMenu)表服务实现类
 *
 * @author zzd
 * @since 2022-11-23 09:27:27
 */
@Service("systemMenuService")
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {
    @Autowired
    private SystemMenuMapper systemMenuMapper;
    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    public ResponseResult findNodes() {
        //全部权限列表
        List<SystemMenu> sysMenuList = this.list();
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }
        //构建树形数据
        List<SystemMenu> result = MenuHelper.buildTree(sysMenuList);
        return new ResponseResult(200,"success",result);
    }

    @Override
    public void removeMenuById(Long id) {
        QueryWrapper<SystemMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Long count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuiguException(ResultCodeEnum.ILLEGAL_REQUEST.getCode(),ResultCodeEnum.ILLEGAL_REQUEST.getMessage());
        }
        systemMenuMapper.deleteById(id);
    }

    @Override
    public ResponseResult findSystemMenuByRoleId(Long roleId) {
        //获取所有status为1的权限列表
        List<SystemMenu> menuList = systemMenuMapper.selectList(new QueryWrapper<SystemMenu>().eq("status", 1));
        //根据角色id获取角色权限
        List<SystemRoleMenu> roleMenus = systemRoleMenuMapper.selectList(new QueryWrapper<SystemRoleMenu>().eq("role_id",roleId));
        //获取该角色已分配的所有权限id
        List<Long> roleMenuIds = new ArrayList<>();
        for (SystemRoleMenu roleMenu : roleMenus) {
            roleMenuIds.add(roleMenu.getMenuId());
        }
        //遍历所有权限列表
        for (SystemMenu sysMenu : menuList) {
            if (roleMenuIds.contains(sysMenu.getId())){
                //设置该权限已被分配
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //将权限列表转换为权限树
        List<SystemMenu> sysMenus = MenuHelper.buildTree(menuList);
        return  new ResponseResult(200,"success",sysMenus);
    }

    @Override
    public ResponseResult doAssign(AssginMenuVo assginMenuVo) {
        //删除已分配的权限
        systemRoleMenuMapper.delete(new QueryWrapper<SystemRoleMenu>().eq("role_id",assginMenuVo.getRoleId()));
        //遍历所有已选择的权限id
        for (String menuId : assginMenuVo.getMenuIdList()) {
            if(menuId != null){
                //创建SysRoleMenu对象
                SystemRoleMenu sysRoleMenu = new SystemRoleMenu();
                sysRoleMenu.setMenuId(Long.valueOf(menuId));
                sysRoleMenu.setRoleId(Long.valueOf(assginMenuVo.getRoleId()));
                //添加新权限
                systemRoleMenuMapper.insert(sysRoleMenu);
            }
        }
        return new ResponseResult(200,"success");
    }

}

