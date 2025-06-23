# 9 - Neue Technologie für das Frontend

Date: 2025-06-10  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

Supersedes [2 - Kotlin Multiplatform als Frontend-Technologie
](0002-kotlin-multiplatform-als-frontend-technologie.md)

## Context

Für End-to-End-Tests (E2E-Tests) wird ein Werkzeug benötigt, das die Web-App anhand von HTML-Tags durchsucht, mit diesen interagiert und Anwendungsfälle testet. Die aktuelle Frontend-Technologie **Kotlin Multiplatform** hat jedoch den Nachteil, dass die gesamte Benutzeroberfläche als ein einziges Canvas gerendert wird. Dadurch ist die Suche nach HTML-Tags nicht möglich, was die Nutzung von Tools wie **Playwright** oder **Selenium** erheblich einschränkt.

## Decision

Da E2E-Tests mit Kotlin Multiplatform nur sehr umständlich möglich sind, wurden zwei mögliche Ansätze identifiziert:

1. Testkonzept anpassen:
    - Ein alternatives Testkonzept wird entwickelt, das trotz der Einschränkungen E2E-Tests ermöglicht.
    - Dies könnte z. B. die Simulation von Mauspositionen oder die direkte Interaktion mit der Canvas-API umfassen.

2. Technologie wechseln:
    - Die Frontend-Technologie wird auf eine Lösung umgestellt, die E2E-Tests besser unterstützt (z. B. React oder Angular).
    - Dies würde eine vollständige Neuentwicklung des Frontends erfordern.

## Alternatives

- Beibehaltung von Kotlin Multiplatform ohne E2E-Tests:  
Diese Option wurde verworfen, da E2E-Tests für die Qualitätssicherung des Projekts als essenziell angesehen werden.
- Manuelle Tests:  
Diese Option wurde ebenfalls verworfen, da sie zeitaufwendig und fehleranfällig ist.

## Consequences

- Vorteile:
    - Ein Wechsel der Technologie würde die Nutzung moderner E2E-Test-Tools wie Playwright ermöglichen.
    - Alternativ könnte ein angepasstes Testkonzept die Einschränkungen von Kotlin Multiplatform umgehen.

- Nachteile:
    - Ein Wechsel der Technologie erfordert einen erheblichen Umbau des Frontends, was zeit- und ressourcenintensiv ist.
    - Die Entwicklung eines alternativen Testkonzepts könnte ebenfalls zeitaufwendig sein und möglicherweise nicht alle Testfälle abdecken.

- Risikominderung:
    - Eine gründliche Analyse der Anforderungen an E2E-Tests sollte durchgeführt werden, bevor eine endgültige Entscheidung getroffen wird.
    - Falls ein Technologiewechsel notwendig ist, sollte ein schrittweiser Übergang geplant werden, um die Auswirkungen auf das Projekt zu minimieren.
