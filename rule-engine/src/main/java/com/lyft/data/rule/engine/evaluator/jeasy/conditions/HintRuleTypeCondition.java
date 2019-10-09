package com.lyft.data.rule.engine.evaluator.jeasy.conditions;

import com.lyft.data.rule.engine.evaluator.jeasy.facts.GatewayQueryFacts;
import org.jeasy.rules.api.Facts;

public class HintRuleTypeCondition extends GatewayCondition {
  private String subject;

  public HintRuleTypeCondition(String subject) {
    this.subject = subject;
  }

  @Override
  public boolean evaluate(Facts facts) {
    if (facts.asMap().containsKey("gatewayQueryFacts")) {
      GatewayQueryFacts gatewayQueryFacts = facts.get("gatewayQueryFacts");
      return gatewayQueryFacts.getQueryText().contains(subject);
    }
    return false;
  }
}
