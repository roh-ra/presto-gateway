package com.lyft.data.rule.engine.evaluator.jeasy.conditions;

import com.lyft.data.rule.engine.evaluator.jeasy.facts.GatewayQueryFacts;
import org.jeasy.rules.api.Facts;

public class UserRuleTypeCondition extends GatewayCondition {
  private String subject;

  public UserRuleTypeCondition(String subject) {
    this.subject = subject;
  }

  @Override
  public boolean evaluate(Facts facts) {
    if (facts.asMap().containsKey("gatewayQueryFacts")) {
      GatewayQueryFacts gatewayQueryFacts = facts.get("gatewayQueryFacts");
      return gatewayQueryFacts.getUser().equals(subject);
    }
    return false;
  }
}
