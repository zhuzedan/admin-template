package com.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzd.domain.SystemMenu;
import com.zzd.exception.GuiguException;
import com.zzd.mapper.SystemMenuMapper;
import com.zzd.result.ResponseResult;
import com.zzd.result.ResultCodeEnum;
import com.zzd.service.SystemMenuService;
import com.zzd.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean removeById(Long id) {
        int count = (int) this.count(new QueryWrapper<SystemMenu>().eq("parent_id", id));
        if (count > 0) {
            throw new GuiguException(ResultCodeEnum.NODE_ERROR.getCode(), ResultCodeEnum.NODE_ERROR.getMessage());
        }
        systemMenuMapper.deleteById(id);
        return false;
    }
}

