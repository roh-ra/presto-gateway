package com.lyft.data.gateway.ha.rule.engine;

import com.lyft.data.gateway.ha.config.DataStoreConfiguration;
import com.lyft.data.gateway.ha.persistence.JdbcConnectionManager;
import com.lyft.data.gateway.ha.persistence.dao.ruleengine.RuleDetails;
import com.lyft.data.gateway.ha.persistence.dao.ruleengine.RuleType;

import com.lyft.data.gateway.ha.rule.engine.evaluator.actions.GatewayAction;
import com.lyft.data.gateway.ha.rule.engine.evaluator.conditions.ConditionEvaluator;
import com.lyft.data.gateway.ha.rule.engine.models.ActionType;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
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


  public static List<RuleDetails> getAllRules(RuleType type) {

      return RuleDetails.where("type_id = ?", type.get("type_id"));

  }

  // Load all the rule
  public static Rules register() {
    // Load rules based on type priority
    connectionManager.open();
    ruleTypes = RuleType.findAll();
    Rules rules = new Rules();
    for (RuleType ruleType : ruleTypes) {
      List<RuleDetails> ruleDetails = getAllRules(ruleType);
      for (RuleDetails ruleDetail : ruleDetails) {
        rules.register(
            new RuleBuilder()
                .name(ruleDetail.get("rule_id").toString())
                .priority((int) (ruleType.get("priority")))
                .when(
                    new ConditionEvaluator(
                        ruleType.get("type_name").toString(),
                        ruleDetail.get("condition_type").toString(),
                        ruleDetail.get("condition_value").toString()))
                .then(
                    new GatewayAction(
                        ActionType.valueOf((ruleDetail.get("action_type").toString())),
                        ruleDetail.get("action_value").toString()))
                .build());
       }

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
    Facts facts = new Facts();
    Rules rules = register();
    facts.put("user", "pganeshan@ea.com");
    facts.put("source", "superset");
    RulesEngine rulesEngine = new DefaultRulesEngine();
    rulesEngine.check(rules, facts);
    rulesEngine.fire(rules, facts);
    System.out.println(facts.toString());
    System.out.println(facts.get("action_type").toString());
    System.out.println(facts.get("routingGroup").toString());
    facts = new Facts();
    facts.put("source", "tableau");
    rulesEngine.fire(rules, facts);
    System.out.println(facts.get("action_type").toString());
    System.out.println(facts.get("routingGroup").toString());

  }
}
