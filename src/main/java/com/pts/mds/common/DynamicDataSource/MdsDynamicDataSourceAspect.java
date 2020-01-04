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
@Order(1) //这是关键，要让该切面调用先于AbstractRoutingDataSource的determineCurrentLookupKey()
public class MdsDynamicDataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before("@annotation(MdsDataSource)")
    public void before(JoinPoint point) {
        try {
            MdsDataSource annotationOfClass = point.getTarget().getClass().getAnnotation(MdsDataSource.class);
            String methodName = point.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
            Method method = point.getTarget().getClass().getMethod(methodName, parameterTypes);
            MdsDataSource methodAnnotation = method.getAnnotation(MdsDataSource.class);
            methodAnnotation = methodAnnotation == null ? annotationOfClass : methodAnnotation;
            MdsDataSourceNames dataSourceName = methodAnnotation != null
                    && methodAnnotation.value() != null ? methodAnnotation.value() : MdsDataSourceNames.PRIMARY;

            MdsDataSourceContextHolder.setDataSource(dataSourceName.name());
        } catch (NoSuchMethodException e) {
            logger.error("未知异常 {方法：" + point.getSignature() + "， 参数：" + point.getArgs() + ",异常：" + e.getMessage() + "}", e);
        }
    }

    @After("@annotation(MdsDataSource)")
    public void after(JoinPoint point) {
        MdsDataSourceContextHolder.clearDataSource();
    }

}
