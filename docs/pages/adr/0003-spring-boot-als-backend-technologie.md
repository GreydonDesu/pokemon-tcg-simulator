# 3 - Spring Boot und MongoDB als Backend-Technologie

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für das Backend wird eine Technologie benötigt, um die Logik, Verwaltung und Persistenz der Applikation zu bewerkstelligen. Die Lösung sollte robust, flexibel und zukunftssicher sein.

## Decision

Die Entscheidung fiel auf die Kombination aus **Spring Boot** für die Backend-Logik und **MongoDB** als Datenbank. Spring Boot bietet eine schnelle und effiziente Entwicklung von REST-APIs, während MongoDB eine flexible Datenstruktur für die Persistenz bereitstellt.

## Alternatives

(nicht betrachtet)

## Consequences

- Vorteile:
    - Spring Boot bietet eine robuste Grundlage für die Entwicklung von REST-APIs.
    - MongoDB ermöglicht eine flexible und schemalose Datenmodellierung.
    - Gute Integration von Spring Boot und MongoDB durch bestehende Bibliotheken.

- Nachteile:
    - Die meisten Spring Boot-Projekte verwenden Java, was bedeutet, dass Kotlin-spezifische Dokumentation begrenzt ist.
    - MongoDB erfordert ein Umdenken im Vergleich zu relationalen Datenbanken, was für Entwickler mit SQL-Erfahrung eine Herausforderung sein kann.
