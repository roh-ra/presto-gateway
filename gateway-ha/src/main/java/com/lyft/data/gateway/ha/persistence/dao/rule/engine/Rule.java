package com.lyft.data.gateway.ha.persistence.dao.rule.engine;

import com.lyft.data.rule.engine.models.GatewayActionType;
import com.lyft.data.rule.engine.models.RoutingRule;
import org.javalite.activejdbc.Model;
import java.util.ArrayList;
import java.util.List;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.Table;

@Cached
@Table("rule")
public class Rule extends Model {
  private static final String type = "type_name";
  private static final String subject = "subject";
  private static final String priority = "priority";
  private static final String actionType = "action_type";
  private static final String actionValue = "action";
  private static final String status = "status";

  public static List<RoutingRule> upcast(List<Rule> rules) {
    List<RoutingRule> routingRules = new ArrayList<>();
    for (Rule model : rules) {
      RoutingRule routingRule = new RoutingRule();
      routingRule.setType(model.getString(type));
      routingRule.setSubject(model.getString(subject));
      routingRule.setPriority(model.getInteger(priority));
      routingRule.setActionType((GatewayActionType) model.get(actionType));
      routingRule.setActionValue(model.getString(actionValue));
      routingRule.setStatus(model.getBoolean(status));
      routingRules.add(routingRule);
    }
    return routingRules;
  }

  public static void update(Rule model, RoutingRule routingRule) {
    model
        .set(type, routingRule.getType())
        .set(subject, routingRule.getSubject())
        .set(priority, routingRule.getPriority())
        .set(actionType, routingRule.getActionType())
        .set(actionValue, routingRule.getActionValue())
        .set(status, routingRule.getStatus())
        .saveIt();
  }

  public static void create(Rule model, RoutingRule routingRule) {
    model
        .create(
            type,
            routingRule.getType(),
            subject,
            routingRule.getSubject(),
            priority,
            routingRule.getPriority(),
            actionType,
            routingRule.getActionType(),
            actionValue,
            routingRule.getActionValue(),
            status,
            routingRule.getStatus())
        .insert();
  }
}
