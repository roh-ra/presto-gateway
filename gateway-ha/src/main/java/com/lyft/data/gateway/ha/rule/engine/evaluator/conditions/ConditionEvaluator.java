package com.lyft.data.gateway.ha.rule.engine.evaluator.conditions;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

public class ConditionEvaluator implements Condition {
  private static String ruleType;
  private static String conditionType;
  private static String conditionValue;

  ConditionEvaluator(String ruleType, String conditionValue) {
    this.ruleType = ruleType;
    this.conditionType = conditionType;
    this.conditionValue = conditionValue;
  }

  public static ConditionEvaluator checkCondition(String ruleType, String conditionType, String conditionValue) {
    return new ConditionEvaluator(ruleType, conditionValue);
  }

  @Override
  public boolean evaluate(Facts facts) {
    if(conditionType.equals("1"))
      return facts.get(ruleType).equals(conditionValue);
    if(conditionType.equals("2"))
     return facts.get(ruleType).toString().startsWith(conditionValue);
    return false;
  }
}
