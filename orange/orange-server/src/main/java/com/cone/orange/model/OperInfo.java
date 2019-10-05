package com.cone.orange.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述操作执行情况
 * @Author Cone
 * @Date 2019/9/21 16:43
 */
public class OperInfo {

    /**
     * 操作结果标志
     * true即为成功
     * 默认为true
     */
    private boolean flag = true;

    /**
     * 描述信息，用于页面提示
     * 默认为 "操作成功"
     */
    private String info = "操作成功";

    /**
     * 承载一些返回数据，如token等
     */
    private Map<String, Object> data = new HashMap<String, Object>();


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String key, Object value) {
        if (null != data) {
            this.data.put(key, value);
        }

    }
}
