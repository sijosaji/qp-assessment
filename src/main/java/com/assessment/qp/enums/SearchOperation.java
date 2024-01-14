package com.assessment.qp.enums;

public enum SearchOperation {

    CONTAINS, EQUAL, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN,
    LESS_THAN_EQUAL, AND, OR;

    public static SearchOperation getEvaluationType(String evaluationType){
        switch(evaluationType){
            case "AND": return AND;
            case "OR": return OR;
            default: return null;
        }
    }

    public static SearchOperation getOperator(String input) {
        switch (input){
            case "cn": return CONTAINS;
            case "eq": return EQUAL;
            case "gt": return GREATER_THAN;
            case "ge": return GREATER_THAN_EQUAL;
            case "lt": return LESS_THAN;
            case "le": return LESS_THAN_EQUAL;

            default: return null;
        }
    }
}