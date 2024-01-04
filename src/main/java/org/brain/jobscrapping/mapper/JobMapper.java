package org.brain.jobscrapping.mapper;

import org.brain.jobscrapping.model.Job;
import org.brain.jobscrapping.payload.JobPayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    List<JobPayload> toPayloadJobList(List<Job> jobs);
}
