package com.moxie.cloud.services.server.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangjiawei
 * @date: 2018/12/20
 */

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);
    private Map<String, Object> datasourceMap;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        LOGGER.info("打包时间: {}", env.getProperty("package.time"));

        datasourceMap = new HashMap<>();
        for (PropertySource<?> propertySource : ((AbstractEnvironment) env).getPropertySources()) {
            this.getPropertiesFromSource(propertySource, datasourceMap);
        }
    }

    @Value("${spring.datasource.url}")
    private String mysqlUrl;
    @Value("${spring.datasource.user}")
    private String mysqlUser;
    @Value("${spring.datasource.pass}")
    private String mysqlPass;


    private void getPropertiesFromSource(PropertySource<?> propertySource, Map<String, Object> map) {
        if (propertySource instanceof MapPropertySource) {
            for (String key : ((MapPropertySource) propertySource).getPropertyNames()) {
                map.put(key, propertySource.getProperty(key));
            }
        }
        if (propertySource instanceof CompositePropertySource) {
            for (PropertySource<?> s : ((CompositePropertySource) propertySource).getPropertySources()) {
                getPropertiesFromSource(s, map);
            }
        }
    }

    private DataSource getDataSource(String url, String user, String pass) {
        datasourceMap.put(DruidDataSourceFactory.PROP_URL, url);
        datasourceMap.put(DruidDataSourceFactory.PROP_USERNAME, user);
        datasourceMap.put(DruidDataSourceFactory.PROP_PASSWORD, pass);

        try {
            return DruidDataSourceFactory.createDataSource(datasourceMap);
        } catch (Exception e) {
            LOGGER.error("无法获得数据源'{}':'{}'", url, ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("无法获得数据源.");
        }
    }

    @Bean(name = "template")
    public JdbcTemplate template() {
        return new org.springframework.jdbc.core.JdbcTemplate(this.getDataSource(mysqlUrl, mysqlUser, mysqlPass));
    }


}
