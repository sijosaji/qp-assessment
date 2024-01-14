package com.assessment.qp.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    @NotNull(message = "fieldName needs to be provided")
    private String fieldName;
    @NotNull(message = "fieldValue needs to be provided")
    private Object fieldValue;
    @NotNull(message = "operator needs to be provided")
    private String operator;
    @NotNull(message = "evaluationType needs to be provided")
    private String evaluationType;
}
