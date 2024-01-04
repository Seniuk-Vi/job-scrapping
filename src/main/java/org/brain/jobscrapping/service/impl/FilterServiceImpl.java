package org.brain.jobscrapping.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.brain.jobscrapping.model.Filter;
import org.brain.jobscrapping.repository.FilterRepository;
import org.brain.jobscrapping.service.FilterService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class FilterServiceImpl implements FilterService {
    private final FilterRepository filterRepository;
    @Override
    public void saveIfNoExists(Filter filter) {
        log.info("Saving filter to the database");
        if(filterRepository.existsByName(filter.getName())){
            log.debug("Filter already exists in the database");
            return;
        }
        filterRepository.save(filter);
    }
    @Override
    public void saveIfNoExists(String filter) {
        log.info("Saving filter to the database");
        if(filterRepository.existsByName(filter)){
            log.debug("Filter already exists in the database");
            return;
        }
        filterRepository.save(Filter.builder().name(filter).build());
    }
}
