package com.zzd.domain;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 角色(SystemRole)表实体类
 *
 * @author zzd
 * @since 2022-11-17 22:03:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_role")
public class SystemRole  {
    @TableId
    private Long id;

    //角色名称
    private String roleName;
    //角色编码
    private String roleCode;
    //描述
    private String description;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;

}

