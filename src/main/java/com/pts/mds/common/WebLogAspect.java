package com.pts.mds.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * 进入方法时间戳
     */
    private Long startTime;

    /**
     * 方法结束时间戳(计时)
     */
    private Long endTime;

    /**
     * 定义切入点.
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.pts.mds.controller.*.*(..))")
    public void webLog() {
    }

    /**
     * 环绕通知
     */
    @Around("webLog()")
    public ApiResponse arround(ProceedingJoinPoint pjp) {
        ApiResponse apiResponse;

        try {
            //处理入参特殊字符和sql注入攻击
            checkRequestParam(pjp);

            //执行访问接口操作
            apiResponse = (ApiResponse) pjp.proceed(pjp.getArgs());
        } catch (Throwable e) {
            apiResponse = handlerException(pjp, e);
        }

        return apiResponse;
    }

    /**
     * 处理接口调用异常
     * @param pjp
     * @param e
     * @return
     */
    private ApiResponse handlerException(ProceedingJoinPoint pjp, Throwable e) {
        logger.error("未知异常 {方法：" + pjp.getSignature() + "， 参数：" + pjp.getArgs() + ",异常：" + e.getMessage() + "}", e);
        ApiResponse apiResponse = new ApiResponse<String>(false, -1, e.getMessage(), "");

        return apiResponse;
    }

    /**
     * 前置通知：
     * 1. 在执行目标方法之前执行，比如请求接口之前的登录验证;
     * 2. 在前置通知中设置请求日志信息，如开始时间，请求参数，注解内容等
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //打印请求的内容
        startTime = System.currentTimeMillis();
        logger.info("请求开始时间：{}" + LocalDateTime.now());
        logger.info("请求Url : {}" + request.getRequestURL().toString());
        logger.info("请求方式 : {}" + request.getMethod());
        logger.info("请求ip : {}" + request.getRemoteAddr());
        logger.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("请求参数 : {}" + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 返回通知：
     * 1. 在目标方法正常结束之后执行
     * 1. 在返回通知中补充请求日志信息，如返回时间，方法耗时，返回值，并且保存日志信息
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        logger.info("请求结束时间：{}" + LocalDateTime.now());
        logger.info("请求耗时：{}" + (endTime - startTime));
        // 处理完请求，返回内容
        logger.info("请求返回 : {}" + ret);
    }

    /**
     * 异常通知：
     * 1. 在目标方法非正常结束，发生异常或者抛出异常时执行
     * 1. 在异常通知中设置异常信息，并将其保存
     *
     * @param throwable
     */
    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        // 保存异常日志记录
        logger.error("发生异常时间：{}" + LocalDateTime.now());
        logger.error("抛出异常：{}" + throwable.getMessage());
    }

    /**
     * 处理入参特殊字符和sql注入攻击
     */
    private void checkRequestParam(ProceedingJoinPoint pjp){
        String str = String.valueOf(pjp.getArgs());
        if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
            String errorinfo = "访问接口：" + pjp.getSignature() + "，输入参数存在SQL注入风险！参数为：" + pjp.getArgs().toString();
            logger.info(errorinfo);
            throw new RuntimeException(errorinfo);
        }
        if (!IllegalStrFilterUtil.isIllegalStr(str)) {
            String errorinfo = "访问接口：" + pjp.getSignature() + ",输入参数含有非法字符!，参数为：" + pjp.getArgs().toString();
            logger.info(errorinfo);
            throw new RuntimeException(errorinfo);
        }
    }

}
