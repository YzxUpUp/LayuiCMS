package com.yzx.layuicms.common;

import com.yzx.layuicms.domain.treeNode;

import java.util.ArrayList;
import java.util.List;

public class treeNodeBuilder {

    public static List<treeNode> build(List<treeNode> treeNodes,Integer topPid){
        List<treeNode> nodes = new ArrayList<>();
        for (treeNode t1 : treeNodes) {
            if(t1.getPid() == topPid){
                nodes.add(t1);
            }
            for (treeNode t2 : treeNodes) {
                if(t1.getId() == t2.getPid()){
                    t1.getChildren().add(t2);
                }
            }
        }
        return nodes;
    }

}
