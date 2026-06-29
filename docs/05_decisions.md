# Decision log

## 2026-06-29 — Składniki strukturalne zamiast tekstu

Decyzja:
Przechodzimy ze składników jako tekstu na listę RecipeIngredient.

Kontekst:
Skalowanie porcji i obliczanie kaloryczności wymaga ilości, jednostek i produktów.

Konsekwencje:
Formularz przepisu będzie bardziej złożony, ale umożliwi skalowanie i kalkulacje.

## 2026-06-29 — Moduł quantity jako osobna domena

Decyzja:
Konwersje jednostek będą w osobnym module quantity.

Kontekst:
Przeliczanie gramów, mililitrów, sztuk i opakowań jest osobnym problemem domenowym.

Konsekwencje:
UI i przepisy nie będą zawierały logiki konwersji.

## 2026-06-29 — Pricing jako archetyp kalkulacji żywieniowej

Decyzja:
Wzorujemy się na archetypie pricing, ale wykorzystujemy go do obliczania kalorii i wartości odżywczych.

Kontekst:
Kaloryczność jest wynikiem kalkulacji z komponentów przepisu.

Konsekwencje:
Nazwy wewnętrzne mogą być żywieniowe, ale struktura modułu ma zachować ideę kalkulatorów, komponentów, kontekstu i wyniku.

## 2026-06-29 — Produkty kanoniczne, warianty i edytowalne jednostki w bazie

Decyzja:
Produkt spożywczy ma warianty, a definicje jednostek domowych/opakowaniowych są rekordami danych.

Kontekst:
Jajka mają rozmiary S/M/L, szklanka nie zawsze ma 250 ml, a mleko 0.5% i 3.2% różni się gęstością oraz kalorycznością mimo że nadal jest mlekiem.

Konsekwencje:
Składnik przepisu może wskazać konkretny wariant produktu. Jeśli wariant nie jest wskazany, kalkulator używa wariantu domyślnego, który można zmienić bez zmiany produktu kanonicznego.
