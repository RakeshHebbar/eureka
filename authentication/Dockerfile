FROM openjdk:8-jre-alpine

ENV JDBC_URL='jdbc:postgresql://postgres:5432/eureka'
ENV DB_USERNAME='eureka'
ENV DB_PASSWORD='eureka'
ENV GEO_HOST='http://geolocation:8085'
ENV EVENTS_HOST='http://events:8086'
ENV EVENTS_SUGGESTION_HOST='http://event-suggestion:8087'

COPY target/*.jar /authentication.jar

CMD java -jar /authentication.jar \
    --spring.datasource.url=$JDBC_URL \
    --spring.datasource.username=$DB_USERNAME \
    --spring.datasource.password=$DB_PASSWORD \
    --zuul.routes.geo.url=$GEO_HOST \
    --zuul.routes.events.url=$EVENTS_HOST \
    --zuul.routes.event-suggestion.url=$EVENTS_SUGGESTION_HOST