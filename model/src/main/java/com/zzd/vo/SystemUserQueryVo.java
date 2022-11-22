package com.zzd.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :zzd
 * @date : 2022/11/21
 */
@Data
public class SystemUserQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String keyword;

    private String createTimeBegin;
    private String createTimeEnd;

    private Long roleId;
    private Long postId;
    private Long deptId;
}
