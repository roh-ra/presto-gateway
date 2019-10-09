package com.lyft.data.gateway.ha.router;

import com.lyft.data.rule.engine.models.RoutingRule;
import com.lyft.data.rule.engine.models.RoutingRuleType;

import java.util.List;

public interface RuleEngineBackendManager {

  List<RoutingRule> getAllRules();

  List<RoutingRuleType> getAllRuleTypes();

  List<RoutingRule> getActiveRules();

  List<RoutingRule> getActiveRules(String ruleType);

  List<RoutingRuleType> getActiveRuleTypes();

  RoutingRuleType addRuleType(RoutingRuleType ruleType);

  RoutingRule addRule(RoutingRule routingRule);

  RoutingRuleType updateRuleType(RoutingRuleType ruleType);

  RoutingRule updateRule(RoutingRule routingRule);

  void deactivateRuleType(String ruleType);

  void deactivateRule(String ruleType, String subject);
}
