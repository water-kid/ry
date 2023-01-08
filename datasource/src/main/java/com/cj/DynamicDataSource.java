package com.cj;

import com.cj.datasource.LoadDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(LoadDataSource loadDataSource) {
        // 将数据源告诉给 AbstractRoutingDataSource

        // 设置所有数据源
        Map<String, DataSource> allDs = loadDataSource.getAllDataSource();
        super.setTargetDataSources(new HashMap<>(allDs));

//        设置默认数据源
        super.setDefaultTargetDataSource(allDs.get(DataSourceType.DEFAULT_DS_NAME));
        //
        super.afterPropertiesSet();
    }

    // 返回数据源名称，，
    @Override
    protected Object determineCurrentLookupKey() {

        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
