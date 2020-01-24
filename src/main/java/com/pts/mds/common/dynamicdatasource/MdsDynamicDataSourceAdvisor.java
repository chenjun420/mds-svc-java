package com.pts.mds.common.dynamicdatasource;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * aop植入
 */
public class MdsDynamicDataSourceAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private Advice advice;

    private Pointcut pointcut;

    public MdsDynamicDataSourceAdvisor(MdsDynamicDataSourceMethodInterceptor mdsDynamicDataSourceMethodInterceptor) {
        this.advice = mdsDynamicDataSourceMethodInterceptor;
        this.pointcut = buildPointcut();
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    private Pointcut buildPointcut() {
        Pointcut cpc = (Pointcut) new AnnotationMatchingPointcut(MdsDataSource.class, true);
        // 类注解
        Pointcut clpc = (Pointcut) AnnotationMatchingPointcut.forClassAnnotation(MdsDataSource.class);
        // 方法注解
        Pointcut mpc = (Pointcut) AnnotationMatchingPointcut.forMethodAnnotation(MdsDataSource.class);
        return new ComposablePointcut(cpc).union(clpc).union(mpc);
    }
}
