package com.lyft.data.gateway.ha.persistence.dao.ruleengine;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Cached;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@IdName("type_id")
@Table("rule_type")
@Cached
public class RuleType extends Model {
    private static final String id = "type_id";
    private static final String name = "type_name";
    private static final String description = "description";
    private static final String priority = "priority";
    private static final String status = "status";
}
