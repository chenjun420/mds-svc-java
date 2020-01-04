package com.pts.mds.common.DynamicDataSource;

import java.lang.annotation.*;

/**
 * 切换数据源的注解
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MdsDataSource {

    MdsDataSourceNames value() default MdsDataSourceNames.PRIMARY;

}