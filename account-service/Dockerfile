#Start with a base image containing Java runtime
FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu

# MAINTAINER instruction is deprecated in favor of using label
# MAINTAINER jkbank.com
#Information around who maintains the image
LABEL "org.opencontainers.image.authors"="jkbank.com"

# Add the application's jar from the target folder to the docker image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]