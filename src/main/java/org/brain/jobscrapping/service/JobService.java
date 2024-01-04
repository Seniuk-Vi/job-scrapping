package org.brain.jobscrapping.service;

import org.brain.jobscrapping.model.Job;

public interface JobService {
    void saveIfNoExists(Job job);
}
