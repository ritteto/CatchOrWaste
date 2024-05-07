CatchOrWaste: Java Game Application for Raspberry Pi Using FXGL
=======

[![Build Status](https://github.com/pi4j/pi4j-example-fxgl/workflows/Maven/badge.svg)](https://github.com/Pi4J/pi4j-example-fxgl/actions/workflows/maven.yml)

## Table of Contents

- [Project Overview](#project-overview)
- [Runtime Dependencies](#runtime-dependencies)
- [Build Dependencies & Instructions](#build-dependencies--instructions)
- [Hardware](#hardware)
- [Features](#features)
- [License](#license)

## Project Overview

This game was developed as part of a project at the University of Northwestern Switzerland ([FHNW](https://www.fhnw.ch/de/)) for the energy company [Primeo](https://www.primeo-energie.ch/privatkunden.html). It is an arcade game programmed in Java aimed at educating children and young people about the throwaway society.

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

### Running the Compiled Application on the Raspberry Pi

#### Compile via Run Configuration
Ensure your local machine is connected to the same network as your Raspberry Pi, then follow these steps:

1. Configure the IP address in the pom.xml file.
2. Select the "Run on Pi" run configuration, which is specified in the `.run` directory.
3. Execute the configuration. Upon a successful build, the new code will be transferred to the Pi (`/home/pi4j/deploy`) and the scripts will execute automatically, replacing any old project in this directory.

#### Compile Manually
After a successful build, locate the generated files in the `/target/distribution` folder. Copy these files to your Raspberry Pi (`/home/pi4j/deploy`) using SCP.

To manually run the application on the Raspberry Pi:
```
sudo ./start-app.sh
```


To enable the application to start automatically after a boot or reboot:

```
sudo ./install-service.sh
```


## Hardware

### Components

1. Joystick
2. Buttons
3. EIZO Screen
4. USB Hub
5. Speakers
6. Raspberry Pi

### Hardware Modifications
Ensure the box is unplugged before making any changes to the hardware. To access the internal components, simply remove the three screws at the back of the box.

## Features

[Describe the game's unique features here]

## License

This project is licensed under the Apache License, Version 2.0. You may not use this file except in compliance with the License, which you can find at:
http://www.apache.org/licenses/LICENSE-2.0

Software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. Refer to the License for the specific language governing permissions and limitations under the License.
