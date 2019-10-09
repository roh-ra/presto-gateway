package com.lyft.data.gateway.ha.router;

import com.lyft.data.gateway.ha.persistence.dao.rule.engine.Rule;
import com.lyft.data.gateway.ha.persistence.dao.rule.engine.RuleType;
import com.lyft.data.gateway.ha.persistence.JdbcConnectionManager;
import com.lyft.data.rule.engine.models.RoutingRule;
import com.lyft.data.rule.engine.models.RoutingRuleType;

import java.util.List;

public class RoutingRuleEngineBackendManager implements RuleEngineBackendManager {
  private JdbcConnectionManager connectionManager;

  public RoutingRuleEngineBackendManager(JdbcConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }

  @Override
  public List<RoutingRule> getAllRules() {
    try {
      connectionManager.open();
      List<Rule> rules = Rule.findAll();
      return Rule.upcast(rules);
    } finally {
      connectionManager.close();
    }
  }

  @Override
  public List<RoutingRuleType> getAllRuleTypes() {
    try {
      connectionManager.open();
      List<RuleType> ruleTypes = RuleType.findAll();
      return RuleType.upcast(ruleTypes);
    } finally {
      connectionManager.close();
    }
  }

  @Override
  public List<RoutingRule> getActiveRules() {
    try {
      connectionManager.open();
      List<Rule> rules = Rule.where("active = ?", true);
      return Rule.upcast(rules);
    } finally {
      connectionManager.close();
    }
  }

  @Override
  public List<RoutingRuleType> getActiveRuleTypes() {
    try {
      connectionManager.open();
      List<RuleType> ruleTypes = RuleType.where("active = ?", true);
      return RuleType.upcast(ruleTypes);
    } finally {
      connectionManager.close();
    }
  }

  @Override
  public List<RoutingRule> getActiveRules(String ruleType) {
    try {
      connectionManager.open();
      List<Rule> rules = Rule.where("active = ? and type_name = ?", true, ruleType);
      return Rule.upcast(rules);
    } finally {
      connectionManager.close();
    }
  }

  @Override
  public RoutingRuleType addRuleType(RoutingRuleType ruleType) {
    return new RoutingRuleType();
  }

  @Override
  public RoutingRule addRule(RoutingRule routingRule) {
    return new RoutingRule();
  }

  @Override
  public RoutingRuleType updateRuleType(RoutingRuleType ruleType) {
    return new RoutingRuleType();
  }

  @Override
  public RoutingRule updateRule(RoutingRule rule) {
    return new RoutingRule();
  }

  @Override
  public void deactivateRule(String ruleTypeName, String subject) {
    try {
      connectionManager.open();
      RuleType.findFirst("type_name = ? and subject = ?", ruleTypeName, subject)
          .set("status", false)
          .saveIt();
    } finally {
      connectionManager.close();
    }
  }

  @Override
  public void deactivateRuleType(String ruleTypeName) {
    try {
      connectionManager.open();
      RuleType.findFirst("name = ?", ruleTypeName).set("status", false).saveIt();
    } finally {
      connectionManager.close();
    }
  }
}
