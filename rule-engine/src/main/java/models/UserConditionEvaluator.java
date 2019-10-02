package models;

import java.util.Set;

public class UserConditionEvaluator extends ConditionEvaluator {

    private String username;
    private Set<String> collection;

    UserConditionEvaluator(String candidate, Set<String> collection) {
        super();
        this.username = username;
        this.collection = collection;
    }

    private boolean isPresent() {
        return this.collection.contains(username);

    }
    @Override
    public boolean evaluate() {
        return this.isPresent();
    }
}
