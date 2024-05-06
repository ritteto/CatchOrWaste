
CatchOrWaste :: Java Game Application for Raspberry Pi using FXGL
=======

[![Build Status](https://github.com/pi4j/pi4j-example-fxgl/workflows/Maven/badge.svg)](https://github.com/Pi4J/pi4j-example-fxgl/actions/workflows/maven.yml)

## Table of Contents

- [Project Overview](#project-overview)
- [Runntime Dependencies](#runtime-dependencies)
- [Build Dependencies & Instructions](#build-dependencies--instructions)
- [Hardware](#hardware)
- [Features](#features)
- [Licence](#license)

## PROJECT OVERVIEW

The goal of the example project is to show how to set up a Pi4J Maven for the Raspberry Pi with JavaFX and some physical
buttons.

The full description is available on
[Game development with FXGL](https://v2.pi4j.com/getting-started/game-development-with-fxgl/).

## RUNTIME DEPENDENCIES

This project uses Pi4J V.2 which has the following runtime dependency requirements:

- [**SLF4J (API)**](https://www.slf4j.org/)
- [**SLF4J-SIMPLE**](https://www.slf4j.org/)
- [**PIGPIO Library**](http://abyz.me.uk/rpi/pigpio) (for the Raspberry Pi) - This dependency comes pre-installed on
  recent Raspbian images. However, you can also download and install it yourself using the instructions found
  [here](http://abyz.me.uk/rpi/pigpio/download.html).

As this application has a JavaFX user interface, we will also need some extra runtimes. This is fully described
on ["User interface with JavaFX](https://v2.pi4j.com/getting-started/user-interface-with-javafx/).

## BUILD DEPENDENCIES & INSTRUCTIONS

### First Installation

Install the game by following these steps:

- Make sure JavaFX & Java is installed on the RaspberryPi
- Make sure the Raspberry Pi is connected to the same network as your local machine
- Change the IP-address in the pom.xml file (See Debian Desktop Wallpaper)
- Run the Application with the specified run configuration (in the .run directory)
- Now you're all set and the Application will automatically start everytime you boot / reboot the Raspberry Pi


This project can be built with [Apache Maven](https://maven.apache.org/) 3.6
(or later) and Java 11 OpenJDK (or later). These prerequisites must be installed prior to building this project. The
following command can be used to download all project dependencies and compile the Java module. You can build this
project directly on a Raspberry Pi with Java 11+.

### Compiled application to run on the Raspberry Pi

This is the list of files created by the build process of this example application:

* pi4j-core
* pi4j-example-fxgl
* pi4j-library-pigpio
* pi4j-plugin-pigpio
* pi4j-plugin-raspberrypi
* slf4j-api
* slf4j-simple
* game.service -> this is a service, that will automatically start the game application on boot / reboot
* install-service.sh -> this script will automatically setup the autostart feature
* start-app.sh --> this is the actual start file which will run pi4j-example-fxgl

```
sudo ./start-app.sh
```

To manually setup the service for autostart run:

```
sudo ./install-service.sh
```

## HARDWARE


## FEATURES


## LICENSE

Pi4J Version 2.0 and later is licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a copy of the License at:
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
language governing permissions and limitations under the License.

