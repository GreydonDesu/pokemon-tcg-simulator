# 6 - Datentypen und Attribute für die Datenbank

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Das Projekt benötigt eine klare Definition der Datentypen und deren Attribute, um die Persistenz und die API-Kommunikation zu standardisieren.

## Decision

Die folgenden Datentypen werden definiert:

- Nutzer
    - Nutzername
    - Password
    - Inventar
- Kartenset
    - ID
    - Name
    - Metadaten (Anzahl Karten, Erscheinungsdatum, Bilder, ...)
    - Liste an Karten
- Karte
    - ID
    - Name
    - Bild

## Consequences

- Vorteile:
    - Klare Strukturierung der Daten erleichtert die Implementierung und Wartung.
    - Die Datentypen sind an die externe API (TCGdex) angepasst.

- Nachteile:
    - Änderungen an den Datentypen erfordern Anpassungen im Frontend und Backend.
