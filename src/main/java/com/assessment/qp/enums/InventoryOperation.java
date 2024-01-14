package com.assessment.qp.enums;

public enum InventoryOperation {
    ADD,
    MINUS;
    public static InventoryOperation getOperator(String input) {
        switch (input){
            case "add": return ADD;
            case "minus", "sub", "subtract": return MINUS;
            default: return null;
        }
    }
}
