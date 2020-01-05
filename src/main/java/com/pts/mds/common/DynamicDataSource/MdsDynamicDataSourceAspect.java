package com.pts.mds.common.DynamicDataSource;

import com.pts.mds.common.WebLogAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切换数据源的切面
 */
public class MdsDynamicDataSourceAspect {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(MdsDynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, MdsDataSource ds) throws Throwable  {
        MdsDataSourceNames dataSourceName = ds.value();
        MdsDataSourceContextHolder.setDataSource(dataSourceName);
        logger.info("设置数据源: {} > {}", dataSourceName.name(), point.getSignature());
    }

    @After("@annotation(ds)")
    public void clearDataSource(JoinPoint point, MdsDataSource ds) {
        MdsDataSourceContextHolder.clearDataSource();
        logger.info("清除数据源: {} > {}", ds.value().name(), point.getSignature());
    }

}
