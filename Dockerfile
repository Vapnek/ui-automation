FROM maven:3.6.0-jdk-8

LABEL AUTHOR="Vitalijs Massans" DESCRIPTION="UI test automation solution"

# Create new directory called docker and use that as working directory
WORKDIR /docker

# Copy all git files in docker directory root
COPY src src
COPY .gitignore ./
COPY pom.xml ./
COPY send_notification.sh ./
COPY testNG.xml ./

# To resolve dependencies based on copied pom.xml
RUN mvn dependency:resolve

# To complete and package application
RUN mvn clean install -DskipTests