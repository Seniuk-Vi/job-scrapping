## Run locally

- make sure to install chrome-driver into your environment
- create database with name `jobs-scrapping`
- open cmd in project root directory
- provide env variables as
    - DB_HOST=your db host (default: localhost)
    - DB_USER=your db user (default: postgres)
    - DB_PASSWORD=your db password (default: postgres)
    - CHROME_DRIVER_PATH=full path to chrome driver (default: /usr/bin/chromedriver)
- run `mvn clean install`
- open swagger ui by url http://localhost:8080/swagger-ui/index.html

## Run with Docker

- run docker app
- open cmd in project root directory
- run `mvn clean install`
- *if want to edit 'docker-compose.yml' file, specify next env variables:
    - replace 'DB_HOST' with your db host
    - replace 'DB_USER' with your db user
    - replace 'DB_PASSWORD' with your db password
    - replace 'CHROME_DRIVER_PATH' with your path to chrome driver
- run `docker compose up`
- open pgAdmin and create server with database with name `jobs-scrapping`
- start application again
- open swagger ui by url http://localhost:8080/swagger-ui/index.html

