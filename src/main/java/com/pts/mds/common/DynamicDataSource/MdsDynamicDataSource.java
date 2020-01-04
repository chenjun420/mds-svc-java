package com.pts.mds.common.DynamicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源路由实现类
 */
public class MdsDynamicDataSource extends AbstractRoutingDataSource {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = MdsDataSourceContextHolder.getDataSource();
        if (dataSource == null) {
            logger.info("当前数据源为[primary]");
        } else {
            logger.info("当前数据源为{}", dataSource);
        }
        return dataSource;
    }

}