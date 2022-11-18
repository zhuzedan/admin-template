package com.zzd.controller;

import com.zzd.domain.SystemRole;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemRoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 角色(SystemRole)表控制层
 *
 * @author zzd
 * @since 2022-11-17 22:04:20
 */
@RestController
@RequestMapping("/admin/system/systemRole")
public class SystemRoleController {
    @Autowired
    private SystemRoleService systemRoleService;

    //查询所有记录
    @ApiOperation("查询所有记录")
    @GetMapping("findAll")
    public ResponseResult findAllRole() {
        List<SystemRole> list = systemRoleService.list();
        return new ResponseResult(200,"success",list);
    }
    //逻辑删除接口
    @ApiOperation("逻辑删除接口")
    @DeleteMapping("remove/{id}")
    public ResponseResult removeRole(@PathVariable Long id) {
        boolean isSuccess = systemRoleService.removeById(id);
        if (isSuccess) {
            return new ResponseResult(200,"删除成功");
        }
        else {
            return new ResponseResult(200,"失败");
        }
    }
    @ApiOperation("分页查询")
    @GetMapping("/{page}/{limit}")
    public ResponseResult findPageRole(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit
            // @ApiParam(name = "roleQueryVo", value = "查询对象", required = false)
            // SystemRole systemRole
    ) {
        // return systemRoleService.selectPageRole(page,limit,systemRole);
        return systemRoleService.selectPageRole(page,limit);
    }
    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody SystemRole role) {
        boolean save = systemRoleService.save(role);
        if (save) {
            return new ResponseResult(200,"成功新增");
        }else {
            return new ResponseResult(401,"新增失败");
        }
    }
    @ApiOperation(value = "获取角色")
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable Long id) {
        SystemRole role = systemRoleService.getById(id);
        return new  ResponseResult(200,"成功获取角色",role);
    }
    @ApiOperation(value = "修改角色")
    @GetMapping("/update")
    public ResponseResult updateById(@RequestBody SystemRole systemRole) {
        systemRoleService.updateById(systemRole);
        return new  ResponseResult(200,"成功新增");
    }
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/batchRemove")
    public ResponseResult batchRemove(@RequestBody List<Long> idList) {
        systemRoleService.removeByIds(idList);
        return new  ResponseResult(200,"批量删除成功");
    }

}

