package com.zzd.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 用户表(SystemUser)表实体类
 *
 * @author zzd
 * @since 2022-11-18 16:02:54
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_user")
public class SystemUser  {
    //会员id@TableId
    private Long id;

    //用户名
    private String username;
    //密码
    private String password;
    //姓名
    private String name;
    //手机
    private String phone;
    //头像地址
    private String headUrl;
    //部门id
    private Long deptId;
    //岗位id
    private Long postId;
    //描述
    private String description;
    //状态（1：正常 0：停用）
    private Integer status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    @TableLogic
    private Integer isDeleted;

}

