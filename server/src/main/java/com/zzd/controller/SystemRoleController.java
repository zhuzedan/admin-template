package com.zzd.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzd.annotation.Log;
import com.zzd.domain.SystemRole;
import com.zzd.enums.BusinessType;
import com.zzd.exception.ResponseException;
import com.zzd.result.ResponseResult;
import com.zzd.result.ResultCodeEnum;
import com.zzd.service.SystemRoleService;
import com.zzd.vo.AssginRoleVo;
import com.zzd.vo.SystemRoleQueryVo;
import io.swagger.annotations.Api;
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
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/api/systemRole")
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
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
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
    @Log(title = "角色管理", businessType = BusinessType.OTHER)
    @ApiOperation("分页查询")
    @GetMapping("/{page}/{limit}")
    public ResponseResult findPageRole(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "sysRoleQueryVo", value = "查询对象",required = false)
                    SystemRoleQueryVo systemRoleQueryVo
    ) {
        Page<SystemRole> pageParam = new Page<>(page, limit);
        IPage<SystemRole> pageModel = systemRoleService.selectPage(pageParam, systemRoleQueryVo);
        return new ResponseResult(200,"查询成功",pageModel);
    }
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @ApiOperation(value = "新增角色")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody SystemRole role) {
        boolean save = systemRoleService.save(role);
        if (save) {
            return ResponseResult.success();
        }else {
            return ResponseResult.error();
        }
    }
    @ApiOperation(value = "获取角色")
    @GetMapping("/get/{id}")
    public ResponseResult get(@PathVariable Long id) {
        SystemRole role = systemRoleService.getById(id);
        if (role != null) {
            return ResponseResult.success(role);
        }
        else {
            throw new ResponseException(ResultCodeEnum.PARAM_NOT_VALID.getCode(),ResultCodeEnum.PARAM_NOT_VALID.getMessage());
        }
    }
    @Log(title = "角色管理",businessType = BusinessType.UPDATE)
    @ApiOperation(value = "修改角色")
    @PostMapping("/update")
    public ResponseResult updateById(@RequestBody SystemRole systemRole) {
        systemRoleService.updateById(systemRole);
        return new  ResponseResult(200,"成功修改");
    }
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/batchRemove")
    public ResponseResult batchRemove(@RequestBody List<Long> idList) {
        systemRoleService.removeByIds(idList);
        return new  ResponseResult(200,"批量删除成功");
    }
    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public ResponseResult toAssign(@PathVariable Long userId) {
        return systemRoleService.getRolesByUserId(userId);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public ResponseResult doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        return systemRoleService.doAssign(assginRoleVo);
    }

}

