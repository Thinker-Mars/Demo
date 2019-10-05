package com.cone.orange.config;

import com.cone.orange.dict.RuleDict;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.*;


/**
 * 事务配置
 * @Author Cone
 * @Date 2019/9/13 10:55
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {
    //定义pointcut表达式，项目中所有以Service结尾的的方法均受事务控制
    private static final String POINTCUT = "execution(* *..*Service.*(..))";

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    /**
     * 定义advice
     * @return
     */
    @Bean
    public TransactionInterceptor txAdvice() {
        //事务管理规则，声明具备事务管理的方法名
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("insert*", RuleDict.topRule());
        source.addTransactionalMethod("update*", RuleDict.topRule());
        source.addTransactionalMethod("query*", RuleDict.readOnlyRule());
        source.addTransactionalMethod("noRollBack*", RuleDict.noRollBackRule());

        return new TransactionInterceptor(platformTransactionManager, source);
    }

    /**
     * 定义切面
     * @return
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        //定义切点Pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //指出连接点JoinPoint
        pointcut.setExpression(POINTCUT);
        //point与advice构成了切面
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
