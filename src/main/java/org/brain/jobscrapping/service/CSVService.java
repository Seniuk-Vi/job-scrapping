package org.brain.jobscrapping.service;

import org.brain.jobscrapping.model.Job;

import java.util.List;

public interface CSVService {
    String generateJobListCSVString(List<Job> jobList);

}
