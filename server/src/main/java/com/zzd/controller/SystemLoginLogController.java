package com.zzd.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzd.domain.SystemLoginLog;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemLoginLogService;
import com.zzd.vo.SystemLoginLogQueryVo;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 系统访问记录(SystemLoginLog)表控制层
 *
 * @author zzd
 * @since 2022-12-06 15:32:52
 */
@Api(tags = "登录日志管理")
@RestController
@RequestMapping("/api/systemLoginLog")
public class SystemLoginLogController {

    @Autowired
    private SystemLoginLogService systemLoginLogService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public ResponseResult index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "systemLoginLogQueryVo", value = "查询对象", required = false)
                    SystemLoginLogQueryVo systemLoginLogQueryVo) {
        Page<SystemLoginLog> pageParam = new Page<>(page, limit);
        IPage<SystemLoginLog> pageModel = systemLoginLogService.selectPage(pageParam, systemLoginLogQueryVo);
        return new ResponseResult(200,"success",pageModel);
    }
    @ApiOperation(value = "查询所有记录")
    @GetMapping("/query")
    public ResponseResult findAll() {
        List<SystemLoginLog> list = systemLoginLogService.list();
        return new ResponseResult(200,"success",list);
    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/read/{id}")
    public ResponseResult selectOne(@PathVariable Long id) {
        SystemLoginLog systemLoginLog = systemLoginLogService.getById(id);
        return new  ResponseResult(200,"成功获取详情",systemLoginLog);
    }

    @ApiOperation(value = "删除数据")
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        boolean flag = systemLoginLogService.removeById(id);
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
        systemLoginLogService.removeByIds(idList);
        return new  ResponseResult(200,"批量删除成功");
    }
}

