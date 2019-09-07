package com.cone.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 登录切面
 * 各个advice的调用顺序为@Around、@Before、@After、@AfterReturning（有异常则为@AfterThrowing）
 * @Author Cone
 * @Date 2019/9/7 21:20
 */
@Aspect
@Component
public class LogAspect {

    //这里定义了切点Pointcut，并指出了连接点JoinPoint，即符合条件的方法，目前Spring Aop仅支持方法执行
    @Pointcut("execution(public * com.cone.aop.controller.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        //RequestContextHolder,持有上下文的Request容器
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求内容
        System.out.println("url: " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD: " + request.getMethod());
        System.out.println("ip: " + request.getRemoteAddr());
        //输出内容：类的全限定名.被调用方法名
        System.out.println("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName()
        + "." + joinPoint.getSignature().getName());
        //输出内容：方法的返回值
        System.out.println("args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        //处理完请求，返回内容
        System.out.println("return value: " + ret);
    }

    @AfterThrowing("webLog()")
    public void error(JoinPoint joinPoint) {

        System.out.println("方法异常时执行.....");
    }

    @After("webLog()")
    public void after(JoinPoint joinPoint) {

        System.out.println("方法最后执行.....");
    }

    @Around("webLog()")
    public Object arround(ProceedingJoinPoint proceedingJoinPoint) {

        System.out.println("方法环绕start.....");
        try {
            Object o = proceedingJoinPoint.proceed();
            System.out.println("方法环绕proceed，结果是: " + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

    }


}
