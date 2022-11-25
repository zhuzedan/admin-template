package com.zzd.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    @TableId(value = "id",type = IdType.AUTO)
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
    @TableLogic
    private Integer isDeleted;

}

