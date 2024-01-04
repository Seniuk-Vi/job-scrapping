package org.brain.jobscrapping.service;

import org.brain.jobscrapping.model.Filters;
import org.brain.jobscrapping.model.Job;

import java.util.List;

public interface TechStarsService {

    Filters scrapeFilters();

    List<Job> scrapeJobs(Filters filter, String sortDirection);
}
