# 5 - API-Endpunkte zwischen Frontend und Backend

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für die Kommunikation zwischen Frontend und Backend über REST API-Aufrufe müssen im Vorhinein Endpunkte bestimmt werden.

## Decision

Folgende Endpunkte werden für die Kommunikation gebraucht:

- Laden von Kartenpaketen aus der externen API
- Öffnen von Kartenpaketen
- Registrieren eines neuen Nutzers
- Anmelden eines bestehenden Nutzers
- Laden des Inventars des angemeldeten Nutzers

## Consequences

Durch die Eigenschaft von REST API, dass die Endpunkte erweitert und verändert werden können auf der Seite des Backends ist eine vernünftige Dokumentation notwendig, damit das Frontend die erwarteten Anforderderungen zum Backend und Ergebnisse vom Backend auch verarbeiten kann. Ebenso wird eine Migrationszeit gebraucht, sofern sich die API grundlegend verändert (beispielsweise bei Major Version Bumps).
