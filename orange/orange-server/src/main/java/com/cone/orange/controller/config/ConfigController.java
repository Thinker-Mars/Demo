package com.cone.orange.controller.config;

import com.cone.orange.model.config.DictConfig;
import com.cone.orange.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统配置查询
 * @Author Cone
 * @Date 2019/11/15 16:18
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 查询数据字典配置信息
     * @param typeName 数据字典的参数类型名称
     * @return
     */
    @GetMapping(value="/dict")
    @ResponseBody
    public List<DictConfig> getDictConfigByTypeName(String typeName) {
        return configService.queryDictConfigByTypeName(typeName);
    }
}
