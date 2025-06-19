# 3 - Spring Boot und mongoDB als Backend-Technologie

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für das Backend wird eine Technologie gebraucht, um die Logik, Verwaltung und Persistenz der Applikation zu bewerkstelligen.

## Decision

Die Entscheidung fiel dabei auf die Kombination Spring Boot als Logik mit einer MongoDB Datenbank für Persistenz. Ich habe bereits durch vorherige Vorlesungen Erfahrung mit Spring Boot gehabt. Spring Boot bietet auch durch ihren [Initializer](https://start.spring.io/) ein Tool zum Initialisieren von Projekten samt optionaler Erweiterungen, welche für das Projekt notwendig sind. Die Programmiersprache ist Kotlin und die Kommunikation mit dem Frontend werden mit REST-Abfragen bewerkstelligt.

Als Datenbank habe ich mich für MongoDB entschieden, da ich mich mit einer Datenbank beschäftigen will, welche auch in Zukunft in jeglichen Umgebungen verwendet wird. Ebenso erleichtert es die Persistenz, da es nicht den üblichen Prozess mit SQL-Abfragen verfolgt.

## Consequences

Ich habe mich für die Programmiersprache Kotlin für Spring Boot entschieden, da ich subjektiv die Programmiersprache über Java mag. Leserlich und für die generelle Entwicklung präferiere ich Kotlin. Ein Problem kann bei der Suche von Dokumentation sein, da andere Entwickler ihre Spring Boot Projekte meistens mit Java schreiben und ich die Lösungen von Java zu Kotlin umschreiben muss. Es ist nicht viel Arbeit, aber es erfordert etwas umdenken.
