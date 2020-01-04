package com.pts.mds.common.DynamicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源持有类
 */
public class MdsDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(MdsDataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String dbType){
        logger.info("切换到[{}]数据源",dbType);
        contextHolder.set(dbType);
    }

    public static String getDataSource(){
        return contextHolder.get();
    }

    public static void clearDataSource(){
        contextHolder.remove();
    }
}
