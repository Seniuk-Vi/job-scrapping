package org.brain.jobscrapping.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brain.jobscrapping.model.Job;
import org.brain.jobscrapping.repository.JobRepository;
import org.brain.jobscrapping.service.JobService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    @Override
    public void saveIfNoExists(Job job) {
        log.info("Saving job to the database");
        if(jobRepository.existsByJobPageUrl(job.getJobPageUrl())){
            log.debug("Job already exists in the database");
            return;
        }
        jobRepository.save(job);
    }
}
