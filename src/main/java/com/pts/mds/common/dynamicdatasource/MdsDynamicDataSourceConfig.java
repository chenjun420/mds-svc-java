package com.pts.mds.common.dynamicdatasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * 数据源配置
 * @Author Harry.Chan <chenpi_cn@hotmail.com>
 */
@Configuration
public class MdsDynamicDataSourceConfig {

    @Bean("dataSourcePrimary")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("dataSourceSecondary")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("dataSourceThirdary")
    @ConfigurationProperties(prefix = "spring.datasource.thirdary")
    public DataSource thirdaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("dataSourcePrimary") DataSource primary
            , @Qualifier("dataSourceSecondary") DataSource secondary
            , @Qualifier("dataSourceThirdary") DataSource thirdary) {
        MdsDynamicDataSource dynamicDataSource = new MdsDynamicDataSource();
        //配置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(primary);

        //配置多数据源
        HashMap<Object, Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put(MdsDataSourceNames.PRIMARY, primary);
        dataSourceMap.put(MdsDataSourceNames.SECONDARY, secondary);
        dataSourceMap.put(MdsDataSourceNames.THIRDARY, thirdary);
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        return dynamicDataSource;
    }

}
