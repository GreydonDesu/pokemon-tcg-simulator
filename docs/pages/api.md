# API-Dokumentation

Die API des Pokémon TCG Pack Simulators bietet verschiedene Endpunkte, die es ermöglichen, Benutzerkonten zu verwalten, Kartenpacks zu öffnen, Inventare abzurufen und verfügbare Sets zu durchsuchen.

## 1. Benutzerverwaltung

### 1.1 Registrierung eines neuen Benutzers

- **Endpunkt:** `POST /api/register`
- **Beschreibung:** Registriert einen neuen Benutzer mit einem Benutzernamen und einem Passwort.
- **Anfrageparameter:**
  - `username` (String, erforderlich): Der Benutzername des neuen Kontos.
  - `password` (String, erforderlich): Das Passwort des neuen Kontos (SHA-512-Hash).
- **Antwort:**
  - **Status 200:** Erfolgreiche Registrierung.

    ```json
    {
        "message": "Account registered successfully. Please login with the new credentials."
    }
    ```

  - **Status 400:** Fehlerhafte Anfrage (z. B. leere Felder oder bereits existierender Benutzername).

### 1.2 Anmeldung eines Benutzers

- **Endpunkt:** `POST /api/accounts/login`
- **Beschreibung:** Meldet einen Benutzer an und gibt ein JWT-Token zurück.
- **Anfrageparameter:**
  - `username` (String, erforderlich): Der Benutzername des neuen Kontos.
  - `password` (String, erforderlich): Das Passwort des neuen Kontos (SHA-512-Hash).
- **Antwort:**
  - **Status 200:** Erfolgreiche Anmeldung.

    ```json
    {
        "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```

  - **Status 400:** Fehlerhafte Anfrage (z. B. leere Felder).
  - **Status 401:** Ungültige Anmeldedaten.

### 1.3 Abrufen des Inventars

- **Endpunkt:** `GET /api/accounts/inventory`
- **Beschreibung:** Gibt das Inventar des aktuell authentifizierten Benutzers zurück.
- **Header:**
  - `Authorization` (String, erforderlich): Das JWT-Token im Format `Bearer <token>`.
- **Antwort:**
  - **Status 200:** Erfolgreiches Abrufen des Inventars.\
  Die Liste kann auch leer sein.

    ```json
    [
        {
            "id": "A1-046",
            "localId": "046",
            "name": "Moltres",
            "image": "http://localhost:8080/images/card_A1-046.png"
        },
        {
            "id": "A1-049",
            "localId": "049",
            "name": "Salandit",
            "image": "http://localhost:8080/images/card_A1-049.png"
        },
        {
            "id": "A1-169",
            "localId": "169",
            "name": "Nidoran♂",
            "image": "http://localhost:8080/images/card_A1-169.png"
        },
        {
            "id": "A1-246",
            "localId": "246",
            "name": "Meowth",
            "image": "http://localhost:8080/images/card_A1-246.png"
        },
        {
            "id": "A1-048",
            "localId": "048",
            "name": "Heatmor",
            "image": "http://localhost:8080/images/card_A1-048.png"
        }
    ]
    ```

  - **Status 401:** Ungültiges oder abgelaufenes Token.

## 2. Kartenpacks

### 2.1 Öffnen eines Kartenpacks

- **Endpunkt:** `POST /api/packs/open`
- **Beschreibung:** Öffnet ein Kartenpack und fügt die Karten dem Inventar des Benutzers hinzu.
- **Header:**
  - `Authorization` (String, erforderlich): Das JWT-Token im Format `Bearer <token>`.
- **Anfrageparameter:**
  - `setId` (String, erforderlich): Die ID des Kartenpacks, das geöffnet werden soll.
- **Antwort:**
  - **Status 200:** Erfolgreiches Öffnen des Packs.

    ```json
    [
        {
            "id": "A1-046",
            "localId": "046",
            "name": "Moltres",
            "image": "http://localhost:8080/images/card_A1-046.png"
        },
        {
            "id": "A1-049",
            "localId": "049",
            "name": "Salandit",
            "image": "http://localhost:8080/images/card_A1-049.png"
        },
        {
            "id": "A1-169",
            "localId": "169",
            "name": "Nidoran♂",
            "image": "http://localhost:8080/images/card_A1-169.png"
        },
        {
            "id": "A1-246",
            "localId": "246",
            "name": "Meowth",
            "image": "http://localhost:8080/images/card_A1-246.png"
        },
        {
            "id": "A1-048",
            "localId": "048",
            "name": "Heatmor",
            "image": "http://localhost:8080/images/card_A1-048.png"
        }
    ]
    ```

  - **Status 400:** Ungültige Anfrage (z. B. ungültige setId).
  - **Status 401:** Ungültiges oder abgelaufenes Token.

## 3. Sets

### 3.1 Abrufen aller verfügbaren Sets

- **Endpunkt:** `GET /api/sets`
- **Beschreibung:** Gibt eine Liste aller verfügbaren Sets zurück.
- **Antwort:**
  - **Status 200:** Erfolgreiches Abrufen der Sets.

    ```json
    [
        {
            "id": "A1",
            "name": "Genetic Apex",
            "logo": "http://localhost:8080/images/logo_A1.png",
            "symbol": "http://localhost:8080/images/symbol_A1.png",
            "totalCards": 286,
            "releaseDate": "2024-10-30",
            "cards": [
            {
                "id": "A1-001",
                "localId": "001",
                "name": "Bulbasaur",
                "image": "http://localhost:8080/images/card_A1-001.png"
            },
            {
                "id": "A1-002",
                "localId": "002",
                "name": "Ivysaur",
                "image": "http://localhost:8080/images/card_A1-002.png"
            },
            ...
            ]
        },
        ...
    ]
    ```

## 4. Bilder

### 4.1 Abrufen eines Bildes

- **Endpunkt:** `GET /images/{fileName}`
- **Beschreibung:** Gibt ein Bild (z. B. Kartenbild, Logo oder Symbol) zurück.
- **Pfadparameter:**
  - `fileName` (String, erforderlich): Der Name der Bilddatei (z. B. card1.png).
- **Antwort:**
  - **Status 200:** Erfolgreiches Abrufen des Bildes (im Header wird der Content-Type gesetzt, z. B. image/png).
- **Status 404:** Bild nicht gefunden.
