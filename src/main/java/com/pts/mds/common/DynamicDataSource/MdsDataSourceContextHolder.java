package com.pts.mds.common.DynamicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源持有类
 */
public class MdsDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(MdsDataSourceContextHolder.class);

    private static final ThreadLocal<MdsDataSourceNames> contextHolder = new ThreadLocal<>();

    public static void setDataSource(MdsDataSourceNames dbType){
        logger.info("切换到[{}]数据源",dbType.name());
        contextHolder.set(dbType);
    }

    public static MdsDataSourceNames getDataSource(){
        return contextHolder.get();
    }

    public static void clearDataSource(){
        contextHolder.remove();
    }
}