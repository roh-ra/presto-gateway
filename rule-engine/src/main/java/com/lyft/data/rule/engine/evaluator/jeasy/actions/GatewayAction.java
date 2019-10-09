package com.lyft.data.rule.engine.evaluator.jeasy.actions;

import com.lyft.data.rule.engine.evaluator.jeasy.facts.GatewayQueryFacts;
import com.lyft.data.rule.engine.models.GatewayActionType;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

public class GatewayAction implements Action {
  private GatewayActionType type;
  private String routingGroup;

  public GatewayAction(GatewayActionType type, String routingGroup) {
    this.type = type;
    this.routingGroup = routingGroup;
  }

  public static GatewayAction getAction(GatewayActionType type, String routingGroup) {
    return new GatewayAction(type, routingGroup);
  }

  @Override
  public void execute(Facts facts) {
    GatewayQueryFacts gatewayQueryFacts = facts.get("gatewayQueryFacts");
    gatewayQueryFacts.setActionType(type);
    gatewayQueryFacts.setRoutingGroup(routingGroup);
  }
}
