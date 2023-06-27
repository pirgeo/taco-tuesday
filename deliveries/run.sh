#!/usr/bin/env bash

../gradlew :deliveries:clean :deliveries:bootJar

java \
  -javaagent:../opentelemetry-javaagent.jar \
  -Dotel.javaagent.configuration-file=../otel-sdk-configuration.properties \
  -jar build/libs/deliveries-0.0.4.jar