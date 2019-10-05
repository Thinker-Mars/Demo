package com.cone.orange.dict;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import java.util.Collections;

/**
 * 事务规则
 * @Author Cone
 * @Date 2019/9/13 12:55
 */
public class RuleDict {

    /**
     * 事务传播性为 PROPAGATION_REQUIRED，隔离级别为最高级，确保阻止脏读、不可重复读以及幻读
     * 回滚规则为Exception
     * @return
     */
    public static final RuleBasedTransactionAttribute topRule() {
        RuleBasedTransactionAttribute topRule = new RuleBasedTransactionAttribute();
        topRule.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        topRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        topRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        return topRule;
    }

    /**
     * 定义事务的规则2，事务传播性为 PROPAGATION_REQUIRED，事务隔离性为 read-only
     * @return
     */
    public static final RuleBasedTransactionAttribute readOnlyRule() {
        RuleBasedTransactionAttribute readOnlyRule = new RuleBasedTransactionAttribute();
        readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnlyRule.setReadOnly(true);
        return readOnlyRule;
    }

    /**
     * 事务传播性为 PROPAGATION_REQUIRED，遇到异常不回滚
     * @return
     */
    public static final RuleBasedTransactionAttribute noRollBackRule() {
        RuleBasedTransactionAttribute noRollBackRule = new RuleBasedTransactionAttribute();
        noRollBackRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        noRollBackRule.setRollbackRules
                (Collections.singletonList(new NoRollbackRuleAttribute(Exception.class)));
        return noRollBackRule;
    }

}
