# Products Service

## Budowanie i uruchamianie przez Docker

1. Zbuduj jar:
   ```sh
   ./mvnw clean package
   ```
2. Zbuduj obraz:
   ```sh
   docker build -t products-service .
   ```
3. Uruchom serwis:
   ```sh
   docker run -p 8082:8082 products-service
   ```

## Healthcheck

Endpoint zdrowia: http://localhost:8082/actuator/health 

## Kafka & AKHQ

W środowisku developerskim dostępny jest broker Kafka oraz panel AKHQ (http://localhost:8090) do podglądu i zarządzania tematami. 