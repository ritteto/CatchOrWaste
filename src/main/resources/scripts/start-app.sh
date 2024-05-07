#!/usr/bin/env bash

dos2unix "$0"

# Java und JavaFX Konfiguration
JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64      # Angepasster Pfad zur Java-Installation
JAVAFX_SDK=/opt/javafx-sdk-20.0.2/lib             # Pfad zum JavaFX SDK

# Starte das Spiel mit den richtigen JavaFX-Modulen und Eigenschaften
#!/usr/bin/env bash

# Setze Umgebungsvariablen
export DISPLAY=:0
export XAUTHORITY=/home/pi4j/.Xauthority

# Starte die JavaFX-Anwendung
sudo -E java -XX:+UseZGC -Xmx1G \
  --module-path /opt/javafx-sdk/lib:/home/pi4j/deploy \
  --add-modules javafx.controls \
  -Dglass.platform=gtk \
  --module com.pi4j.example/catchorwaste.CatchOrWasteApp
