package org.brain.jobscrapping.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.brain.jobscrapping.model.Filters;
import org.brain.jobscrapping.model.Job;
import org.brain.jobscrapping.service.TechStarsService;
import org.brain.jobscrapping.utils.Base64Helper;
import org.brain.jobscrapping.utils.DateConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TechStarsServiceImpl implements TechStarsService {
    private final Base64Helper base64Helper;
    private final ObjectMapper objectMapper;
    private final DateConverter dateConverter;
    private WebDriverWait wait;
    private static final String URL = "https://jobs.techstars.com/jobs";
    private final ChromeDriver chromeDriver;
    @PostConstruct
    void postConstruct(){
        this.wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(7));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
    }

    private final String JOB_FUNCTION_BUTTON_CLASS = ".sc-dmqHEX.enTheS";
    private final String JOB_FUNCTIONS_ELEMENTS_CLASS = ".sc-beqWaB.cTPvSS";

    private final String LOCATION_BUTTON_CLASS = ".sc-dmqHEX.enTheS";
    private final String LOCATION_ELEMENTS_CLASS = "sc-beqWaB fzXBnb";
    private final String INDUSTRY_BUTTON_CLASS = "sc-dmqHEX enTheS";
    private final String INDUSTRY_ELEMENTS_CLASS = "sc-beqWaB cTPvSS";
    private final String COMPANY_SIZE_BUTTON_CLASS = ".sc-dmqHEX.enTheS";
    private final String COMPANY_SIZE_ELEMENTS_CLASS = ".sc-beqWaB.cTPvSS";
    private final String COMPANY_STAGE_BUTTON_CLASS = ".sc-dmqHEX.enTheS";
    private final String COMPANY_STAGE_ELEMENTS_CLASS = ".sc-beqWaB.cTPvSS";
    private final String TOPICS_BUTTON_CLASS = ".sc-dmqHEX.enTheS";
    private final String TOPICS_ELEMENTS_CLASS = ".sc-beqWaB.cTPvSS";
    private final String COMPANY_BUTTON_CLASS = ".sc-dmqHEX.enTheS";
    private final String COMPANY_ELEMENTS_CLASS = ".sc-beqWaB.cTPvSS";

    public Filters scrapeFilters() {
        chromeDriver.get(URL);
        waitLoading();
        closeCookiesApproval();
        // scrape filters
        Filters filters = new Filters();
        filters.setJobFunction(scrapeJobFunctionFilter());
        filters.setLocation(scrapeLocationFilter());
        filters.setIndustry(scrapeIndustryFilter());
//        filters.setCompanySize(scrapeCompanySizeFilter());
//        filters.setCompanyStage(scrapeCompanyStageFilter());
//        filters.setTopics(scrapeTopicsFilter());
//        filters.setCompany(scrapeCompanyFilter());
        return filters;
    }

    @SneakyThrows
    @Override
    public List<Job> scrapeJobs(Filters filter, String sortDirection) {
        // get encoded filters
        String jsonFilters = objectMapper.writeValueAsString(filter);
        String encodedFilters = base64Helper.encode64(jsonFilters);
        // get jobs with encodedFilters in params
        chromeDriver.get(URL + "?filter=" + encodedFilters);
        waitLoading();
        closeCookiesApproval();
        // scrape jobs
        List<Job> jobList = scrapeJobs();
        // sort jobs
        if ("desc".equalsIgnoreCase(sortDirection)) {
            jobList.sort(Comparator.comparing(Job::getPostedDate).reversed());
        } else {
            jobList.sort(Comparator.comparing(Job::getPostedDate));
        }
        return jobList;
    }

    private List<Job> scrapeJobs() {
        log.info("Scrape jobs");
        // Find Job junction filter button
        List<WebElement> jobsElements = chromeDriver.findElements(By.cssSelector(".sc-beqWaB.gupdsY.job-card "));
        // Wait for the jobs to load
        waitLoading();
        List<Job> jobList = new ArrayList<>();
        for (WebElement jobElement : jobsElements) {
            log.info("Scraping job - {}", jobElement);
            Job job = new Job();
            // job page url
            job.setJobPageUrl(jobElement.findElement(By.cssSelector(".sc-beqWaB.eMSmau.theme_only")).getAttribute("href"));
            // logo
            job.setLogo(jobElement.findElement(By.cssSelector(".sc-dmqHEX.eTCoCQ")).getAttribute("src"));
            // posted date
            String stringDate = jobElement.findElement(By.cssSelector("[itemprop='datePosted']")).getAttribute("content"); // like 2024-01-03
            job.setPostedDate(dateConverter.dateToUnixTimestamp(stringDate));
            // position name
            job.setPositionName(jobElement.findElement(By.cssSelector(".sc-beqWaB.kToBwF")).getText());
            // url to organization
            job.setUrlToOrganization(jobElement.findElement(By.cssSelector(".sc-beqWaB.iwxlWP.theme_only")).getAttribute("href"));
            // organization title
            job.setOrganizationTitle(jobElement.findElement(By.cssSelector(".sc-beqWaB.iwxlWP.theme_only")).getText());
            // location, can be not present
            List<WebElement> location = jobElement.findElements(By.cssSelector("[itemprop='address']"));

            if (jobElement.findElements(By.cssSelector("[itemprop='address']")).isEmpty()) {
                job.setLocation("NOT_FOUND");
            } else {
                job.setLocation(location.get(0).getAttribute("content"));
            }
            // tag names
            StringBuilder tagNames = new StringBuilder();
            List<WebElement> tags = jobElement.findElements(By.cssSelector(".sc-dmqHEX.dncTlc"));
            for (WebElement tag : tags) {
                tagNames.append(tag.getText()).append(", ");
            }
            job.setTagNames(tagNames.toString());
            jobList.add(job);
        }
        for (var job : jobList) {
            // check if job is on jobs.techstars.com and add more info
            if (job.getJobPageUrl().startsWith("https://jobs.techstars.com/")) {
                log.info("Scraping job detaily - {}", job.getPositionName());

                chromeDriver.get(job.getJobPageUrl());
                waitLoading();
                // description
                WebElement divElement = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.fmCCHr"));
                String descriptionHTML = divElement.getAttribute("innerHTML");
                job.setDescription(descriptionHTML);
                // labor function
                WebElement laborFunctionElement = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.bpXRKw"));
                job.setLaborFunction(laborFunctionElement.getText());
            } else {
                job.setDescription("NOT_FOUND");
                job.setLaborFunction("NOT_FOUND");
            }
        }
        return jobList;
    }

    private List<String> scrapeJobFunctionFilter() {
        // Find Job junction filter button
        WebElement button = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.insqju button"));
        button.click();

        // Wait for the filters to load
        waitLoading();
        // Find all filter names that are in button content
        return chromeDriver.findElements(By.cssSelector(".sc-beqWaB.cTPvSS"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private  void waitLoading() {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete';"));
    }

    private List<String> scrapeLocationFilter() {
        WebElement button = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.ikEWRp button"));
        button.click();

        waitLoading();

        return chromeDriver.findElements(By.cssSelector(".sc-beqWaB.fzXBnb"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private List<String> scrapeIndustryFilter() {
        WebElement button = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.fLmMAc button"));
        button.click();

        waitLoading();

        return chromeDriver.findElements(By.cssSelector(".sc-beqWaB.cTPvSS"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private List<String> scrapeCompanySizeFilter() {
        WebElement button = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.chcvOD button"));
        button.click();

        waitLoading();

        return chromeDriver.findElements(By.cssSelector(".sc-beqWaB.cTPvSS"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private List<String> scrapeCompanyStageFilter() {
        WebElement button = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.xjuZq button"));
        button.click();

        waitLoading();

        return chromeDriver.findElements(By.cssSelector(".sc-beqWaB.cTPvSS"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private List<String> scrapeTopicsFilter() {
        WebElement button = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.uwbHl button"));
        button.click();

        waitLoading();

        return chromeDriver.findElements(By.cssSelector(".sc-beqWaB.cTPvSS"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private List<String> scrapeCompanyFilter() {
        WebElement button = chromeDriver.findElement(By.cssSelector(".sc-beqWaB.jkzAOU button"));
        button.click();

        waitLoading();

        return chromeDriver.findElements(By.cssSelector(".sc-beqWaB.cTPvSS"))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private void closeCookiesApproval() {
        // close cookies approval if button exists
        try {
            // Wait for the button to be clickable
            WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
            WebElement rejectCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#onetrust-close-btn-container button")));

            // Click the button
            rejectCookiesButton.click();
        } catch (TimeoutException e) {
            // The button was not found within the specified time
            System.out.println("Cookies approval button was not found or not clickable within the specified time.");
        }
    }
}
