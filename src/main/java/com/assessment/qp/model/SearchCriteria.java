package com.assessment.qp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {

    private String fieldName;
    private Object fieldValue;
    private String operator;
    private String evaluationType;
}
