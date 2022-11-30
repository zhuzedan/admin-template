package com.zzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzd.domain.SystemLoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;


/**
 * 系统访问记录(SystemLoginLog)表数据库访问层
 *
 * @author zzd
 * @since 2022-11-29 10:33:32
 */
@Repository
@Mapper
public interface SystemLoginLogMapper extends BaseMapper<SystemLoginLog> {

}

