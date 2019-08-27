"# MultiModeApp02CarsSelector" 
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)

## General info
Multi-Module Maven Application with Java Modules , still in progress adding new functionality

## Technologies
* Java - version 12
* gson - version 2.8.4
* maven - version 3.6
* Multi-Module Maven 
* TestUnit junit 4.12
* junit-jupiter

## Setup
download, compile and run, in module main file to compile main-1.0-SNAPSHOT-jar-with-dependencies.jar

## Code Examples
 public Map<Car2, Integer> mapByCarsAndMileage() {
        System.out.println("solution for task nr 5 --------------->>>>>>>>>>>>");
        return car2Set.stream()
                .collect(Collectors.toMap(
                       Function.identity(),
                        Car2::getMileage
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::max,
                        LinkedHashMap::new
                ));
    }

## Features

To-do list:
- cleanCode - optimize code 
- Junit
- Json



## Status
Project is: _in_progress_