# 8 - Authentification mittels JWT

Date: 2025-05-26  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für die Kommunikation zwischen Frontend und Backend wird bei einigen Endpunkten ein angemeldeter Nutzer erwartet. Eine sichere und effiziente Methode zur Authentifizierung ist notwendig, die mit REST-API-Endpunkten kompatibel ist. Die Authentifizierung sollte sicherstellen, dass sensible Daten wie Passwörter nicht im Klartext übertragen werden.

## Decision

Die Authentifizierung wird mittels JSON Web Tokens (JWT) umgesetzt. Der Prozess ist wie folgt:

1. Anmeldung und Registrierung:
    - Der Nutzer gibt seinen Nutzernamen und sein Passwort ein.
    - Das Passwort wird im Frontend mit HMAC256 pseudonymisiert, bevor es an das Backend gesendet wird.

2. Token-Generierung:
    - Das Backend überprüft die Anmeldedaten und generiert ein JWT, wenn die Eingaben korrekt sind.
    - Das JWT wird an das Frontend zurückgegeben und für zukünftige Anfragen verwendet.

3. Token-Verwendung:
    - Authentifizierte Anfragen enthalten das JWT im Header (Authorization: Bearer \<token>).
    - Das Backend validiert das Token und gewährt Zugriff auf geschützte Ressourcen.

4. Schlüsselverwaltung:
    - Der Schlüssel zur Generierung von JWT wird als Umgebungsvariable in der Datei docker-compose.yml gespeichert.

## Alternatives

- Session-basierte Authentifizierung:  
Diese Methode wurde verworfen, da sie zusätzliche Serverressourcen für die Verwaltung von Sitzungen erfordert und weniger skalierbar ist.
- OAuth 2.0:  
Eine komplexere Lösung, die für die Anforderungen des Projekts als überdimensioniert angesehen wurde.

## Consequences

- Vorteile:
    - JWT ist leichtgewichtig und gut für REST-APIs geeignet.
    - Die Authentifizierung ist skalierbar und unabhängig vom Serverzustand.
    - Passwörter werden nicht im Klartext übertragen, was die Sicherheit erhöht.

- Nachteile:
    - Die aktuelle Implementierung der Schlüsselverwaltung ist unsicher, da der Schlüssel in der docker-compose.yml hinterlegt ist.
    - Es besteht ein Risiko, dass der Schlüssel kompromittiert wird, wenn er nicht sicher gespeichert wird.

- Risikominderung:
    - Der Schlüssel sollte in einem sicheren Geheimnis-Management-Tool (z. B. HashiCorp Vault oder AWS Secrets Manager) gespeichert werden.
    - Alternativ kann der Schlüssel als Umgebungsvariable in einem sicheren CI/CD-System hinterlegt werden.
