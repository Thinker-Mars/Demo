package com.cone.orange.dao.config;

import com.cone.orange.model.config.DictConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取配置
 * @Author Cone
 * @Date 2019/11/16 11:18
 */
@Mapper
@Component
public interface ConfigDao {

    /**
     * 根据数据字典的参数类型名称取得该参数对应的配置
     * @param typeName 数据字典的参数类型名称
     * @return
     */
    List<DictConfig> getDictConfigByTypeName(String typeName);
}
