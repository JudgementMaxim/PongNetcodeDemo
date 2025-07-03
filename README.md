# Pong TCP Game Server (Kotlin)

**Präsentation:** Spiele-Netzwerkarchitektur unter Linux  
**Modul:** Betriebssysteme und Verteilte Systeme (2. Semester)  
**Komponente:** Server (Kotlin TCP)

---

## 🧠 Projektbeschreibung

Diese Komponente bildet den Server für ein Pong-Spiel im Rahmen einer Hochschulpräsentation.  
Der Fokus liegt auf der Verwendung von **TCP-Sockets**, einem eigenen **Spielzustandsmodell** und einer **Tick-basierten Spielschleife**.

Der Server ist so konzipiert, dass er mit einer Node.js-basierten WebSocket-Bridge zusammenarbeitet,  
um die Kommunikation mit einem browserbasierten Frontend zu ermöglichen (siehe separater Client-Teil).

---

## ⚙️ Technologien

- Kotlin (JVM)
- Java TCP (ServerSocket)
- TimerTask-basierte GameLoop
- JSON-Ausgabe an Clients (ohne externe Bibliotheken)
- Multithreading mit ExecutorService

---

## 📂 Projektstruktur

pong-server/
├── GameState.kt # Zentrale Datenstruktur: Ball, Paddle, Velocity
├── GameLoop.kt # Spielschleife (Ticker), Kollisionslogik, Broadcast
├── GameServer.kt # TCP-Server, verarbeitet eingehende Client-Kommandos
└── README_Server.md


---

## 🔁 Netzwerkprotokoll

### Eingaben (vom Client an Server, über TCP)

Textbasierte Steuerbefehle:

- `"up1"` – Paddle 1 (links) nach oben
- `"down1"` – Paddle 1 nach unten
- `"up2"` – Paddle 2 (rechts) nach oben
- `"down2"` – Paddle 2 nach unten

### Ausgaben (vom Server an Client)

Der Spielzustand wird als JSON-String an alle verbundenen Clients gesendet:

```json
{
  "ballX": 412,
  "ballY": 220,
  "p1": 180,
  "p2": 200
}
```

Der Broadcast erfolgt standardmäßig alle 100 Millisekunden.
▶️ Server starten
🔧 Voraussetzungen

    Kotlin Compiler (kotlinc)

    JDK 17+ empfohlen

🧪 Kompilieren und Ausführen

kotlinc GameState.kt GameLoop.kt GameServer.kt -include-runtime -d server.jar
java -jar server.jar

    Der Server öffnet TCP-Port 12345 und akzeptiert parallele Verbindungen (Thread-basiert).

📌 Hinweise zur Präsentation

    Die Spielphysik (Ballbewegung, Kollisionsverhalten) ist vollständig im Server implementiert.

    Die Web-Clients (Browser) interagieren nur indirekt über eine separate WebSocket-Bridge (Node.js).

    Der Aufbau eignet sich zur Veranschaulichung von:

        TCP-Kommunikation

        Verteilten Zuständen

        GameLoop-Architektur

        Brücken-Pattern (TCP ↔ WebSocket)

📚 Lizenz / Verwendung

Dieses Projekt dient ausschließlich zu Lehr- und Demonstrationszwecken
im Rahmen des Moduls Betriebssysteme und Verteilte Systeme
an einer deutschen Hochschule (2. Semester).

Keine kommerzielle Nutzung vorgesehen.
