# 9 - Neue Technologie für das Frontend

Date: 2025-06-10  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

Supersedes [2 - Kotlin Multiplatform als Frontend-Technologie
](0002-kotlin-multiplatform-als-frontend-technologie.md)

## Context

Für E2E-Tests wird oft eine Testweise bestimmt, welche die Webapp mittels HTML-Tags sucht, damit interagiert, und somit Anwendungsfälle testet und simuliert.

## Decision

Ein Werkzeug für E2E-Tests ist Playwright, welches anhand der HTML-Tags diese Tests ausführt. Leider hat die Technologie Kotlin Multiplatform den Nachteil, dass das gesamte Frontend als ein einziges Canvas dargestellt wird und die Suche nach HTML-Tags nicht möglich ist. Tools wie Playwright können nur sehr umständlich benutzt werden (beispielsweise durch das Bestimmen von Mauspositionen in der UI)

Wenn E2E-Tests notwendig sind, dann ist Kotlin Multiplatform nicht geeignet. Entweder muss:

- Ein anderes Testkonzept erstellt werden, um trotz der Einschränkungen E2E-Tests zu bewerkstelligen
- Die Technologie komplett ändern, welches Tools für E2E-Tests unterstützt.

## Consequences

Abhängig von der Entscheidung, kann es einen erheblichen Umbau des Frontends verlangen. Auch der Entwurf eines Testkonzeptes kann zeitaufwendig sein.
