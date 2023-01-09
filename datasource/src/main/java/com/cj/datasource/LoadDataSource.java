package com.cj.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.cj.DruidProperties;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Component
@EnableConfigurationProperties(DruidProperties.class)
public class LoadDataSource {

    @Autowired
    DruidProperties druidProperties;

/*
* seata里面的 数据源 ：DataSourceProxy
* */
    public Map<String, DataSourceProxy> getAllDataSource(){
        HashMap<String, DataSourceProxy> map = new HashMap<>();
        try {
            Map<String, Map<String, String>> ds = druidProperties.getDs();
            for (String key : ds.keySet()) {
                DruidDataSource datasource = druidProperties.datasource((DruidDataSource) DruidDataSourceFactory.createDataSource(ds.get(key)));
                map.put(key, new DataSourceProxy(datasource));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
