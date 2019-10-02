package com.lyft.data.gateway.ha.rule.engine;

import com.lyft.data.gateway.ha.config.DataStoreConfiguration;
import com.lyft.data.gateway.ha.persistence.JdbcConnectionManager;
import com.lyft.data.gateway.ha.persistence.dao.ruleengine.RuleDetails;
import com.lyft.data.gateway.ha.persistence.dao.ruleengine.RuleType;

import com.lyft.data.gateway.ha.rule.engine.models.ActionType;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import java.util.List;

import static com.lyft.data.gateway.ha.rule.engine.evaluator.actions.GatewayAction.returnAction;
import static com.lyft.data.gateway.ha.rule.engine.evaluator.conditions.ConditionEvaluator.checkCondition;

public class RuleEngine {
  private static JdbcConnectionManager connectionManager;
  private static List<RuleType> ruleTypes;

  public RuleEngine(JdbcConnectionManager connectionManager) {
    this.connectionManager = connectionManager;
  }

  public static void getAllRulesTypes() {
    try {
      connectionManager.open();
      ruleTypes = RuleType.findAll();
    } finally {
      connectionManager.close();
    }
  }

  public static List<RuleDetails> getAllRules(RuleType type) {
    try {
      connectionManager.open();
      return RuleDetails.where("type_id = ?", type.get("type_id"));
    } finally {
      connectionManager.close();
    }
  }

  // Load all the rule
  public static Rules register() {
    // Load rules based on type priority
    connectionManager.open();
    Rules rules = new Rules();
    for (RuleType ruleType : ruleTypes) {
      List<RuleDetails> ruleDetails = getAllRules(ruleType);
      for (RuleDetails ruleDetail : ruleDetails)
        rules.register(
            new RuleBuilder()
                .name(ruleDetail.get("rule_id").toString())
                .priority((int) (ruleType.get("priority")))
                .when(
                    checkCondition(
                        ruleType.get("type_name").toString(),
                        ruleDetail.get("condition_type").toString(),
                        ruleDetail.get("condition_value").toString()))
                .then(
                    returnAction(
                        (ActionType) ruleDetail.get("action_type"),
                        ruleDetail.get("action_value").toString())));
    }

    connectionManager.close();
    return rules;
  }

  public static void main(String[] args) {

    DataStoreConfiguration dataStoreConfiguration = new DataStoreConfiguration();
    dataStoreConfiguration.setDriver("com.mysql.cj.jdbc.Driver");
    dataStoreConfiguration.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/prestogateway");
    dataStoreConfiguration.setUser("root");
    dataStoreConfiguration.setPassword("root123");
    RuleEngine ruleEngine = new RuleEngine(new JdbcConnectionManager(dataStoreConfiguration));
    getAllRulesTypes();
    Rules rules = register();
    Facts facts = new Facts();
    facts.put("user", "pganeshan@ea.com");
    facts.put("source", "superset");
    RulesEngine rulesEngine = new DefaultRulesEngine();
    rulesEngine.fire(rules, facts);
    facts = new Facts();
    facts.put("source", "tableau");
  }
}
