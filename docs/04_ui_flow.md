# Przepływ UI

Formularz przepisu powinien zbierać bazową liczbę porcji oraz listę składników strukturalnych: produkt, ilość, jednostkę i notatkę. Szczegóły przepisu pokazują selector porcji, przeskalowaną listę składników i sekcję orientacyjnej kaloryczności.

UI nie wykonuje konwersji jednostek. Ekrany powinny korzystać z interfejsów `QuantityConverter`, `ProductCatalog` i `NutritionCalculator`.
