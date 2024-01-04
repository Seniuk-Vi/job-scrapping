FROM maven:3.9.6-eclipse-temurin-17
LABEL authors="Vitalii Seniuk"

ARG JAR_FILE=target/*.jar
ARG DB_HOST
ARG DB_USER
ARG DB_PASSWORD
ARG CHROME_DRIVER_PATH
# Install tools.
#RUN apt update -y & apt install -y wget unzip
#RUN apt-get install -y tzdata


# Google Chrome


ARG CHROME_VERSION=120.0.6099.199-1
RUN apt-get update -qqy \
	&& apt-get -qqy install gpg unzip \
	&& wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
	&& echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
	&& apt-get update -qqy \
	&& apt-get -qqy install google-chrome-stable=$CHROME_VERSION \
	&& rm /etc/apt/sources.list.d/google-chrome.list \
	&& rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
	&& sed -i 's/"$HERE\/chrome"/"$HERE\/chrome" --no-sandbox/g' /opt/google/chrome/google-chrome

# ChromeDriver

ARG CHROME_DRIVER_VERSION=120.0.6099.109
RUN wget -q -O /tmp/chromedriver.zip https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$CHROME_DRIVER_VERSION/linux64/chromedriver-linux64.zip \
	&& unzip /tmp/chromedriver.zip -d /opt \
	&& rm /tmp/chromedriver.zip \
	&& ln -s /opt/chromedriver-linux64/chromedriver /usr/bin/chromedriver

# Set environment variables from build-time arguments
ENV CHROME_DRIVER_PATH=CHROME_DRIVER_PATH \
    DB_HOST=$DB_HOST \
    DB_USER=$DB_USER \
    DB_PASSWORD=$DB_PASSWORD

#Copy source code and pom file.
COPY ${JAR_FILE} job-scrapping.jar


ENTRYPOINT ["java", "-jar", "/job-scrapping.jar"]