# Catan Game - TEAM-24
## SFWRENG 2AA4 - Assignment 1 & 2
## Date: March 11, 2026

## SonarQube Badge

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mahnoornav_2AA4-team-24&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mahnoornav_2AA4-team-24)

## Authors:
- Alyshba Arshad
- Mahnoor Naveed
- Zainab Mirza
- Swetha Jagannath

## Catan Simulator

This repository contains a java implementation of a simulator for the popular game Settlers of Catan using OO design and SOLID principles. It implements the core components from Catan and simulates player turns, resource distribution, player trading and building structures.

## Program Overview 

The simulator implements the main components of Catan including: 

- Board and tile layout
- Players and resources
- Settlements, cities, roads
- Dice rolls, player turns
- Player resource trading
- Game state exportion to JSON file

The game runs in the terminal, simulating four players taking turns until one reaches 10 victory points or the max number of roundd is reached causing the game to end. 

## Repo Structure

2AA4-team-24
  - Assignment_1
      - Initial Catan simulation implementation (computer players only)
  - Assignment_2
      - Extended version with human input and game state exporting (human + computer players)
  - README.md
  - sonar-project.properties

  
## Game Components 

Core parts
- Catan.java - Handles main game loop, turn logic, dice rolls
- Board.java - Manages board states including placement of settlements, roads, cities
- ValidateMove.java - Validates player moves according to game rules
- Robber.java - Represents the robber component used if 7 is rolled

Player System
- Player.java - Represents a player and their resources and points
- HumanPlayer.java - Represents a human player that can interact with game through commands
- ComputerPlayer.java - simulater automated player and its actions 
- Trade - interface defining trading logic between players

Structures
- Structure.java - Base class for building elements in catan
- Settlement.java - Represents settlements placed on board owned by players
- City.java - Represents cities placed on board owned by players
- Road.java - Represents a road on the board, that is owned by a player. 

Game reosurces 
- Tile.java - Represents a resource tile with its number
- ResourceType.java - Defines all different resources in catan
- Dice.java - simulates rolling two six-sided dices 

User interaction 
- CommandParser.java - Parses and interprets commands entered by human player

Output 
- GameExporter.java - Exports current game state into a JSON file for visualizer
- Demonstrator.java - Contains main method to run game simulator

       
## How to run simulator

1. Clone repository
    - git clone https://github.com/mahnoornav/2AA4-team-24.git
    - cd 2AA4-team-24

1. Open project
    - in IntelliJ  or Eclipse
  
4. Run Program
     - Locate Assignment_2/RevisedCatanCode
     - Run Demonstrator.java
         - Click Run 'Demonstrator.main()'
     
This will the run the simulator and display the game on the terminal. You can interact with game through command line prompts.

