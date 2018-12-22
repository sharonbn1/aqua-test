# Aqua Test

My solution to Aqua Security programming test

## Overview

The solution includes stage 1 and stage 2 and also the first bonus point. <br>
The solution is written in Java. It uses maven as build and dependency management tool.<br>
The solution is comprised of three separate projects:<br>
* stage1 - standalone Java application
* stage2 - Spring Boot web application with embedded Apache Tomcat web server
* common - contains shared classes between the stages: API and helper utilities to parse/manipulate json/xml etc.

## Prerequisites

* [Java JDK](https://www.oracle.com/technetwork/java/javase/overview/index.html) - version 8 or higher (solution was built and tested with version 11)
* [Maven](https://maven.apache.org/) - version 3 or higher (solution was built with version 3.2.1)

## Build

from the base directory, run

```
mvn clean package
```
alternatively, invoke `build.cmd` from the base directory.

## Run

All stages produce single executable jar files. The build artifacts can be found under `target` directory of each project.

in order to run stage 1 from the base directory, run 

```
java -jar ./stage1/target/stage1-1.0.jar --help
```
script file `run_stage1.cmd` contains a sample run with args <br>
in order to run stage 2 from the base directory, run 

```
java -jar ./stage2/target/stage2-1.0.jar --help
```
script file `run_stage2.cmd` contains a sample run with args 
