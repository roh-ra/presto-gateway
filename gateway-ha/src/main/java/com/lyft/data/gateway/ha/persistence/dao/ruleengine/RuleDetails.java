package com.lyft.data.gateway.ha.persistence.dao.ruleengine;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@IdName("rule_id")
@Table("rule")
@Cached
public class RuleDetails extends Model {
    private static final String id = "rule_id";
    private static final String type = "type_id";
    private static final String description = "description";
    private static final String priority = "priority";
    private static final String conditionType = "condition";
    private static final String conditionValue = "condition";
    private static final String actionType = "action_type";
    private static final String actionValue = "action";
    private static final String status = "status";
}
