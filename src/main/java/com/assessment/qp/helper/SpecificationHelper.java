package com.assessment.qp.helper;

import com.assessment.qp.entity.Item;
import com.assessment.qp.enums.SearchOperation;
import com.assessment.qp.model.SearchCriteria;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
@Data
public class SpecificationHelper {

    private List<SearchCriteria> filters;

    /**
     * Method to build custom query from Filters provided.
     * @return {@link Specification<Item>}
     */
    public Specification<Item> buildSpecification() {
        if (filters.isEmpty()) {
            return null;
        }

        Specification<Item> result = new QueryHelper(filters.get(0));
        for (int idx = 1; idx < filters.size(); idx++){
            SearchCriteria criteria = filters.get(idx);
            result =  SearchOperation.getEvaluationType(criteria.getEvaluationType()) == SearchOperation.AND
                    ? Specification.where(result).and(new QueryHelper(criteria))
                    : Specification.where(result).or(new QueryHelper(criteria));
        }
        return result;

    }

}
