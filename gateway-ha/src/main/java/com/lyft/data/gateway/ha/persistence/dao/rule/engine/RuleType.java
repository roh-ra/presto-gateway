package com.lyft.data.gateway.ha.persistence.dao.rule.engine;

import com.lyft.data.rule.engine.models.RoutingRuleType;
import java.util.ArrayList;
import java.util.List;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Cached
@IdName("name")
@Table("rule_type")
public class RuleType extends Model {
  private static final String name = "name";
  private static final String description = "description";
  private static final String priority = "priority";
  private static final String status = "status";

  public static List<RoutingRuleType> upcast(List<RuleType> ruleTypes) {
    List<RoutingRuleType> routingRuleTypes = new ArrayList<>();
    for (RuleType model : ruleTypes) {
      RoutingRuleType routingRuleType = new RoutingRuleType();
      routingRuleType.setName(model.getString(name));
      routingRuleType.setDescription(model.getString(description));
      routingRuleType.setPriority(model.getInteger(priority));
      routingRuleType.setStatus(model.getString(status));
      routingRuleTypes.add(routingRuleType);
    }
    return routingRuleTypes;
  }

  public static void update(RuleType model, RoutingRuleType routingRuleType) {
    model
        .set(name, routingRuleType.getName())
        .set(description, routingRuleType.getDescription())
        .set(priority, routingRuleType.getPriority())
        .set(status, routingRuleType.getStatus())
        .saveIt();
  }

  public static void create(RuleType model, RoutingRuleType routingRuleType) {
    model
        .create(
            name,
            routingRuleType.getName(),
            description,
            routingRuleType.getDescription(),
            priority,
            routingRuleType.getPriority(),
            status,
            routingRuleType.getStatus())
        .insert();
  }
}
