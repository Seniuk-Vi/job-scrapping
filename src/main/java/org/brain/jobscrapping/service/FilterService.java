package org.brain.jobscrapping.service;

import org.brain.jobscrapping.model.Filter;

public interface FilterService {
    void saveIfNoExists(Filter filter);
    void saveIfNoExists(String filter);
}
