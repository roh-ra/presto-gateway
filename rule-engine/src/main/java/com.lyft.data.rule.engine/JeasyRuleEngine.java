package com.lyft.data.rule.engine;

import com.lyft.data.rule.engine.evaluator.jeasy.actions.GatewayAction;
import com.lyft.data.rule.engine.evaluator.jeasy.conditions.GatewayCondition;
import com.lyft.data.rule.engine.evaluator.jeasy.facts.GatewayQueryFacts;
import com.lyft.data.rule.engine.models.GatewayActionType;
import com.lyft.data.rule.engine.models.RoutingRule;
import com.lyft.data.rule.engine.models.RoutingRuleType;
import java.util.ArrayList;
import java.util.List;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

public class JeasyRuleEngine extends RuleEngineFactory {
  private static List<RoutingRule> routingRules;
  private static Rules rules;
  private static RulesEngine rulesEngine;

  public JeasyRuleEngine(List<RoutingRuleType> routingRuleTypes, List<RoutingRule> routingRules) {
    super(routingRuleTypes, routingRules);
    this.rules = new Rules();
    this.rulesEngine = new DefaultRulesEngine();
  }

  public void registerRuleEngine() {
    for (RoutingRule rule : routingRules) {
      rules.register(
          new RuleBuilder()
              .name(rule.getType() + ":" + rule.getSubject())
              .priority(rule.getPriority())
              .when(GatewayCondition.isTrue(rule.getType(), rule.getSubject()))
              .then(GatewayAction.getAction(rule.getActionType(), rule.getActionValue()))
              .build());
    }
  }

  public void process(GatewayQueryFacts gatewayQueryFacts) {
    Facts facts = new Facts();
    facts.put("gatewayQueryFacts", gatewayQueryFacts);
    rulesEngine.fire(rules, facts);
    gatewayQueryFacts = facts.get("gatewayQueryFacts");
    System.out.println(gatewayQueryFacts.toString());
  }

  public static void main(String[] args) {
    routingRules = new ArrayList<>();
    RoutingRule rule = new RoutingRule();
    rule.setType("user");
    rule.setSubject("yjin@ea.com");
    rule.setPriority(1);
    rule.setActionType(GatewayActionType.BLACKLIST);
    rule.setActionValue("");
    rule.setStatus(true);
    routingRules.add(rule);
    JeasyRuleEngine jeasyRuleEngine = new JeasyRuleEngine(new ArrayList<>(), routingRules);
    jeasyRuleEngine.registerRuleEngine();
    GatewayQueryFacts gatewayQueryFacts = new GatewayQueryFacts();
    gatewayQueryFacts.setUser("yjin@ea.com");
    jeasyRuleEngine.process(gatewayQueryFacts);
  }
}
