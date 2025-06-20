# 6 - Datentypen und Attribute für die Datenbank

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für das Projekt werden Datentypen und deren Attribute definiert. Diese Datentypen werden dann auch in der Datenbank so abgespeichert. Alle Funktionen und Endpunkte sollen entsprechend der vorliegenden Datentypen konstruiert werden.

## Decision

Das Projekt braucht zwei Datentypen:

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

Der Datentyp des Kartenset und der Karte orientiert sich an der Implementierung der von der externen API zur Verfügung gestellte Datentyp. Das Projekt kürzt dabei bewusst einige Attribute raus, die für das Projekt nicht notwendig sind.

## Consequences

Das Projekt ist sehr abhängig von den definierten Datentypen und bei Änderungen von Datentypen wird das Anpassen von Frontend und Backend notwendig.
