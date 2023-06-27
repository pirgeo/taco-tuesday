#!/usr/bin/env bash

../gradlew :orders:clean :orders:bootJar

java \
  -javaagent:../opentelemetry-javaagent.jar \
  -Dotel.javaagent.configuration-file=../otel-sdk-configuration.properties \
  -jar build/libs/orders-0.0.4.jar