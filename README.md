# SOFTENG 206 Project

## Kēmu Kupu

This is an app designed to test people’s ability to spell words in the Māori dictionary and to aid in the learning of Te reo Māori. The target audience of Kēmu Kupu is young adults within the 18-25 year old range, who have recently moved to New Zealand and want to improve their knowledge of Te reo Māori.

## Setup

### Dependencies:
Use the following commands to install the required dependencies.

- Java Runtime Environment (JRE) 11
```bash
> sudo apt install openjdk-11-jre
```

- JavaFX 11
```bash
> sudo apt install openjfx
```

- Festival Speech Synthesis System
```bash
> sudo apt install festival
```

*NOTE:* Festival requires Maori voice packages. This can be acquired from the [University of Auckland](https://www.auckland.ac.nz/).

### Additional Dependencies:
Use the following commands to install the required dependencies, these ensure no errors while running.

```bash
> sudo apt-get install gtk2-engines-pixbuf
> sudo apt-get install libcanberra-gtk-module
```

## Māori Keyboard (Ubuntu)

Add a Māori keyboard to Elementary OS by going to:
1. System Settings
2. Keyboard
3. \+ (bottom left)
4. Maori
5. Add Layout

Add macrons to vowels by pressing `<Right Alt> + <Vowel>`.

## Run
Ensure JavaFX is located in the following directory:

```
/home/student/javafx-sdk-11.0.2/javafx
```

Ensure that ```run.sh``` is given executable permissions for the current user.
```bash
> chmod +x run.sh
```

Start the application.
```bash
> ./run.sh
```
