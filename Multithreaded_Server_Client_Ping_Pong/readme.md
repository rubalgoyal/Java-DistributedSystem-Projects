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
   
 - `Client.java` class acts as the Client and is a *main* class for Client. It connects with Server on a given Port and Address. Each Client has it's own instance of `Ball.java` *b* which is communictaed with Server through an established connection. Depending upon the `b.tossResult` Client either starts first with *'Ping'* or respond to Server by *'Pong'*. The Client class has a method `generateRandomNumber` which generates an integer either 0 or 1 randomly.
     
 - `Server.java` class acts as the Server and is a *main* class for Server. The Server class first starts the server on a given *port* and then runs an infinite loop for accepting different clients also keeping track of number of clients is connected to with local variable *gameNumber*. Within each loop, this class starts a thread for `ServerClientHandler.java` which executes the Serve side game.
   -  `ServerClientHandler.java` this is class which implements the *Runnable* interface overiding the *run* method. For each successful connection, this class stores the incoming objects from Client into `Ball b` and then:
      - Similar to client it generates a random integer either 0 or 1 and compares that with `b.tossValue`. Stores the winner of toss as *Server* if Server is the winner, 'Client' is Client is the winner into `b.tossResult`. In case of tie, it stores the 'Tie'. This process continues until there is a definite winner.
      - Upon a winner identification, an infinite loop of communication continues between Server and the Client. The Server responds *'Ping'* if it's the winner otherwise *'Pong'*.

### Instructions for executing the code
Although this project has been compiled however, after cloning the repo or downloading the code files, you may please complie the projects. You can use following instructions to run the code from Terminal if using Mac or Command Prompt if using windows. Please ensure that you have the Java Complier installed if not already.

First, please compile the files from your Terimal or Command Prompt using following commands. In order to compile using below instruction please ensure your pwd is where you have cloned/downloaded these files.
```
javac Server.java
javac Client.java
```

Once these files are successfully compiled, then:
1. First, initiate a Server by following command.
```
java Server 5050
```
2. Then open a new Command Prompt or Terminal and execute following command.
```
java Client localhost 5050
```
3. Now, you will see the following output on each of the prompts.
4. If you want to run more than 1 client, repeat step 2 as many time as you like. Then you will see the following output on Server prompt indicating different palyers.

#### Version 4
- In Version 4, I have created two threads, one each for *Server* and *Client*.
- The `PingPongServant.java` contains the `main` method. It accepts 3 arguments *host*, *port*, & *otherPort*. This class first checks if there is an active *Server* or not. If there is any active *Server* on either of *port* or *otherPort* then it creates the Client on *otherPort* or *port* respectivly.
   - If there is no active *Server*, then it will initialize the *Server* on *port*.
   
To run the Servant please follow below instructions. Open a new Command Prompt or Terminal then:
```
javac PingPongServant.java
```
5. Then use the following command to connect with ongoing *Server* in step 1.
```
java PingPongServant localhost 5050 5051
```
6. Now repeat the step 2 with a port input of **5051**. This way you will be able to connect to Servant Server running on **5051**.


### Sample Output

Below screen shows the sample output from Version 3 and Version 4.
- On left most terminal, I have started a *Server* on **5050** port.
- Then, on the second from left terminal, I have started a *Client* on **localhost** and **5050** port.
- On the right most terminal, I have started a servant with input **localhost** and **5050** **5051** ports.
  - You can see, the *Client* of the servant has connected to *Server* running on **5050** on the left most terminal.
- Then, on the second from right terminal, I have started a *Client* on **localhost** and **5051** port.
  - You can see, this *Client* has connected to *Server* of servant running on **5051** port.
  - The right most terminal is now acting both as a *Client* and *Server* at the same time.

![Screenshot 2023-02-10 at 11 47 04 AM](https://user-images.githubusercontent.com/105172154/218178026-7ef2d9b7-4708-44f4-b428-f376a79e489a.jpg)
