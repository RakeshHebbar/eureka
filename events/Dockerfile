FROM openjdk:8-jre-alpine
ENV JDBC_URL='jdbc:postgresql://postgres:5432/eureka'
ENV DB_USERNAME='eureka'
ENV DB_PASSWORD='eureka'
ENV GEO_API='http://geolocation:8085'
COPY target/*.jar /events.jar
CMD java -jar /events.jar --geo.api=$GEO_API \
    --spring.datasource.url=$JDBC_URL \
    --spring.datasource.username=$DB_USERNAME \
    --spring.datasource.password=$DB_PASSWORD \