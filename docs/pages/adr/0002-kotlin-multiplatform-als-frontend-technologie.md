# 2 - Kotlin Multiplatform als Frontend-Technologie

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für das Frontend wird eine Technologie benötigt, um die Applikation als Web-App auf jeglichen Browsern darstellen zu können. Die Technologie sollte plattformübergreifend sein und eine moderne, flexible Entwicklung ermöglichen.

## Decision

Die Entscheidung fiel auf **Kotlin Multiplatform**, da es eine plattformübergreifende Entwicklung ermöglicht und sich gut in bestehende Kotlin-Ökosysteme integriert. Der Entwickler hat bereits Erfahrung mit **Android Compose**, das ähnliche Konzepte verwendet, was die Einarbeitungszeit reduziert.

## Alternatives

- React: Eine etablierte Technologie mit einer großen Community, jedoch keine native Integration in das Kotlin-Ökosystem.
- Flutter: Eine weitere plattformübergreifende Technologie, die jedoch eine andere Programmiersprache (Dart) erfordert, was zusätzliche Einarbeitung bedeutet.

## Consequences

- Vorteile:
    - Wiederverwendbarkeit von Code zwischen verschiedenen Plattformen.
    - Gute Integration in das Kotlin-Ökosystem.
    - Reduzierte Einarbeitungszeit durch bestehende Erfahrung mit Android Compose.

- Nachteile:
    - Kotlin Multiplatform ist noch nicht so ausgereift wie React oder Flutter, was zu Kompatibilitätsproblemen mit gängigen Tools führen kann.
    - Begrenzte Community und Dokumentation im Vergleich zu etablierten Technologien.
