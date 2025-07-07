1. Inicjalizacja projektu
   - Utworzenie struktury katalogów
   - Dodanie pliku `pom.xml` dla parent-pom
   - Konfiguracja podstawowych zależności

2. Dokeryzacja aplikacji
    - Dodanie pliku `Dockerfile` do każdego serwisu
    - Konfiguracja `docker-compose.yml` dla uruchamiania całego środowiska

3. Dodanie prostego github workflow
   - Konfiguracja podstawowych kroków CI (budowanie, testowanie)

4. Dodanie postgresa jako bazy danych dla każdego serwisu
   - Konfiguracja połączenia z bazą danych w każdym serwisie (każdy z nich ma swoją bazę, ale na jednym kontenerze)
   - Podłączenie flyway do każdego serwisu
   - Dodanie podstawowej struktury tabel w bazach danych

5. Dodanie skryptu budującego wszystkie serwisy za jednym razem - build-all.sh

6. Naprawa github workflow
   - Lokalne testy z narzędziem ACT
   - Naprawa skryptów i testy na środowisku githuba

7. Dodanie Kafki
   - Konfiguracja Kafki jako systemu kolejkowania wiadomości
[//]: # (   - Implementacja producenta i konsumenta wiadomości)
[//]: # (   - Dodanie topiców do Kafki)
[//]: # (   - Konfiguracja kompresji wiadomości)
   