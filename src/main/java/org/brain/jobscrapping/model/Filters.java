package org.brain.jobscrapping.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Filters {
    @JsonProperty("job_functions")
    private List<String> JobFunction;
    @JsonProperty("searchable_locations")
    private List<String> Location;
    @JsonProperty("organization.industry_tags")
    private List<String> Industry;
//    private List<Filter> CompanySize;
//    private List<Filter> CompanyStage;
//    private List<Filter> Topics;
//    private List<Filter> Company;
}
