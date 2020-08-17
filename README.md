# coding-challenge
5 in Row Coding Challenge

This application is developed using Java 1.8 and Springboot 2.2. Prerequisite to run this application is java 1.8

## Coding Challenge Repo structure

### connect-game 
This directory contains the maven project for the connect-game server. 

### connect-game-client
This directory contains the maven project for the connect-game-client

## Running the Connect-Game server from command line

1. Go to the directory where connect-game-0.0.1-SNAPSHOT.jar is saved

2. Run the following command to start the connect-game server
   $ java -jar connect-game-0.0.1-SNAPSHOT.jar

## Running the Connect-Game clients from command line

1. Open two command line terminals

2. Go to the directory where connect-game-client-0.0.1-SNAPSHOT.jar is saved

3. Run the following command in both terminals to start the clients :   
   $ java -jar connect-game-client-0.0.1-SNAPSHOT.jar

4. If clients needs to be run from different machines, provide server ip address and port with command :  
   $  java -jar connect-game-client-0.0.1-SNAPSHOT.jar --destination.address=http://10.120.93.154:8190 
