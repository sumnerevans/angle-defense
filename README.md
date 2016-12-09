# Angle Defense

> **ALL ACTIVE DEVELOPMENT ON ANGLE DEFENSE HAS MOVED**
>
> We have moved to: [angle](https://github.com/MadeFromTheBlue/angle)
>
> This repository will be kept for archival purposes, but no active development will be done.

-----

Final project for CSCI 306 Software Engineering by **bungle.jar**.

**Team Members:** [Jonathan Sumner Evans](http://the-evans.family/sumner), [Robbie
Merillat](https://github.com/BloodRaine), and [Sam Sartor](http://gh.samsartor.com/).

## Description
Like tower defense, but with angles. Because math.

Because of the complex dependencies, there is no way to export
this as an Eclipse project. Instead we used the automated build
system Gradle.

## Opening/Building
### IntelliJ IDEA
1. `File->New->Project from Existing Sources...` or `* Import Project` (if you are in the welcome
   screen)
2. Select "Import project from existing model" and "Gradle"
3. Click next a few times

### No IDE
* Run `gradlew run` to run the program (sometimes finicky)
* Run `gradlew check` to run tests (results shown in ./build/reports/tests/index.html)
* Run `gradlew jar` to create a cross-platfrom runnable jar (in ./build/libs/)

## Contributing
Read [CONTRIBUTING.md](https://github.com/sumnerevans/angle-defense/blob/master/CONTRIBUTING.md)
