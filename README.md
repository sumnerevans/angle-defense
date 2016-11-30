# Angle Defense
Final project for CSCI 306 Software Engineering by **bungle.jar**.

**Team Members:** [Jonathan Sumner Evans](http://the-evans.family/sumner), [Robbie
Merillat](https://github.com/BloodRaine), and [Sam Sartor](http://gh.drocclusion.net/).

## Description
Like tower defense, but with angles. Because math.

## Opening/Building
### Eclipse
1. Open the project directory (containing src/, build.gradle, etc) in command prompt or some other
   shell
2. Run `gradle eclipse`
3. Open the directory as an eclipse project

### IntelliJ Idea
1. `File->New->Project from Existing Sources...` or `* Import Project` (if you are in the welcome
   screen)
2. Select "Import project from existing model" and "Gradle"
3. Click next a few times

### No IDE
* Run `gradle build` to create a jar
* Run `gradle check` to run tests (results shown in ./build/reports/tests/index.html)
