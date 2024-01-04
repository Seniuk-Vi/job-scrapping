package org.brain.jobscrapping.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.brain.jobscrapping.payload.JobPayload;

import java.util.List;

@Data
@AllArgsConstructor
public class JobListResponse {
    private Integer numberOfJobs;
    private List<JobPayload> jobs;
}
