package com.pts.mds.common.DynamicDataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MdsDynamicDataSourceMethodInterceptor implements MethodInterceptor {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(MdsDynamicDataSourceMethodInterceptor.class);

    /**
     * 缓存方法注解值
     */
    private static final Map<Method, MdsDataSourceNames> METHOD_CACHE = new HashMap<>();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            MdsDataSourceNames dataSourceName = determineDatasource(invocation);
            logger.info("设置数据源: > {}", dataSourceName.name());
            MdsDataSourceContextHolder.setDataSource(dataSourceName);
            return invocation.proceed();
        } finally {
            MdsDataSourceContextHolder.clearDataSource();
        }
    }

    private MdsDataSourceNames determineDatasource(MethodInvocation invocation) {
        Method method = invocation.getMethod();
        if (METHOD_CACHE.containsKey(method)) {
            return METHOD_CACHE.get(method);
        } else {
            MdsDataSource ds = method.isAnnotationPresent(MdsDataSource.class) ? method.getAnnotation(MdsDataSource.class)
                    : AnnotationUtils.findAnnotation(method.getDeclaringClass(), MdsDataSource.class);
            METHOD_CACHE.put(method, ds.value());
            return ds.value();
        }
    }
}
