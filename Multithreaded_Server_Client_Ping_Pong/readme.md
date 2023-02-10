# homework-1-ping-pong-rubalgoyal
homework-1-ping-pong-rubalgoyal created by GitHub Classroom

The basic definition of the game is as follows:
 - Server and Client both acts as a player.
 - First, a uniformly distribute toss is conducted using random values either 0 or 1. The player with lower value on toss, will start the game.
   - The player who starts first, will print `Ping` on the output console of other player and the opposing player will print `Pong`.
 - Multiple clients/players can connect to a single Server and Server keeps the record to which Client it's playing with.
 
 ### Multi-threaded Server Client design
 The image below shows the conceptual design for Multi-threaded Server. Please read [Code Implementation](https://github.com/cs455-spring2023/homework-1-ping-pong-rubalgoyal/edit/main/README.md#code-implementation) for the program part.
 <img width="835" alt="Multithreaded Server Client Design" src="https://user-images.githubusercontent.com/105172154/217138078-0f3067e1-60e0-4382-afa0-7f503f1a6792.png">

 
 ### Code Implementation
 
 I like creating classes into seperate files, so I created four classes `Ball.java`, `Client.java`, `Server.java`, and `ServerClientHandler.java`. 
 - `Ball.java` is serialized class to simulate the behavior of a BALL in a ping pong game. It has following data members:-
   - *message* is a string data member to carry Ping/Pong between client and server.
   - *tossValue* is an integer data member which carries client toss value either 0 or 1 to server.
   - *tossResult* is an string data member which carries the toss result that is "Server" or "Client" if there is result of toss otherwise "Tie".
   
 - `Client.java` class acts as the Client and is a *main* class for Client. It connects with Server on a given Port and Address. As of now *port* and *address* are fixed. Each Client has it's own instance of `Ball.java` *b* which is communictaed with Server through an established connection. Depending upon the `b.tossResult` Client either starts first with *'Ping'* or respond to Server by *'Pong'*. The Client class has a method `generateRandomNumber` which generates an integer either 0 or 1 randomly.
     
 - `Server.java` class acts as the Server and is a *main* class for Server. The Server class first starts the server on a given *port* (as of now, it is hard coded) and then runs an infinite loop for accepting different clients also keeping track of number of clients is connected to with local variable *gameNumber*. Within each loop, this class starts a thread for `ServerClientHandler.java` which executes the Serve side game.
   -  `ServerClientHandler.java` this is class which implements the *Runnable* interface overiding the *run* method. For each successful connection, this class stores the incoming objects from Client into `Ball b` and then:
      - Similar to client it generates a random integer either 0 or 1 and compares that with `b.tossValue`. Stores the winner of toss as *Server* if Server is the winner, 'Client' is Client is the winner into `b.tossResult`. In case of tie, it stores the 'Tie'. This process continues until there is a definite winner.
      - Upon a winner identification, an infinite loop of communication continues between Server and the Client. The Server responds *'Ping'* if it's the winner otherwise *'Pong'*.

### Instructions for executing the code
Although this project has been compiled however, after cloning the repo or downloading the code files, you may please complie the projects. You can use following instructions to run the code from Terminal if using Mac or Command Prompt if using windows. Please ensure that you have the Java Complier installed if not already.

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

Below is the sample output.
![Screenshot 2023-02-07 at 8 33 27 PM](https://user-images.githubusercontent.com/105172154/217423715-d4b35df7-c88c-4200-be46-5ec9a70abf76.jpg)
![Screenshot 2023-02-07 at 8 33 42 PM](https://user-images.githubusercontent.com/105172154/217423763-5a267e4f-b757-455f-abbf-c341417a9c8d.jpg)

