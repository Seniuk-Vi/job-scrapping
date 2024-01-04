package org.brain.jobscrapping.repository;

import org.brain.jobscrapping.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByJobPageUrl(String jobPageUrl);
}
