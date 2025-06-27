# Project Template

Szablon projektu mikroserwisowego opartego o Spring Boot 3, Java 21 oraz Maven. Projekt zawiera trzy przykładowe serwisy: users-service, products-service oraz orders-service. Każdy serwis jest osobnym modułem, dziedziczącym po wspólnym parent-pom.

## Struktura
- `users-service` – serwis użytkowników
- `products-service` – serwis produktów
- `orders-service` – serwis zamówień

## Wymagania
- Java 21
- Maven 3.9+

## Instalacja

1. Zainstaluj parent-pom (w katalogu głównym):
   ```sh
   mvn install -N
   ```
2. Zbuduj wszystkie moduły:
   ```sh
   mvn install
   ```
3. Uruchom wybrany serwis, np. users-service:
   ```sh
   cd users-service
   ./mvnw spring-boot:run
   ```

## Uruchamianie przez Docker

Każdy serwis można zbudować i uruchomić osobno:

```sh
cd users-service
./mvnw clean package
# Budowa obrazu
docker build -t users-service .
# Uruchomienie
docker run -p 8083:8083 users-service
```
Analogicznie dla pozostałych serwisów (products-service, orders-service).

## Uruchamianie całego środowiska przez docker-compose

W katalogu głównym:

```sh
docker-compose up --build
```

Serwisy będą dostępne na portach:
- users-service: http://localhost:8083
- products-service: http://localhost:8082
- orders-service: http://localhost:8081

Aby zatrzymać środowisko:
```sh
docker-compose down
```
