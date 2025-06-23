# 5 - API-Endpunkte zwischen Frontend und Backend

Date: 2025-04-12  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für die Kommunikation zwischen Frontend und Backend werden REST-API-Endpunkte benötigt, die die definierten Funktionen des Systems unterstützen.

## Decision

Die folgenden Endpunkte werden implementiert:

- Laden von Kartenpaketen aus der externen API.
- Öffnen von Kartenpaketen.
- Registrieren eines neuen Nutzers.
- Anmelden eines bestehenden Nutzers.
- Laden des Inventars des angemeldeten Nutzers.

## Alternatives

- **GraphQL**: Eine Alternative zu REST, die jedoch für die Anforderungen des Projekts als überdimensioniert angesehen wurde.
- **gRPC**: Eine weitere Alternative, welche die Kommunikation mittels Protobuf bewerkstellungt.

## Consequences

- Vorteile:
    - REST ist ein bewährtes und weit verbreitetes Protokoll.
    - Die Endpunkte sind flexibel und können bei Bedarf erweitert werden.

- Nachteile:
    - Änderungen an den Endpunkten erfordern eine Anpassung des Frontends.
    - Eine klare Dokumentation der API ist notwendig, um Missverständnisse zu vermeiden.
