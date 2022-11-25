package com.zzd.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 角色菜单(SystemRoleMenu)表实体类
 *
 * @author zzd
 * @since 2022-11-24 13:36:51
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_role_menu")
public class SystemRoleMenu  {
    @TableId
    private Long id;

    
    private Long roleId;
    
    private Long menuId;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;

}

