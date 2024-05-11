# CatchOrWaste: Java Game Application for Raspberry Pi Using FXGL

[![Build Status](https://github.com/pi4j/pi4j-example-fxgl/workflows/Maven/badge.svg)](https://github.com/Pi4J/pi4j-example-fxgl/actions/workflows/maven.yml)

<img src="assets/GAME_GIF.gif" alt="GIF" width="200"/>

## Table of Contents

- [Project Overview](#project-overview)
- [Runtime Dependencies](#runtime-dependencies)
- [Build Dependencies & Instructions](#build-dependencies--instructions)
- [Setup new RaspberryPi](#setup-new-raspberrypi)
- [Run on RaspberryPi](#run-on-raspberrypi)
- [Game modifications](#game-modifications)
- [Hardware](#hardware)
- [Features](#features)
- [License](#license)

## Project Overview

This game was developed as part of a project at the University of Northwestern Switzerland ([FHNW](https://www.fhnw.ch/de/)) for the energy company [Primeo](https://www.primeo-energie.ch/privatkunden.html). It is an arcade game programmed in Java aimed at educating children and young people about the throwaway society.

<img src="assets/img/box_front.jpg" alt="box_back" width="300"/>

<img src="assets/img/box_side.jpg" alt="box_front" width="300"/>

<img src="assets/img/box_back.jpg" alt="box_side" width="300"/>

## Runtime Dependencies

This project uses Pi4J V.2 which requires the following runtime dependencies:

- [**SLF4J (API)**](https://www.slf4j.org/)
- [**SLF4J-SIMPLE**](https://www.slf4j.org/)
- [**PIGPIO Library**](http://abyz.me.uk/rpi/pigpio) (for the Raspberry Pi) - This library is pre-installed on recent Raspbian images, but can also be manually installed using the instructions found [here](http://abyz.me.uk/rpi/pigpio/download.html).

This application also utilizes a JavaFX user interface, fully detailed in the [User Interface with JavaFX](https://v2.pi4j.com/getting-started/user-interface-with-javafx/) documentation.

## Build Dependencies & Instructions

To build this project, you need [Apache Maven](https://maven.apache.org/) 3.6 or later and Java 11 OpenJDK or later. Ensure these prerequisites are installed before proceeding. The following Maven command will clean previous builds and package the project:

```
mvn package clean
```

## Setup new RaspberryPi
To setup a new RaspberryPi ..


## Run on RaspberryPi

### Compile via Run Configuration
Ensure your local machine is connected to the same network as your Raspberry Pi then follow these steps:

1. Configure the IP address in the [pom.xml](pom.xml) file.
2. Select the [Run on Pi.run.xml](.run%2FRun%20on%20Pi.run.xml) run configuration.
3. Execute the configuration. Upon a successful build, the new code will be transferred to the Pi (`/home/pi4j/deploy`) and the scripts will execute automatically, replacing any old project in this directory and setting up the service.

#### Compile Manually
After a successful build, locate the generated files in the `/target/distribution` folder. Copy these files to your Raspberry Pi (`/home/pi4j/deploy`) using SCP.

To manually run the application on the Raspberry Pi run the [start-app.sh](src%2Fmain%2Fresources%2Fscripts%2Fstart-app.sh) script:
```
sudo ./start-app.sh
```

To enable the application to start automatically after a boot or reboot run the [install-service.sh](src%2Fmain%2Fresources%2Fscripts%2Finstall-service.sh) script:

```
sudo ./install-service.sh
```

## Game modifications


## Hardware

### Components

| Component                                   | Count | Info                                                                                      |
|---------------------------------------------|-------|-------------------------------------------------------------------------------------------|
| Arcade Joystick, 8 Ways, 65,3mm hight, red  | 1     | [Technical sheet](assets%2Fpdf%2FArcade-Joystick-8-Wege-65-3mm-Hoehe-rot.pdf)             |
| Arcade Button, 30mm, LED 5V DC, transparent | 3     | [Technical sheet](assets%2Fpdf%2FArcade-Button-30mm-beleuchtet-LED-5V-DC-transparent.pdf) |
| EIZO FlexScan 19” (48cm)                    | 1     | [Technical sheet](assets%2Fpdf%2Feizo_l768_datenblatt.pdf)                                |
| Raspberry Pi                                | 1     | [Technical sheet](assets%2Fpdf%2Fraspberry-pi-4-reduced-schematics.pdf)                   |
| USB Hub                                     | 1     | 4x USB 3.1                                                                                |
| Speakers                                    | 1     | Old Speakers integrated (see img)                                                         |


### Hardware Modifications
Ensure the box is unplugged before making any changes to the hardware. To access the internal components, simply remove the three screws at the back of the box.

### Wiring


## Features

[Describe the game's unique features here]

## License

This project is licensed under the Apache License, Version 2.0. You may not use this file except in compliance with the License, which you can find at:
http://www.apache.org/licenses/LICENSE-2.0

Software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. Refer to the License for the specific language governing permissions and limitations under the License.
