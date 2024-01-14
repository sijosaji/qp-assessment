package com.assessment.qp.dto;

import com.assessment.qp.model.SearchCriteria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchDto {

    private List<SearchCriteria> searchCriteriaList;
    @NotNull(message = "limit needs to be provided")
    private Integer limit;
    @NotNull(message = "offset needs to be provided")
    private Integer offset;

}
