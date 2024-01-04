package org.brain.jobscrapping.payload;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class JobPayload {
    @CsvBindByName(column = "Position Name")
    @CsvBindByPosition(position = 0)
    private String positionName;

    @CsvBindByName(column = "Organization Title")
    @CsvBindByPosition(position = 1)
    private String organizationTitle;

    @CsvBindByName(column = "Labor Function")
    @CsvBindByPosition(position = 2)
    private String laborFunction;

    @CsvBindByName(column = "Description")
    @CsvBindByPosition(position = 3)
    private String description; // HTML preferred

    @CsvBindByName(column = "Job Page URL")
    @CsvBindByPosition(position = 4)
    private String jobPageUrl;

    @CsvBindByName(column = "URL to Organization")
    @CsvBindByPosition(position = 5)
    private String urlToOrganization;

    @CsvBindByName(column = "Logo")
    @CsvBindByPosition(position = 6)
    private String logo;

    @CsvBindByName(column = "Location")
    @CsvBindByPosition(position = 7)
    private String location;

    @CsvBindByName(column = "Posted Date")
    @CsvBindByPosition(position = 8)
    private long postedDate; // Unix Timestamp

    @CsvBindByName(column = "Tag Names")
    @CsvBindByPosition(position = 9)
    private String tagNames; // separated by comma
}