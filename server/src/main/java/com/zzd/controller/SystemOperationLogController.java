package com.zzd.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzd.domain.SystemOperationLog;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemOperationLogService;
import com.zzd.vo.SystemOperationLogQueryVo;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 操作日志记录(SystemOperLog)表控制层
 *
 * @author zzd
 * @since 2022-11-28 13:27:32
 */
@Api(tags = "操作日志管理")
@RestController
@RequestMapping("/admin/system/systemOperLog")
public class SystemOperationLogController {

    @Autowired
    private SystemOperationLogService systemOperationLogService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public ResponseResult index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "systemOperLogQueryVo", value = "查询对象", required = false)
                    SystemOperationLogQueryVo systemOperationLogQueryVo) {
        Page<SystemOperationLog> pageParam = new Page<>(page, limit);
        IPage<SystemOperationLog> pageModel = systemOperationLogService.selectPage(pageParam, systemOperationLogQueryVo);
        return new ResponseResult(200,"success",pageModel);
    }

    @ApiOperation(value = "查询所有记录")
    @GetMapping("/query")
    public ResponseResult findAll() {
        List<SystemOperationLog> list = systemOperationLogService.list();
        return new ResponseResult(200,"success",list);
    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/read/{id}")
    public ResponseResult selectOne(@PathVariable Long id) {
        SystemOperationLog systemOperationLog = systemOperationLogService.getById(id);
        return new  ResponseResult(200,"成功获取详情", systemOperationLog);
    }

    @ApiOperation(value = "删除数据")
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        boolean flag = systemOperationLogService.removeById(id);
        if (flag) {
            return new ResponseResult(200,"删除成功");
        }
        else {
            return new ResponseResult(200,"失败");
        }
    }
    @ApiOperation(value = "批量删除数据")
    @DeleteMapping("/batchRemove")
    public ResponseResult batchRemove(@RequestBody List<Long> idList) {
        systemOperationLogService.removeByIds(idList);
        return new  ResponseResult(200,"批量删除成功");
    }
}

