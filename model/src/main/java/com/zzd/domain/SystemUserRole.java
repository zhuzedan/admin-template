package com.zzd.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 用户角色(SystemUserRole)表实体类
 *
 * @author zzd
 * @since 2022-11-22 14:47:32
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_user_role")
public class SystemUserRole  {
    @TableId
    private Long id;

    //角色id
    private Long roleId;
    //用户id
    private Long userId;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;

}

