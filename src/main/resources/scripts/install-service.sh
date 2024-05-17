#!/bin/sh

dos2unix "$0"

# Definiere den Pfad für den Service und das Skript

SERVICE_PATH="/etc/systemd/system/game.service"
SERVICE_SCRIPT="/home/pi4j/deploy/game.service"

# Kopiere den Service in das systemd Verzeichnis
sudo cp $SERVICE_SCRIPT $SERVICE_PATH

# Setze die korrekten Berechtigungen für den Service
sudo chmod 644 $SERVICE_PATH

# Lade den systemd Daemon neu, um Änderungen zu erkennen
sudo systemctl daemon-reload

# Aktiviere den Service, damit er beim Booten startet
sudo systemctl enable game.service

# Starte den Service sofort
sudo systemctl start game.service
