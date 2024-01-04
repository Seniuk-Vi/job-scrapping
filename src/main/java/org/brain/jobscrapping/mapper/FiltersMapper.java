package org.brain.jobscrapping.mapper;

import org.brain.jobscrapping.model.Filters;
import org.brain.jobscrapping.payload.request.FiltersRequest;
import org.brain.jobscrapping.payload.response.FiltersResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FiltersMapper {
    FiltersMapper INSTANCE = Mappers.getMapper(FiltersMapper.class);

    Filters toFilters(FiltersRequest filtersRequest);
    FiltersRequest toFiltersRequest(Filters filters);
    FiltersResponse toFiltersResponse(Filters filters);
}
