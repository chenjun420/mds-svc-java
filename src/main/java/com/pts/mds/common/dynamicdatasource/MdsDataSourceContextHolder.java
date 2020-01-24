package com.pts.mds.common.dynamicdatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程级数据源持有类
 */
public class MdsDataSourceContextHolder {

    private static final Logger logger = LoggerFactory.getLogger(MdsDataSourceContextHolder.class);

    /**
     * 线程级别的私有变量
     */
    private static final ThreadLocal<MdsDataSourceNames> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源
     */
    public static void setDataSource(MdsDataSourceNames dbType){
        logger.info("切换到[{}]数据源",dbType.name());
        contextHolder.set(dbType);
    }

    /**
     * 获取数据源
     */
    public static MdsDataSourceNames getDataSource(){
        return contextHolder.get();
    }

    /**
     * 设置数据源之前一定要先移除
     */
    public static void clearDataSource(){
        contextHolder.remove();
    }

}
