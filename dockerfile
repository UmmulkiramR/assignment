
#############################
#   Builder
#############################
FROM openjdk:21-jdk as builder
WORKDIR /usr/src/app
ADD . .
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw clean package -DskipTests

#############################
#   Server
#############################
FROM openjdk:21-jdk

ENV APP_HOME /srv


COPY --from=builder /usr/src/app/target/assignment-*.jar $APP_HOME/assignment.jar

WORKDIR $APP_HOME

RUN useradd app
USER app

CMD ["java", "-ea", "-jar", "/srv/assignment.jar"]
EXPOSE 8080/tcp
