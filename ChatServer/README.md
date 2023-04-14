# Programming Project 1 Chat Server with GRPC
**Team Members: <br>
Rubal Goyal (Graduate student, Sp23 - CS 555 - Distributed Systems )**<br>
**Brett Weaver (Under-graduate student, Sp23 - CS 455 - Distributed Systems )**
### Team Contributions:
- Rubal Goyal: Server and Client Development, Design, Readme, Requirement Discussions, Video Recording
- Brett Weaver: Server Development support, Makefile, Gradle build, Discussions, Readme, Requirement Discussions, Video Recording

## Problem Statement
In this project, we will build a simplified chat server that can support multiple clients over the Internet. We are (sort of) going to mimic popular chat apps like Slack and Discord.

Here is a simple list of commands and behaviors I want your chat server to implement:
command description<br>
- [ ] nick <nickname> Pick a nickname for the user
- [ ] list List channels and number of users
- [ ] join <channel> Join a channel
- [ ] leave <channel> Leave a channel
- [ ] post <channel> <content> Post a message to a channel

### Requirements
- Develop a simple text-based client to demonstrate the functionality of you chat server.
- A client must have picked a nickname to join a channel. They don’t need a nickname to list channels. If they update their nickname while using the software, it should be reflected in their posts received
by other clients.
- A client must have joined a channel to receive or send messages using the channel.
- The number of users displayed to the client must be the number of active users who are members of the channel.
- A message sent to a channel must be delivered to every client that is part of the channel.
- If a client’s connection fails during the interaction and it doesn’t leave the channel gracefully, it must still be removed from the list of users in the channel.
- The server must support mutiple concurrent users.
- Add configurable log levels. There are many Java/Kotlin logging libraries to choose from. Limit the number of JVM threads being started here. If you choose to use coroutines in your solution, the coroutine runtime will do this for you. If you choose a coroutine solution, you must not use
blocking calls.
- Clean up unused connections and sockets. Not doing so creates resources leaks that may cause longrunning
servers to crash.
- Shut down the server gracefully after it has been idle for a period of time. You can decide what time
period and what "idle" means (within reason). Make sure to document both in your README.
- Add a shutdown hook to gracefully stop the server if it receives an interrupt signal.
- Persistance is not necessary. It’s perfectly fine to comepletely restart if the server is killed.
- Use either an object-based protocol or a RPC-based protocol.
- Extra Credit: Implement your client program in a non-JVM language of your choice.

## Project Architecture
We have implemented the GRPC for Server-Client communication. We have programmed the Server side code in `Java` and Client side code in `Python` languages. The structure of the code is as below:
  - `GrpcChatServer`: GRPC Implementation folder containing both Server and Client.<br />
    |<br />
    |--`ChatClient`: contains the Python code for Client and Proto file.<br />
    |<br />
    |--`ChatServer`: contains the Java code for Server, Proto file, Gradle and related build settings and dependency declarations.<br />

Below image explains the implementation of Server and Client. For each server and client individual implementation explanation, you may refer to readme.md file in each of the individual repositories.

![IMG_9402](https://user-images.githubusercontent.com/105172154/224414938-3a8a676f-5904-4974-a627-5310dbc1e5b6.png)

## Dependencies

We have included all the dependecies required in `build.gradle` as below. You wouldn't need to make any changes in the `build.gradle` to run the project from the terminal.

```
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'

    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java
    implementation group: 'com.google.protobuf', name: 'protobuf-java', version: '4.0.0-rc-2'
    implementation group: 'com.google.protobuf', name: 'protobuf-java-util', version: '4.0.0-rc-2'

    // https://mvnrepository.com/artifact/io.grpc/grpc-all
    implementation group: 'io.grpc', name: 'grpc-all', version: '1.53.0'
    implementation 'javax.annotation:javax.annotation-api:1.3.2'
    implementation 'io.grpc:grpc-netty-shaded:1.53.0'
    implementation 'io.grpc:grpc-protobuf:1.53.0'
    implementation 'io.grpc:grpc-stub:1.53.0'

    implementation 'commons-cli:commons-cli:1.5.0'
    implementation 'commons-codec:commons-codec:1.15'

    compileOnly("javax.annotation:javax.annotation-api:1.3.2")

}
```

## Instructions to execute code
### Server Execution

1. Please open Windows Command Prompt(cmd) or Terminal on Mac.
2. Change the directory to `../ChatServer/`.
3. Remove objects and executable files:
```
make clean
```
4. Create objects and executable files to run program.
```
make build
```
or to both clean and build, you may use below instructions as well
```
make clean build
```
5. To run Server:
```
java -cp build/libs/ChatServer-1.0-SNAPSHOT-all.jar ChatServer
```



### Client Execution
1. Please open Windows Command Prompt(cmd) or Terminal on Mac.
2. Change the directory to `../ChatClient/`.
3. Then depending upon your python installations you may need to run one of the following instructions:<br />
  3.1 In below instructions, `example` is the clientId. You may enter any clientId of your choice.<br />
  3.2 `5050` is the port number. Please ensure to pass the same port as used in Server. Otherwise the client will not connect to the Server.

```
python3 ChatClient.py example 5050
```
or 
```
python ChatClient.py example 5050
```
To run multiple Clients, you may repeat steps from 1 to 3.

### Sample Output-Testing

![Screenshot 2023-03-10 at 1 28 03 PM](https://user-images.githubusercontent.com/105172154/224422140-68c60647-b75b-4dcd-9cc0-40db62269665.png)


## Demonstration Video

https://youtu.be/TJabOtnGjdI
