package com.lyft.data.rule.engine.models;

import lombok.Data;

@Data
public class RoutingRule {
  private String type;
  private int priority;
  private String subject;
  private GatewayActionType actionType;
  private String actionValue;
  private Boolean status;
}
