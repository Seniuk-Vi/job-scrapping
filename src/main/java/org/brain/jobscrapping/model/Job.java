package org.brain.jobscrapping.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jobs")
public class Job {
    @Id
    @SequenceGenerator(name = "jobs_id_sequence", sequenceName = "jobs_id_seq", allocationSize = 5)
    @GeneratedValue(generator = "jobs_id_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

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
    @Column(columnDefinition = "TEXT")
    private String description; // HTML preferred

    @CsvBindByName(column = "Job Page URL")
    @CsvBindByPosition(position = 4)
    @Column(unique = true)
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