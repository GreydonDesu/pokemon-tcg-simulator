# 4 - TCGdex als externe API

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Das Projekt benötigt eine externe API, um Informationen über Karten und Kartenpacks bereitzustellen. Diese Daten sind essenziell für die Simulation des Kartenpack-Öffnens.

## Decision

Die Entscheidung fiel auf die Verwendung von **TCGdex** als externe API. Diese API bietet die notwendigen Informationen über Karten und Kartenpacks und ist Open Source.

## Alternatives

- **Pokémon TCG Developers**: Eine alternative API, die jedoch mit einem Entwicklerkonto zugänglich ist und nicht alle benötigten Daten bereitstellt.

## Consequences

- Vorteile:
    - TCGdex bietet eine umfassende Datenbasis für Karten und Kartenpacks.
    - Die API ist Open Source und kostenlos nutzbar.

- Nachteile:
    - Begrenzte öffentliche Dokumentation.
    - Unklarheit über DDoS-Schutz, was bei hoher Anfragefrequenz zu Problemen führen könnte.
    - Abhängigkeit von der Verfügbarkeit der API.

- Risikominderung:
    - Implementierung eines Caching-Mechanismus, um die Anzahl der API-Anfragen zu reduzieren.
    - Fallback-Mechanismen für den Fall, dass die API nicht verfügbar ist.
