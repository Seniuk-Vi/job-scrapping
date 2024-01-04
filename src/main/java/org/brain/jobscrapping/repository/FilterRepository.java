package org.brain.jobscrapping.repository;

import org.brain.jobscrapping.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository  extends JpaRepository<Filter, Long> {
    boolean existsByName(String name);
}
