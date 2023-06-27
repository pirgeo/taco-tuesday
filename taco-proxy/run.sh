#!/usr/bin/env bash

if [ ! -f "docker.env" ]; then
  echo "need to prepare env file 'docker.env'. Copy docker.template.env to docker.env and fill in the values:"
  echo "\tcp docker.template.env docker.env"
  exit 1
fi

docker build -t taco-proxy:latest .

docker run \
  --env-file=docker.env \
  taco-proxy:latest

