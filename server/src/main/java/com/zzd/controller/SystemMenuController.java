package com.zzd.controller;

import com.zzd.domain.SystemMenu;
import com.zzd.result.ResponseResult;
import com.zzd.service.SystemMenuService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 菜单表(SystemMenu)表控制层
 *
 * @author zzd
 * @since 2022-11-23 09:27:27
 */
@Api(tags = "菜单列表")
@RestController
@RequestMapping("/admin/system/systemMenu")
public class SystemMenuController {

    @Autowired
    private SystemMenuService systemMenuService;

    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public ResponseResult findNodes() {
        return systemMenuService.findNodes();
    }

    @ApiOperation(value = "查询所有记录")
    @GetMapping("/query")
    public ResponseResult findAll() {
        List<SystemMenu> list = systemMenuService.list();
        return new ResponseResult(200,"success",list);
    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/read/{id}")
    public ResponseResult selectOne(@PathVariable Long id) {
        SystemMenu systemMenu = systemMenuService.getById(id);
        return new  ResponseResult(200,"成功获取详情",systemMenu);
    }

    @ApiOperation(value = "新增数据")
    @PostMapping("/save")
    public ResponseResult insert(@RequestBody SystemMenu systemMenu) {
        boolean flag = systemMenuService.save(systemMenu);
        if (flag) {
            return new ResponseResult(200,"成功新增");
        }else {
            return new ResponseResult(401,"新增失败");
        }
    }

    @ApiOperation(value = "修改数据")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody SystemMenu systemMenu) {
        systemMenuService.updateById(systemMenu);
        return new ResponseResult(200,"成功修改");
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public ResponseResult remove(@PathVariable Long id) {
        systemMenuService.removeById(id);
        return new ResponseResult(200,"success");
    }

}

