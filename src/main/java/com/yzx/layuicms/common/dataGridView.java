package com.yzx.layuicms.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装用以转换为JSON的数据信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class dataGridView {

    private Integer code = 0;
    private String msg = "";
    private Long count = 0L;

    //装载树节点集合
    private Object data;

    public dataGridView(Long count, Object data) {
        super();
        this.count = count;
        this.data = data;
    }

    public dataGridView(Object data) {
        super();
        this.data = data;
    }
}
