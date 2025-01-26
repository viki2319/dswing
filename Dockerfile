FROM openjdk:17-jdk

COPY build/libs/pgsql-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT ["java","-jar", "pgsql-0.0.1-SNAPSHOT.jar"]