FROM openjdk:17
LABEL maintainer="sijo.saji"
ADD target/qp-0.0.1-SNAPSHOT.jar qp-assessment.jar
ENTRYPOINT ["java", "-jar", "qp-assessment.jar"]