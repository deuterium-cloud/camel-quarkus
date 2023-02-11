#!/bin/bash

mvn -f rabbitmq-connector/pom.xml clean &&
mvn -f rabbitmq-connector/pom.xml package -Dquarkus.package.type=uber-jar &&

mvn -f websocket-connector/pom.xml clean &&
mvn -f websocket-connector/pom.xml package -Dquarkus.package.type=uber-jar &&

mvn -f web-api/pom.xml clean &&
mvn -f web-api/pom.xml package -Dquarkus.package.type=uber-jar
