package com.pts.mds.common.DynamicDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * 数据源配置
 */
@Configuration
public class MdsDynamicDataSourceConfig {

    @Bean(name = "dataSourcePrimary")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceSecondary")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dataSourceThirdary")
    @ConfigurationProperties(prefix = "spring.datasource.thirdary")
    public DataSource thirdaryDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        MdsDynamicDataSource dynamicDataSource = new MdsDynamicDataSource();
        //配置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(primaryDataSource());

        //配置多数据源
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(MdsDataSourceNames.PRIMARY.name(), primaryDataSource());
        dataSourceMap.put(MdsDataSourceNames.SECONDARY.name(), secondaryDataSource());
        dataSourceMap.put(MdsDataSourceNames.THIRDARY.name(), thirdaryDataSource());
        dynamicDataSource.setTargetDataSources(dataSourceMap); // 该方法是AbstractRoutingDataSource的方法
        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事务
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

}
