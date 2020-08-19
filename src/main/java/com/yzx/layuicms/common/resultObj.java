package com.yzx.layuicms.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 各种操作结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class resultObj {

    public static final resultObj  UPDATE_SUCCESS=new resultObj(constant.OK, "更新成功");
    public static final resultObj  UPDATE_ERROR=new resultObj(constant.ERROR, "更新失败");

    public static final resultObj  ADD_SUCCESS=new resultObj(constant.OK, "添加成功");
    public static final resultObj  ADD_ERROR=new resultObj(constant.ERROR, "添加失败");

    public static final resultObj  DELETE_SUCCESS=new resultObj(constant.OK, "删除成功");
    public static final resultObj  DELETE_ERROR=new resultObj(constant.ERROR, "删除失败");

    public static final resultObj  RESET_SUCCESS=new resultObj(constant.OK, "重置成功");
    public static final resultObj  RESET_ERROR=new resultObj(constant.ERROR, "重置失败");

    public static final resultObj  DISPATCH_SUCCESS=new resultObj(constant.OK, "分配成功");
    public static final resultObj  DISPATCH_ERROR=new resultObj(constant.ERROR, "分配失败");

    public static final resultObj  OPERATE_SUCCESS=new resultObj(constant.OK, "操作成功");
    public static final resultObj  OPERATE_ERROR=new resultObj(constant.ERROR, "操作失败");

    private Integer code;
    private String msg;

}
