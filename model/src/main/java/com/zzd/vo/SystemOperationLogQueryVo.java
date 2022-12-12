package com.zzd.vo;

import lombok.Data;

@Data
public class SystemOperationLogQueryVo {

	private String title;
	private String operName;

	private String createTimeBegin;
	private String createTimeEnd;

}
