# Mutlithread Server Client Ping Pong Game Simulation



In this project, I am trying to implement [TCP protocol](https://en.wikipedia.org/wiki/Transmission_Control_Protocol) in Java to simulate a Ping Pong Game. I also implemented Threading on Server to connect multiple clients concurrently. The basic definition of the game is as follows:
 - Server and Client both acts as a player.
 - First, a uniformly distribute toss is conducted using random values either 0 or 1. The player with lower value on toss, will start the game.
   - The player who starts first, will print `Ping` on the output console of other player and the opposing player will print `Pong`.
 - Multiple clients/players can connect to a single Server and Server keeps the record to which Client it's playing with.
 
 
 ### Multi-threaded Server Client design
 <img width="835" alt="Multithreaded Server Client Design" src="https://user-images.githubusercontent.com/105172154/217138078-0f3067e1-60e0-4382-afa0-7f503f1a6792.png">

 
 ### Code Implementation
 
 I like creating classes into seperate files, so I created four classes `Ball.java`, `Client.java`, `Server.java`, and `ServerClientHandler.java`. 
 - `Ball.java` is serialized class to simulate the behavior of a BALL in a ping pong game. It has following data members:-
   - *message* is a string data member to carry Ping/Pong between client and server.
   - *tossValue* is an integer data member which carries client toss value either 0 or 1 to server.
   - *tossResult* is an string data member which carries the toss result that is "Server" or "Client" if there is result of toss otherwise "Tie".


### Instructions for executing the code
First, please compile the files from your Terimal or Command Prompt using following commands
```
javac Server.java
javac Client.java
```

Once these files are successfully compiled, then:
1. First, initiate a Server by following command.
```
java Server
```
2. Then open a new Command Prompt or Terminal and execute following command.
```
java Client
```
3. Now, you will see the following output on each of the prompts.
4. If you want to run more than 1 client, repeat step 2 as many time as you like. Then you will see the following output on Server prompt indicating different palyers.
