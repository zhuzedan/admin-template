package com.zzd.vo;

import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

@Data
public class SystemLoginLogQueryVo {
	
	@ApiModelProperty(value = "用户账号")
	private String username;

	private String createTimeBegin;
	private String createTimeEnd;

}
