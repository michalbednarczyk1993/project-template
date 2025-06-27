# Orders Service

## Budowanie i uruchamianie przez Docker

1. Zbuduj jar:
   ```sh
   ./mvnw clean package
   ```
2. Zbuduj obraz:
   ```sh
   docker build -t orders-service .
   ```
3. Uruchom serwis:
   ```sh
   docker run -p 8081:8081 orders-service
   ```

## Healthcheck

Endpoint zdrowia: http://localhost:8081/actuator/health 