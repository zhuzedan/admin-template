package com.zzd.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzd.domain.SystemUser;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemUserService;
import com.zzd.vo.SystemUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 用户表(SystemUser)表控制层
 *
 * @author zzd
 * @since 2022-11-10 14:21:47
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/api/systemUser")
public class SystemUserController {
    @Autowired
    SystemUserService systemUserService;

    @ApiOperation("用户列表")
    @GetMapping("/{page}/{limit}")
    public ResponseResult userList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "systemUserQueryVo", value = "查询对象", required = false)
                    SystemUserQueryVo systemUserQueryVo
    ) {
        Page<SystemUser> pageParam = new Page<>(page, limit);
        IPage<SystemUser> pageModel = systemUserService.selectPage(pageParam, systemUserQueryVo);
        return new ResponseResult(200,"查询成功",pageModel);
    }
    @ApiOperation(value = "查询所有记录")
    @GetMapping("/query")
    public ResponseResult findAll() {
        List<SystemUser> list = systemUserService.list();
        return new ResponseResult(200,"success",list);
    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/read/{id}")
    public ResponseResult selectOne(@PathVariable Long id) {
        SystemUser systemUser = systemUserService.getById(id);
        return new  ResponseResult(200,"成功获取详情",systemUser);
    }

    @ApiOperation(value = "新增数据")
    @PostMapping("/save")
    public ResponseResult insert(@RequestBody SystemUser systemUser) {
        boolean flag = systemUserService.save(systemUser);
        if (flag) {
            return new ResponseResult(200,"成功新增");
        }else {
            return new ResponseResult(401,"新增失败");
        }
    }

    @ApiOperation(value = "修改数据")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody SystemUser systemUser) {
        systemUserService.updateById(systemUser);
        return new ResponseResult(200,"成功修改");
    }

    @ApiOperation(value = "删除数据")
    @DeleteMapping("delete/{id}")
    public ResponseResult delete(@PathVariable Long id) {
        boolean flag = systemUserService.removeById(id);
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
        systemUserService.removeByIds(idList);
        return new  ResponseResult(200,"批量删除成功");
    }
    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public ResponseResult updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        systemUserService.updateStatus(id, status);
        return new ResponseResult(200,"更新成功");
    }

}

