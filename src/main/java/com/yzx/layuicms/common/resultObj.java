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

    public static final resultObj  OUTPORT_SUCCESS=new resultObj(constant.OK, "退货成功");
    public static final resultObj  OUTPORT_ERROR=new resultObj(constant.ERROR, "退货失败");

    public static final resultObj  AUTH_ERROR=new resultObj(constant.ERROR, "权限不足");

    public static final resultObj  PROVIDER_ERROR=new resultObj(constant.ERROR, "该供应商不供应这个商品");

    public static final resultObj  OUTPORTNUMBER_ERROR=new resultObj(constant.ERROR, "退货量大于进货量");
    public static final resultObj  SALESBACKNUMBER_ERROR=new resultObj(constant.ERROR, "退货量大于销货量");
    public static final resultObj  SALESNUMBER_ERROR=new resultObj(constant.ERROR, "销货量大于库存量");

    private Integer code;
    private String msg;

}
