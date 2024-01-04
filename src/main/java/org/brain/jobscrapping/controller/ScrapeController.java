package org.brain.jobscrapping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brain.jobscrapping.mapper.FiltersMapper;
import org.brain.jobscrapping.mapper.JobMapper;
import org.brain.jobscrapping.model.Filters;
import org.brain.jobscrapping.model.Job;
import org.brain.jobscrapping.payload.request.FiltersRequest;
import org.brain.jobscrapping.payload.response.FiltersResponse;
import org.brain.jobscrapping.payload.response.JobListResponse;
import org.brain.jobscrapping.service.CSVService;
import org.brain.jobscrapping.service.FilterService;
import org.brain.jobscrapping.service.JobService;
import org.brain.jobscrapping.service.TechStarsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scrape")
@Slf4j
@RequiredArgsConstructor
public class ScrapeController {
    private final TechStarsService techStarsAPI;
    private final CSVService csvService;
    private final JobService jobService;
    private final FilterService filterService;

    @Operation(summary = "Get all filters and write to db")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = FiltersResponse.class), mediaType = "application/json")})})
    @GetMapping("/filters")
    @ResponseStatus(HttpStatus.OK)
    public FiltersResponse getFilters() {
        log.info("Scrape filters");
        Filters filters = techStarsAPI.scrapeFilters();
        // Export filters to DB
        filters.getIndustry().forEach(filterService::saveIfNoExists);
        filters.getJobFunction().forEach(filterService::saveIfNoExists);
        filters.getLocation().forEach(filterService::saveIfNoExists);
        return FiltersMapper.INSTANCE.toFiltersResponse(filters);
    }

    @Operation(summary = "Get all jobs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobListResponse.class), mediaType = "application/json")})})
    @PostMapping("/jobs")
    @ResponseStatus(HttpStatus.OK)
    public JobListResponse getJobs(@RequestBody FiltersRequest filters, @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        log.info("Scrape jobs");
        Filters filtersModel = FiltersMapper.INSTANCE.toFilters(filters);
        List<Job> jobs = techStarsAPI.scrapeJobs(filtersModel, sortDirection);
        return new JobListResponse(jobs.size(), JobMapper.INSTANCE.toPayloadJobList(jobs));
    }

    @Operation(summary = "Get all jobs in CSV")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "text/csv")})})
    @PostMapping("/jobs/csv")
    public ResponseEntity<String> getJobsCSV(@RequestBody FiltersRequest filters, @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        log.info("Scrape jobs csv");
        Filters filtersModel = FiltersMapper.INSTANCE.toFilters(filters);
        List<Job> jobList = techStarsAPI.scrapeJobs(filtersModel, sortDirection);
        // Export jobs to CSV
        String csvData = csvService.generateJobListCSVString(jobList);
        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=jobs.csv");
        // Return CSV data as a file download
        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }

    @Operation(summary = "Get all jobs and write to db")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobListResponse.class), mediaType = "application/json")})})
    @PostMapping("/jobs/db")
    public JobListResponse getJobsDB(@RequestBody FiltersRequest filters, @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        log.info("Scrape jobs db");
        Filters filtersModel = FiltersMapper.INSTANCE.toFilters(filters);
        List<Job> jobList = techStarsAPI.scrapeJobs(filtersModel, sortDirection);
        // Export jobs to DB
        jobList.forEach(jobService::saveIfNoExists);
        return new JobListResponse(jobList.size(), JobMapper.INSTANCE.toPayloadJobList(jobList));
    }
}
