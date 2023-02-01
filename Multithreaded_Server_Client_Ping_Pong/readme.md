# Mutlithread Server Client Ping Pong Game Simulation



In this project, I am trying to implement [TCP protocol](https://en.wikipedia.org/wiki/Transmission_Control_Protocol) in Java to simulate a Ping Pong Game. I also implemented Threading on Server to connect multiple clients concurrently. The basic definition of the game is as follows:
 - Server and Client both acts as a player.
 - First, a uniformly distribute toss is conducted using random values either 0 or 1. The player with lower value on toss, will start the game.
   - The player who starts first, will print `Ping` on the output console of other player and the opposing player will print `Pong`.
 - Multiple clients/players can connect to a single Server and Server keeps the record to which Client it's playing with.
 
 
 ### Code Implementation
 
 I like creating classes into seperate files, so I created four classes `Ball.java`, `Client.java`, `Server.java`, and `ServerClientHandler.java`. 

