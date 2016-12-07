# How to Contribute

## Environment Setup
Angle Defense uses Java 8 and Gradle. We recommend using [IntelliJ
IDEA](https://www.jetbrains.com/idea/) for development. The following instructions are for IntelliJ
IDEA:

1. `File -> New -> Project from Existing Sources...` or `* Import Project` (if you are on the
    welcome screen)
2. Select "Import project from existing model" and "Gradle"
3. Click next a few times

## How Angle Defense is Organized

    src
    |--> main
    |    |--> java
    |    |   |--> angleDefense: the package that contains everything
    |    |        |--> config: contains all of the configuration classes
    |    |        |            these basically are immutable objects that are
    |    |        |            deserialized from JSON
    |    |        |--> draw: contains all of the OpenGL stuff that nobody but
    |    |        |          Sam understands
    |    |        |--> gui: contains all of the GUI classes
    |    |        |--> logic: contains the game logic
    |    |        |--> util: utility classes and functions
    |    |--> resources
    |         |--> assets:
    |         |--> shaders:
    |         |--> assets.json: contains the asset configuration
    |         |--> default-config.json: contains the default configuration
    |         |                         which is used for normal gameplay
    |         |--> test-config.json: contains the configuration used for the
    |                                tests
    |--> test
    |    |--> java: JUnit test classes

## Configuration

## Level Configurations

