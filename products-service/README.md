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