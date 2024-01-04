# User story
As a user, I want to have an application that can scrape job listings from jobs.techstars.com based on specific job functions. The application should allow me to input the desired work functions or job categories I am interested in, and then it should automatically crawl the website to gather relevant job postings for those functions. The scraped data should be presented in a user-friendly format, showing key details. Additionally, I would like the application to have an option to filter or sort the results based on different criteria, such as job location or posting date. This will help me efficiently find and explore job opportunities tailored to my preferences, ultimately simplifying the job search process and improving my overall experience on the jobs.techstars.com platform.


## Server port: 8080

## Requirements

- Java version 17.0.7
- Spring Boot 3.1.3
- Maven 3.9.1
- Docker*
- PostgreSQL
- ChromeDriver


## Technologies Used

1. Java 17
2. Spring Boot

## Tasks
- [x] Input desired work functions or job categories, job location
- [x] Scrape jobs by filters (only first page)
- [x] Sort by posting date
- [x] Scrapping results dump to SQL file, with db creation schema or in CSV format 

