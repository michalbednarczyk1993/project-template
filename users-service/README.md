# Users Service

## Budowanie i uruchamianie przez Docker

1. Zbuduj jar:
   ```sh
   ./mvnw clean package
   ```
2. Zbuduj obraz:
   ```sh
   docker build -t users-service .
   ```
3. Uruchom serwis:
   ```sh
   docker run -p 8083:8083 users-service
   ```

## Healthcheck

Endpoint zdrowia: http://localhost:8083/actuator/health 