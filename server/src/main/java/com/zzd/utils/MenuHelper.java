package com.zzd.utils;

import com.zzd.domain.SystemMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据菜单数据构建菜单树的工具类
 * </p>
 *
 */
public class MenuHelper {

    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SystemMenu> buildTree(List<SystemMenu> sysMenuList) {
        List<SystemMenu> trees = new ArrayList<>();
        for (SystemMenu systemMenu : sysMenuList) {
            if (systemMenu.getParentId().longValue() == 0) {
                trees.add(findChildren(systemMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static SystemMenu findChildren(SystemMenu systemMenu, List<SystemMenu> treeNodes) {
        systemMenu.setChildren(new ArrayList<SystemMenu>());

        for (SystemMenu it : treeNodes) {
            if(systemMenu.getId().longValue() == it.getParentId().longValue()) {
                if (systemMenu.getChildren() == null) {
                    systemMenu.setChildren(new ArrayList<>());
                }
                systemMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return systemMenu;
    }
}