# Helden-Plugin

Ein Spigot/Paper-Plugin für Minecraft-Server (getestet für 1.21.9 – 26.2). Bietet einen Villager-Wirtschaftskreislauf, einen Shop, Basen-Schutz und Starter-Items für neue Spieler.

## Aus Quellcode bauen

Voraussetzungen: JDK 21+, Maven.

```bash
mvn clean package
```

Die fertige Datei liegt danach unter `target/Helden-Plugin.jar`. Die Abhängigkeit `spigot-api` ist als `provided` markiert (wird vom Server zur Laufzeit bereitgestellt) — passe die Version in `pom.xml` bei Bedarf an deine Ziel-Serverversion an.

## Installation (fertiges Jar)

1. `Helden-Plugin.jar` in den `plugins`-Ordner deines Servers legen.
2. Server (neu)starten.
3. Beim ersten Start wird `plugins/Helden-Plugin/config.yml` angelegt.

## Berechtigungen

| Permission | Standard | Freischaltet |
|---|---|---|
| `helden-plugin.admin` | Nur OP | `/placeshop`, `/setstartitems` |

`/setbase` und `/villagerinfo` kann jeder Spieler nutzen.

## Für Spieler

### Erster Login
Beim ersten Betreten des Servers bekommt jeder Spieler automatisch:
- 20 Emeralds
- Die vom Admin konfigurierten Starter-Items (siehe `/setstartitems`)

### Basis setzen — `/setbase`
Setzt an der aktuellen Position einen **30-Block-Radius-Schutz**. Innerhalb dieses Radius können andere Spieler (die nicht OP sind) keine Blöcke abbauen oder platzieren.

- Funktioniert nur **einmal pro Spieler** — es gibt keine Möglichkeit, die Basis danach zu verschieben.
- Du erhältst dabei ein Schild-Item **"Villager-Übersicht"**. Rechtsklick damit (egal ob auf einen Block oder in die Luft) zeigt deine aktuelle Villager-Anzahl und dein zu erwartendes Einkommen an — das Item wird dabei **nicht** als Block platziert.

### Villager einsammeln
Es gibt zwei Wege, wie dir ein Villager gehört:

1. **Automatisch per Spawn-Ei**: Wenn in der Nähe eines Spielers (< 10 Blöcke) ein Villager per **Spawn-Ei** gespawnt wird, wird er automatisch diesem Spieler zugewiesen. (Achtung: Villager-Spawn-Eier gibt es nur im Kreativmodus, im Überlebensmodus wirst du diesen Weg normalerweise nicht nutzen können.)
2. **Manuell durch Rechtsklick**: Rechtsklickst du einen Villager, der noch **niemandem gehört** (egal ob er natürlich gespawnt, gezüchtet, mit `/summon` erzeugt oder bereits vorhanden war), öffnet sich **statt** des Handelsfensters zuerst ein kleines Bestätigungsmenü:
   - **Grüne Wolle "Ja, hinzufügen"**: Der Villager wird dir zugewiesen, danach öffnet sich automatisch das Handelsfenster.
   - **Rote Wolle "Nein"**: Abbrechen, der Villager bleibt unbeansprucht.

Gehört der Villager bereits **dir**, öffnet sich beim Rechtsklick ganz normal das Handelsfenster (wie in Vanilla Minecraft). Gehört er **einem anderen Spieler**, wird das Handelsfenster **nicht** geöffnet — du bekommst stattdessen nur angezeigt, wem er gehört.

Alle 4 Spieltage (`96000` Ticks) bekommt jeder Besitzer **5 Emeralds pro zugewiesenem, lebendem Villager** automatisch ausgezahlt (nur wenn online).

- **Stirbt** ein dir gehörender Villager (egal wodurch), verlierst du ihn aus deiner Zählung — du bekommst danach keine Emeralds mehr für ihn und wirst per Nachricht informiert.
- Rechtsklick auf einen fremden/eigenen Villager (der dir bereits gehört) zeigt dir dessen Besitzer an bzw. öffnet den Handel.
- `/villagerinfo` zeigt jederzeit deine aktuelle Villager-Anzahl und dein Einkommen an.

### Shop nutzen
Rechtsklick auf den Notenblock am Shop-PC (siehe `/placeshop`) öffnet das Shop-Menü:
- **Steinarten**, **Hölzer**, **Eisen**, **Essen**, **Spawn-Eier** als Kategorien
- Linksklick = 1 Stück kaufen, Rechtsklick = 64 Stück kaufen
- Bezahlt wird automatisch mit Emeralds aus deinem Inventar

## Für Admins

### Shop-Standort setzen — `/placeshop`
Platziert **an deiner aktuellen Position** einen Notenblock (Kaufterminal) mit einem Schild darüber. Nur ein Shop-Standort ist gleichzeitig aktiv — ein erneuter Aufruf verschiebt den aktiven Shop-Standort dorthin, wo du gerade stehst (der alte Notenblock bleibt als Deko-Block stehen, ist aber nicht mehr interaktiv).

### Starter-Items konfigurieren — `/setstartitems`
Öffnet ein Inventar mit 27 freien Slots. Was du dort hineinlegst, bekommen alle **neuen** Spieler beim ersten Join zusätzlich zu den 20 Emeralds.
- **Grünes Wollblock** (Slot 40): Speichern & Schließen
- **Rotes Wollblock** (Slot 44): Alle Starter-Items löschen
- Schließt du das Inventar einfach (z. B. mit ESC), wird ebenfalls automatisch gespeichert.

## Konfiguration (`config.yml`)

Wird automatisch verwaltet, in der Regel nicht von Hand bearbeiten:

```yaml
starter-items: []       # von /setstartitems verwaltet
shop-location: null      # von /placeshop verwaltet
bases: {}                 # von /setbase verwaltet
villager-owners: {}       # Villager-Zuordnung
first-join-players: []    # wer schon Starter-Items bekommen hat
```

## Bekannte Design-Grenzen

- Jeder Spieler kann nur **eine** Basis in seinem Spielerleben setzen (kein Reset-Befehl vorhanden).
- Es kann nur **ein** Shop-Standort gleichzeitig aktiv sein (kein Multi-Shop).
- Preise für Shop-Items sind im Code fest hinterlegt (siehe `ShopManager`), nicht per Config änderbar.
- Offene Villager-Bestätigungsmenüs werden bei einem Serverneustart verworfen (kein Problem — einfach erneut anklicken).

## Projektstruktur

```
Helden-Plugin/
├── pom.xml                          Maven-Build-Konfiguration
├── src/main/java/de/heldenplugin/main/
│   ├── HeldenPlugin.java                Haupt-Plugin-Klasse (onEnable/onDisable)
│   ├── commands/                     Alle Befehle (/placeshop, /setbase, ...)
│   ├── listeners/                    Event-Listener (Shop, Villager, Schutz, ...)
│   └── manager/                      Geschäftslogik & Datenhaltung
└── src/main/resources/
    ├── plugin.yml                    Plugin-Metadaten, Befehle, Berechtigungen
    └── config.yml                    Default-Konfiguration
```

## Versionshinweise

- `api-version: "1.21"` in der `plugin.yml` — kompatibel mit Spigot/Paper **1.21.x** bis **26.2**.
- Java 21+ auf dem Server erforderlich (bzw. Java 25 für Minecraft 26.1+).

### Translations will come soon!

