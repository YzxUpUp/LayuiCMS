package com.yzx.layuicms.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页左侧导航树节点实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class treeNode {

    private Integer id;
    @JsonProperty("parentId")
    private Integer pid;
    private String title;
    private String icon;
    private String href;
    private Boolean spread;
    private List<treeNode> children = new ArrayList<>();

    private String checkArr; //0表示拥有权限，1表示未拥有

    /**
     * 首页左边导航树的构造器
     */
    public treeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
        super();
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
    }

    /**
     * dTree复选树的构造器
     * @param id
     * @param pid
     * @param title
     * @param spread
     * @param checkArr
     */
    public treeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }
}
