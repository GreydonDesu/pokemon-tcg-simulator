# 2 - Kotlin Multiplatform als Frontend-Technologie

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für das Frontend wird eine Technologie gebraucht, um die Applikation als Web-App auf jeglichen Browsern darstellen zu können.

## Decision

Die Entscheidung ist auf Kotlin Multiplatform gefallen, da ich als Entwickler bereits viel Erfahrung mit der Technologie Android Compose, was für die App-Entwicklung in Android bereits etabliert ist, habe. Kotlin Multiplatform ist programmatisch genau so funktional wie Android Compose und ich fühle mich in der Technologie wohl und würde darin auch mein Frontend bauen wollen.

## Consequences

Diese Technologie ist von Google als Antwort zu anderen Cross-Platform-Technologien wie React Native und Flutter entstanden worden. Ziel von Google war eine konkurrenzfähige Technologie auf den Markt zu bringen und damit Marktanteile schaffen. Ein Nachteil davon ist beispielsweise die Reifegrad. Dadurch, dass die Technologie nicht vollständig durchdacht ist und quasi eine angepasste Version vom etablierten Android Compose ist, kann es zu Kompatibilitätsschwierigkeiten mit gängigen Tools, die auch für die sonstige Software-Entwicklung verwendet wird, geben.
