package com.pts.mds.common.DynamicDataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切换数据源的切面
 */
@Component
@Aspect
@Order(-1) //这是关键，要让该切面调用先于AbstractRoutingDataSource的determineCurrentLookupKey()
public class MdsDynamicDataSourceAspect {

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, MdsDataSource ds) {
        MdsDataSourceContextHolder.setDataSource(ds.value());
    }

    @After("@annotation(ds)")
    public void clearDataSource(JoinPoint point, MdsDataSource ds) {
        MdsDataSourceContextHolder.clearDataSource();
    }

}
