# Installation

## Voraussetzungen

- Windows, macOS oder Linux
- Docker & Docker Compose
- Für lokale Installation
    - Java 21
    - Node.js (für das Frontend)
    - MongoDB (für die Datenbank)

## Installation mit Docker & Docker Compose

1. **Klonen Sie das Repository**

    ```sh
    git clone https://github.com/GreydonDesu/pokemon-tcg-simulator.git
    ```

2. **Navigieren Sie in das Projektverzeichnis**

    ```sh
    cd pokemon-tcg-simulator
    ```

    > Sollen Sie die Anwendung lokal bauen wollen, springen Sie zu "Lokale Installation"

3. **Starten Sie die Anwendung**

    ```sh
    docker-compose up --build
    ```

    Die Anwendung samt Benutzeroberfläche (Frontend), Anwendungslogik (Backend) und Datenbank wird durch diesen Befehl aufgebaut. Der Prozess kann etwa 10 Minuten dauern.

4. **Anwendung testen**

    Öffnen Sie `http://localhost:3000/` in Ihrem Browser, um die Anwendung zu nutzen.

## Lokale Installation

Falls Sie die Anwendung lokal ohne Docker ausführen möchten, folgen Sie diesen Schritten:

1. **Klonen Sie das Repository**

    ```sh
    git clone https://github.com/GreydonDesu/pokemon-tcg-simulator.git
    ```

2. **Navigieren Sie in das Projektverzeichnis**

    ```sh
    cd pokemon-tcg-simulator
    ```

3. **Frontend starten**

    Navigieren Sie in das Verzeichnis `application/` und führen Sie den folgenden Befehl aus:

    ```sh
    ./gradlew wasmJsBrowserDevelopmentRun -t --quiet
    ```

    Das Frontend wird unter `http://localhost:8080/` verfügbar sein.

4. **Backend starten**

    Navigieren Sie in das Verzeichnis `backend/` und führen Sie die folgenden Befehle aus:

    ```sh
    ./gradlew build
    ./gradlew bootRun
    ```

    Das Backend wird unter `http://localhost:8081/` verfügbar sein.

5. **MongoDB starten**

    Stellen Sie sicher, dass eine MongoDB-Instanz läuft. Standardmäßig erwartet das Backend, dass MongoDB unter `mongodb://localhost:27017` verfügbar ist.

    Falls Sie Docker verwenden, können Sie MongoDB mit folgendem Befehl starten:

    ```sh
    docker run -d -p 27017:27017 --name mongodb mongo
    ```

6. **Anwendung testen**

    Öffnen Sie `http://localhost:8080/` in Ihrem Browser, um die Anwendung zu nutzen.
