# 10. Konzept für Integrationstests

Date: 2025-06-19  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für Integrationstests wird ein Testkonzept benötigt, um die Verbindung zwischen Backend (Spring Boot) und Datenbank (MongoDB) zu verifizieren.

## Decision

Das Konzept besteht darin, eine MongoDB Dockerinstanz aufzumachen und den Befehl `gradle test` des Backends durchgeführt. Der Test führt automatisch Unit- und Integrationstests und bricht ab, sofern einer der Tests scheitert. Diese Tests werden auch bei Docker Compose und in der Pipeline durchgeführt.

## Consequences

Der Entwickler muss lokal eine Dockerinstanz einschalten, um die Integrationstests durchführen zu können. Dies ist umständlich und teilweise ressourcenintensiv für die Entwicklungsmaschine. Eine Alternative ist eine eingebettete MongoDB für die Tests einpflegen. Dies ist aber nicht im Rahmen des Projektes geschafft worden.
