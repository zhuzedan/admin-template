package com.zzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzd.domain.SystemMenu;
import com.zzd.result.ResponseResult;
import com.zzd.vo.AssginMenuVo;


/**
 * 菜单表(SystemMenu)表服务接口
 *
 * @author zzd
 * @since 2022-11-23 09:27:27
 */
public interface SystemMenuService extends IService<SystemMenu> {

    ResponseResult findNodes();

    void removeMenuById(Long id);

    ResponseResult findSystemMenuByRoleId(Long roleId);

    ResponseResult doAssign(AssginMenuVo assginMenuVo);
}

