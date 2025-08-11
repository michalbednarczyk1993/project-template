#!/bin/bash

docker exec kafka \
  kafka-topics --bootstrap-server localhost:9092 \
  --create --if-not-exists \
  --topic product.created \
  --partitions 1 \
  --replication-factor 1 \
  --config cleanup.policy=delete \
  --config retention.ms=604800000
