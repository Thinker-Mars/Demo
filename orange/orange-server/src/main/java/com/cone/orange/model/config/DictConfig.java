package com.cone.orange.model.config;

/**
 * 数据字典配置
 * @Author Cone
 * @Date 2019/11/16 12:15
 */
public class DictConfig {

    private int configId;//配置id
    private int configTypeId;//配置类型id
    private String configValue;//配置值

    public int getConfigId() {
        return configId;
    }

    public int getConfigTypeId() {
        return configTypeId;
    }

    public String getConfigValue() {
        return configValue;
    }
}
