
FROM registry.access.redhat.com/ubi8/openjdk-17:1.14

COPY --chown=185 target/*.jar web-api.jar

EXPOSE 8080
USER 185

ENTRYPOINT ["java","-jar","web-api.jar"]

