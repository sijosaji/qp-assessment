package com.assessment.qp.dto;

import com.assessment.qp.model.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchDto {

    private List<SearchCriteria> searchCriteriaList;
    private Integer limit;
    private Integer offset;

}
