package com.cone.orange.service.config;

import com.cone.orange.dao.config.ConfigDao;
import com.cone.orange.model.config.DictConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import java.util.List;

/**
 * @Author Cone
 * @Date 2019/11/17 23:54
 */
@Service
public class ConfigService {

    @Autowired
    private ConfigDao configDao;

    /**
     * 查询数据字典配置信息
     * @param typeName 数据字典的参数类型名称
     * @return
     */
    public List<DictConfig> queryDictConfigByTypeName(String typeName) {

        List<DictConfig> dictConfigList = Lists.newArrayList();
        dictConfigList = configDao.getDictConfigByTypeName(typeName);
        return dictConfigList;
    }
}
