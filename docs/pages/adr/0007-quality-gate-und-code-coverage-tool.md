# 7 - Quality Gate und Code Coverage-Tool

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für die Qualitätssicherung des Quellcodes werden Werkzeuge benötigt, die die Einhaltung von Qualitätsstandards und die Testabdeckung sicherstellen.

## Decision

- **Codacy** wird als Tool für die Code-Qualitätssicherung verwendet.
- **JaCoCo** wird für die Testabdeckung eingesetzt.

## Consequences

- Vorteile:
    - Codacy bietet eine einfache Integration mit GitHub und unterstützt Kotlin.
    - JaCoCo ist gut in Spring Boot integriert und liefert detaillierte Berichte.

- Nachteile:
    - Eine Migration zu anderen Tools (z. B. SonarQube) erfordert zusätzlichen Aufwand.
