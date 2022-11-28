package com.zzd.vo;

import lombok.Data;

@Data
public class SystemOperLogQueryVo {

	private String title;
	private String operName;

	private String createTimeBegin;
	private String createTimeEnd;

}
