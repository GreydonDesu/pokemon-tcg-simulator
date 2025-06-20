# 8 - Authentification mittels JWT

Date: 2025-05-26  
Author: [Raymond Hoang](mailto:grey@greydon.de)

## Status

Accepted

## Context

Für die Kommunikation zwischen Frontend und Backend werden bei einigen Endpunkten ein angemeldeter Nutzer erwartet. Eine Weise für die Authentifizierung ist dafür notwendig, welche auch mit Nutzung von API-Endpunkten kompatibel ist.

## Decision

Die Anmeldung und Registrierung des Nutzers wird mit Nutzername und Passwort bewerkstelligt. Wichtig ist dabei, dass das Passwort nicht als Klartext über das Netzwerk verschickt wird. Das Passwort wird im Frontend mit HMAC256 pseudonymisiert.

Das Backend erhält den Nutzername und das pseudonymisierte Passwort und gibt ein JSON Web Token an das Frontend zurück, sofern die Eingaben korrekt sind. Alle authentifierbare Anfragen werden mit diesem Token abgefertigt. Der Schlüssel zur Generierung von JWT wird als Umgebungsvariable gespeichert (Dies ist in der Datei docker-compose.yml hinterlegt).

## Consequences

Die aktuelle Implementierung des Schlüssels zur Generierung von JWT ist sehr unsicher und soll zum nächstebesten Moment angepasst werden. Gegebenenfalls bietet es sich da an, den Schlüssel als Geheimnachricht in das Repository hinzuzufügen und Docker Compose bezieht sich die notwendigen Informationen von dem Repository.
