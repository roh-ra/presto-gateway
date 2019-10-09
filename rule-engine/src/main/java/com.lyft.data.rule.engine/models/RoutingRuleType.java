package com.lyft.data.rule.engine.models;

import lombok.Data;

@Data
public class RoutingRuleType {
  private String name;
  private String description;
  private int priority;
  private String status;
}
