package com.lyft.data.rule.engine.evaluator.jeasy.conditions;

import org.jeasy.rules.api.Condition;

public abstract class GatewayCondition implements Condition {

  public static GatewayCondition isTrue(String type, String subject) {
    switch (type) {
      case "user":
        return new UserRuleTypeCondition(subject);
      case "tool":
        return new ToolRuleTypeCondition(subject);
      case "hint":
      default:
        return new HintRuleTypeCondition(subject);
    }
  }
}
