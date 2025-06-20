# 7 - Quality Gate und Code Coverage-Tool

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für eine verlässliche Qualitätssicherung des Quellcodes werden Werkzeuge für das Bestimmen von Qualitätszielen und der Abdeckung von Tests benötigt.

## Decision

Die Entscheidung ist auf [Codacy](https://www.codacy.com/) gefallen, da es für öffentlich zugängliche Projekte, was dieses Projekt ist, ein kostenfreies Paket anbietet. Codacy unterstützt alle Linting und Code Quality Werkzeuge für die bekannten Programmiersprachen und bietet eine Integration mit GitHub an.

Für die Abdeckung der Testfälle wird JaCoCo verwendet. Dies bietet eine Integration mit Spring Boot an und kann den Abdeckungsbericht in Form von XML exportieren, was für die Darstellung des Reports auf Codacy benötigt wird.

## Consequences

Soll ein anderes Tool für diese Zwecke wie SonarQube verwendet werden, wird eine Migration notwendig sein. Die Änderungen können dabei etwa einen Tag dauern.
