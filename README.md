# Catan Simulator - TEAM-24
## SFWRENG 2AA4 - Assignment 1
## Date: Feb 13, 2026

## SonarQube Badge

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mahnoornav_2AA4-team-24&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=mahnoornav_2AA4-team-24)

## Authors:
- Alyshba Arshad
- Mahnoor Naveed
- Zainab Mirza
- Swetha Jagannath
  
## Program Overview 

This program simulates the popular board game Settlers of Catan using OO design and SOLID princples. It implements the core components from Catan and simulates four players taking turns until one reaches 10 victory points or the max number of roundd is reached causing the game to end. 

The program contains the following classes:

1. Board.java - Manages the game board, including tiles, settlements, roads, and placement logic.
2. Player.java - Represents a game player and manages player resources, actions and points.
3. Tile.java - Represents a resource tile with its type and number.
4. Settlement.java - Represents a settlement or city on the board, that is owned by a player.
5. Road.java - Represents a road on the board, that is owned by a player. 
6. ResourceType.java - Defines the various types of resources in Catan.
7. Catan.java - Handles all game logic including turn taking, dice rolls and validating wins.
8. Demonstrator.java - Contains the main method to run simulation. 
       
# How to run simulation 

1. Open IntelliJ / Eclipse
2. Open and Run Demonstrator.java
     - Click run 'Demonstrator.main()'
     
This will the run the simulator and print the actions and points earned by each player on the terminal.    



