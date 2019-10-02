package com.lyft.data.gateway.ha.rule.engine.evaluator.actions;

import com.lyft.data.gateway.ha.rule.engine.models.ActionType;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

public class GatewayAction implements Action {
  private ActionType type;
  private String routingGroup;

  public GatewayAction(ActionType type, String routingGroup) {
    this.type = type;
    this.routingGroup = routingGroup;
  }

  public static GatewayAction returnAction(ActionType type, String routingGroup) {
    return new GatewayAction(type, routingGroup);
  }

  @Override
  public void execute(Facts facts) throws Exception {
    facts.put("action_type", type);
    facts.put("routingGroup", routingGroup);
  }
}
