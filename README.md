# Project Template

Szablon projektu mikroserwisowego opartego o Spring Boot 3, Java 21 oraz Maven. Projekt zawiera trzy przykładowe serwisy: users-service, products-service oraz orders-service. Każdy serwis jest osobnym modułem, dziedziczącym po wspólnym parent-pom.

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

## Wymagania
- Java 21
- Maven 3.9+

## Struktura
- `users-service` – serwis użytkowników
- `products-service` – serwis produktów
- `orders-service` – serwis zamówień
