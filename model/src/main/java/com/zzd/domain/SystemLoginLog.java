package com.zzd.domain;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 系统访问记录(SystemLoginLog)表实体类
 *
 * @author zzd
 * @since 2022-11-29 10:33:50
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_login_log")
public class SystemLoginLog  {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //用户账号
    private String username;
    //登录IP地址
    private String ipaddr;
    //登录状态（0成功 1失败）
    private Integer status;
    //提示信息
    private String msg;
    //访问时间
    private Date accessTime;
    
    private Date createTime;
    
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;

}

