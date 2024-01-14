package com.assessment.qp.helper;

import com.assessment.qp.entity.Item;
import com.assessment.qp.enums.SearchOperation;
import com.assessment.qp.model.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class QueryHelper implements Specification<Item> {
    private final SearchCriteria searchCriteria;

    public QueryHelper(final SearchCriteria searchCriteria){
        super();
        this.searchCriteria = searchCriteria;
    }
    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String fieldName = searchCriteria.getFieldName();
        Object fieldValue = searchCriteria.getFieldValue();
        switch (Objects.requireNonNull(SearchOperation.getOperator(searchCriteria.getOperator()))) {
            case CONTAINS -> {
                if (fieldValue instanceof String) {
                     return criteriaBuilder.like((root.get(fieldName)), "%" + fieldValue + "%");
                }
            }
            case EQUAL -> {
                return criteriaBuilder.equal(root.get(fieldName), fieldValue);
            }
            case GREATER_THAN -> {
                 return criteriaBuilder.greaterThan(root.get(fieldName), fieldValue.toString());
            }
            case GREATER_THAN_EQUAL -> {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), fieldValue.toString());
            }
            case LESS_THAN -> {
                return criteriaBuilder.lessThan(root.get(fieldName), fieldValue.toString());
            }
            case LESS_THAN_EQUAL -> {
                return criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), fieldValue.toString());
            }
        }
        return null;
    }
}
