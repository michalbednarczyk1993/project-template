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

## Baza danych i migracje

Projekt korzysta z bazy danych PostgreSQL uruchamianej w kontenerze Docker. Każdy serwis posiada własną bazę danych (`orders_db`, `products_db`, `users_db`), które są tworzone automatycznie przy starcie kontenera Postgresa dzięki skryptowi `init-db.sh`.

Migracje Flyway są uruchamiane automatycznie przy starcie każdego serwisu Spring Boot. Oznacza to, że po uruchomieniu środowiska przez `docker-compose up --build`, każda aplikacja wykona swoje migracje (z folderu `src/main/resources/db/migration`) na odpowiedniej bazie danych.

### Jak to działa?
- Przy starcie kontenera Postgresa wykonywany jest skrypt `init-db.sh`, który tworzy bazy danych.
- Po uruchomieniu serwisów Flyway automatycznie wykonuje migracje SQL na odpowiedniej bazie.

Nie musisz wykonywać żadnych dodatkowych kroków, aby utworzyć bazy lub tabele testowe – wszystko dzieje się automatycznie.

## Budowanie wszystkich serwisów jednym poleceniem

W katalogu głównym projektu znajduje się skrypt `build-all.sh`, który automatycznie buduje wszystkie serwisy (`orders-service`, `products-service`, `users-service`).

### Jak użyć?

W systemach Linux, MacOS, WSL lub Git Bash na Windows:

```sh
  ./build-all.sh
```

Skrypt wykrywa środowisko i uruchamia odpowiednie polecenia. Po zakończeniu budowania każdego serwisu wyświetla czytelny podział w logach, a na końcu czeka na naciśnięcie klawisza przez użytkownika.

W czystym Windows (cmd/PowerShell) zalecane jest użycie PowerShell lub uruchomienie skryptu przez Git Bash.

## CI/CD
Projekt zawiera podstawową konfigurację GitHub Actions do automatyzacji procesu CI/CD. Workflow jest skonfigurowany do uruchamiania testów i budowania aplikacji przy każdym PR albo pushu do gałęzi `main` oraz `develop`.
### Testowanie lokalne z [ACT](https://nektosact.com/introduction.html)
Do lokalnego testowania workflowów GitHub Actions można użyć narzędzia act, które symuluje środowisko GitHub Actions na lokalnej maszynie przy użyciu Dockera.
Żeby przetestować workflow lokalnie, wykonaj następujące kroki:


🔧 Wymagania
Zainstalowany act: [User Manual](https://nektosact.com/installation/index.html)
Docker uruchomiony w tle + uruchomiony w nim kontener Postgresa z bazami danych bazujący na docker-compose

Aby uruchomić konkretny workflow, będąc w folderze głównym projektu użyj komendy:
```sh
  act -W .github/workflows/<nazwa-workflowu>.yml
```
To uruchomi workflow w lokalnym środowisku, symulując działanie GitHub Actions.
