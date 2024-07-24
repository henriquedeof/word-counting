# Word Processing Application

This project is a word processing application that counts words in a file based on specific criteria defined in a properties file.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)

## Features
- Counts the number of words in a file that start with a specific character.
- Filters words based on a minimum length specified in the properties file.
- Logs the results using SLF4J and Logback.
- Configurable via a properties file (wordCounting.properties).
- Generates a fat JAR (uber JAR) with all dependencies included for easy execution.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/henriquedeof/word-counting.git
    cd word-counting
    ```

2. Build the project using Gradle:
    ```sh
    ./gradlew shadowJar
    ```

## Usage

To run the application, use the following command:
```sh
java -jar build/libs/word-counting-1.0.0.jar <source-path> <properties-file-path>
```
