package com.lyft.data.rule.engine;

import com.lyft.data.rule.engine.evaluator.jeasy.facts.GatewayQueryFacts;
import com.lyft.data.rule.engine.models.RoutingRule;
import com.lyft.data.rule.engine.models.RoutingRuleType;

import java.util.List;

public abstract class RuleEngineFactory {
  private List<RoutingRuleType> routingRuleTypes;
  private List<RoutingRule> routingRules;

  public RuleEngineFactory(List<RoutingRuleType> routingRuleTypes, List<RoutingRule> routingRules) {
    this.routingRuleTypes = routingRuleTypes;
    this.routingRules = routingRules;
  }

  abstract  void registerRuleEngine();

  abstract void process(GatewayQueryFacts gatewayQueryFacts);

  private enum RuleEngineType {
    JAVA_EASY
  }

  public RuleEngineFactory getRuleEngine(String engineType) {
    RuleEngineType ruleEngineType = RuleEngineType.valueOf(engineType);
    switch (ruleEngineType) {
      case JAVA_EASY:
      default:
        return new JeasyRuleEngine(routingRuleTypes, routingRules);
    }
  }
}
