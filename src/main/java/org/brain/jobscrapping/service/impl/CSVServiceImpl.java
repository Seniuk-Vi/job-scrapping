package org.brain.jobscrapping.service.impl;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.brain.jobscrapping.exceptions.CSVNotExportedException;
import org.brain.jobscrapping.model.Job;
import org.brain.jobscrapping.service.CSVService;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;

@Component
public class CSVServiceImpl implements CSVService {
    public String generateJobListCSVString(List<Job> jobList) {
        try {
            StringWriter writer = new StringWriter();
            StatefulBeanToCsv<Job> beanToCsv = new StatefulBeanToCsvBuilder<Job>(writer).build();
            beanToCsv.write(jobList);
            return writer.toString();
        } catch (Exception e) {
            throw new CSVNotExportedException("Failed to generate CSV", e);
        }
    }
}
