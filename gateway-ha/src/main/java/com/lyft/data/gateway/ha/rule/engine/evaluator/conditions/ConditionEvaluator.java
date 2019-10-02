package com.lyft.data.gateway.ha.rule.engine.evaluator.conditions;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

public class ConditionEvaluator implements Condition {
  private String ruleType;
  private String conditionType;
  private String conditionValue;

  public ConditionEvaluator(String ruleType, String conditionType, String conditionValue) {
    this.ruleType = ruleType;
    this.conditionType = conditionType;
    this.conditionValue = conditionValue;
  }

  public static ConditionEvaluator checkCondition(String ruleType, String conditionType, String conditionValue) {
    return new ConditionEvaluator(ruleType, conditionType,conditionValue);
  }

  @Override
  public boolean evaluate(Facts facts) {
    System.out.println("here"+facts.toString());
    System.out.println(conditionValue);
    System.out.println(ruleType);
    System.out.println(conditionType);
    if(conditionType.equals("1"))
      return facts.get(ruleType).equals(conditionValue);
    if(conditionType.equals("2"))
     return facts.get(ruleType).toString().startsWith(conditionValue);
    return false;
  }
}
