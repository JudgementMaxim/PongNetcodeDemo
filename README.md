# 🏓 Pong Netcode Demo (Kotlin Server + Prediction)

**Ziel dieses Projekts:**  
Ein einfaches Pong-Spiel, das demonstriert, wie Netzwerkverzögerung kompensiert werden kann – mit **Client Prediction**, **Server Reconciliation** und **künstlicher Latenz**.

---

## 🎯 Features

- ✅ Pong-Gameplay mit Ball & Paddle
- ✅ TCP-Netzwerkkommunikation (Client ↔ Server)
- ✅ Künstliche Latenz-Simulation (z. B. 150 ms)
- ✅ Client Prediction (Bewegung sofort anzeigen)
- ✅ Server Reconciliation (Korrektur bei Abweichung)
- ✅ Umschaltbar: Prediction & Latenz
- ✅ Optional: Docker für einfachen Deployment

---

## 🧱 Projektstruktur

```plaintext
pong-netcode-demo/
│
├── server/                   ← Kotlin TCP-Server
│   ├── GameServer.kt
│   └── GameState.kt
│
├── client/                   ← GUI oder CLI-Client (z. B. JS, Kotlin Desktop)
│   └── PongClient.kt
│
├── docker/
│   └── Dockerfile.server     ← Für Kotlin Server
│
└── README.md                 ← Dieses Dokument
```

---

## 🚦 Ablaufdiagramm

```plaintext
[Client Input] → (Prediction lokal) → [an Server senden]
                                 ↓
                          [Server verarbeitet]
                                 ↓
                    [GameState an Client zurück]
                                 ↓
             [Client vergleicht Prediction vs Server]
                 → Wenn abweichend: Reconciliation
```

---

## 🧪 Simulation: Latenz & Prediction sichtbar machen

| Modus               | Beschreibung |
|---------------------|--------------|
| `Prediction OFF`    | Paddle reagiert nur nach Serverantwort → wirkt verzögert |
| `Prediction ON`     | Paddle bewegt sich sofort → glatter Eindruck |
| `Reconciliation`    | Wenn Position zu stark abweicht → visuelles „Zurückspringen“ |
| `Latency Slider`    | 0–500 ms künstliche Verzögerung zur Demonstration |

---

## 🔧 Starten des Servers (ohne Docker)

### Voraussetzungen:
- Java 17+
- Kotlin CLI oder IntelliJ

```bash
cd server
kotlinc GameServer.kt -include-runtime -d server.jar
java -jar server.jar
```

---

## 🐳 Starten mit Docker

```bash
cd docker
docker build -t pong-server .
docker run -p 12345:12345 pong-server
```

---

## 🎮 Steuerung (Client)

- ↑ / ↓  → Paddle bewegen
- `P`    → Prediction an/aus
- `L`    → Latenz erhöhen/vermindern

---

## 🧠 Begriffe (kurz erklärt)

### Client Prediction
> Spielerinput wird sofort lokal angezeigt, ohne auf Serverantwort zu warten.

### Server Reconciliation
> Der Server hat das letzte Wort. Wenn die lokale Vorhersage falsch war, wird der echte Zustand vom Server durchgesetzt.

### Rollback (optional)
> Der Client "springt zurück" zum alten Zustand und wendet neue Inputs erneut an (nicht Teil dieser Demo).

---

## 📚 Quellen

- [Gaffer on Games – Client Prediction](https://gafferongames.com/post/client_server_game_loop/)
- [Valve Netcode Overview](https://developer.valvesoftware.com/wiki/Source_Multiplayer_Networking)
- [GGPO – Rollback Explained](https://www.ggpo.net/)

---

## 📬 Kontakt / Autor

Erstellt im Rahmen einer Uni-Präsentation über Echtzeit Multiplayer Architekturen unter Linux (2. Semester).  
Kotlin-Server by Maxim Skalenko
