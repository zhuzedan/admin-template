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
 * 操作日志记录(SystemOperLog)表实体类
 *
 * @author zzd
 * @since 2022-11-28 13:09:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_oper_log")
public class SystemOperLog  {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //模块标题
    private String title;
    //业务类型（0其它 1新增 2修改 3删除）
    private String businessType;
    //方法名称
    private String method;
    //请求方式
    private String requestMethod;
    //操作类别（0其它 1后台用户 2手机端用户）
    private String operatorType;
    //操作人员
    private String operName;
    //部门名称
    private String deptName;
    //请求URL
    private String operUrl;
    //主机地址
    private String operIp;
    //请求参数
    private String operParam;
    //返回参数
    private String jsonResult;
    //操作状态（0正常 1异常）
    private Integer status;
    //错误消息
    private String errorMsg;
    //操作时间
    private Date operTime;
    
    private Date createTime;
    
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;

}

