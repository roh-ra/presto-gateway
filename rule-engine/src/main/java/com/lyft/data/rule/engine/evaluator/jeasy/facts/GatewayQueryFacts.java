package com.lyft.data.rule.engine.evaluator.jeasy.facts;

import com.lyft.data.rule.engine.models.GatewayActionType;
import lombok.Data;

@Data
public class GatewayQueryFacts {
  private String user;
  private String queryText;
  private String sourceHeader;
  private String routingHeader;
  private GatewayActionType actionType;
  private String routingGroup;
}
