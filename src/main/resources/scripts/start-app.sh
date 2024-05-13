#!/bin/sh

DISPLAY=:0 XAUTHORITY=/home/pi4j/.Xauthority sudo -E java -XX:+UseZGC -Xmx1G \
  --module-path "/opt/javafx-sdk-20.0.2/lib:/home/pi4j/deploy" \
  --add-modules javafx.controls \
  -Dglass.platform=gtk \
  --module com.pi4j.example/catchorwaste.CatchOrWasteApp
