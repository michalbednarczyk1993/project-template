#!/bin/bash

set -e

# Funkcja budująca serwisy w bash
build_bash() {
  for service in orders-service products-service users-service; do
    echo -e "\n\n=============================="
    echo "Buduję $service..."
    echo -e "==============================\n"
    (cd $service && ./mvnw clean package)
    echo -e "\n=============================="
    echo "Zakończono budowanie $service"
    echo -e "==============================\n\n"
  done
  echo "Budowanie wszystkich serwisów zakończone sukcesem!"
}

# Funkcja budująca serwisy w PowerShell (dla czystego Windows, jeśli chcesz)
build_powershell() {
  for service in orders-service products-service users-service
  do
    echo -e "\n\n=============================="
    echo "Buduję $service..."
    echo -e "==============================\n"
    powershell.exe -Command "cd $service; ./mvnw.cmd clean package"
    echo -e "\n=============================="
    echo "Zakończono budowanie $service"
    echo -e "==============================\n\n"
    cd ..
  done
  echo "Budowanie wszystkich serwisów zakończone sukcesem!"
}

# Wykrywanie systemu operacyjnego i środowiska
unameOut="$(uname -s 2>/dev/null || echo Unknown)"
case "${unameOut}" in
    Linux*|Darwin*|MINGW*|MSYS*|CYGWIN*)
        build_bash
        ;;
    *)
        echo "Wykryto czysty Windows. Próbuję uruchomić przez PowerShell."
        build_powershell
        ;;
esac

echo "\nNaciśnij dowolny klawisz, aby zakończyć..."
# Czekaj na naciśnięcie klawisza (działa w bash, Git Bash, WSL)
read -n 1 -s -r -p ""
