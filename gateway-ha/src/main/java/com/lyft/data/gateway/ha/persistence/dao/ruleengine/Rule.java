package com.lyft.data.gateway.ha.persistence.dao.ruleengine;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@IdName("rule_id")
@Table("rules")
@Cached
public class Rule extends Model {
    private static final String id = "rule_id";
    private static final String description = "description";
    private static final String priority = "priority";
    private static final String condition = "condition";
    private static final String action = "action";
    private static final String type = "type";
    private static final String status = "status";
}
