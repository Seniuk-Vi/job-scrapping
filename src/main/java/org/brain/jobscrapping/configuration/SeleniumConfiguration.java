package org.brain.jobscrapping.configuration;

import jakarta.annotation.PostConstruct;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfiguration {
    @Value("${webdriver.chrome.driver}")
    private String chromeDriverPath;

    @PostConstruct
    void postConstruct() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
    }

    @Bean
    public ChromeDriver getChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless"); // run in headless mode
        return new ChromeDriver(options);
    }
}
