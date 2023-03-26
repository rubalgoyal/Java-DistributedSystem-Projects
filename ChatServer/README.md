# Programming Project 1 Chat Server with GRPC
**Team Members: <br>
Rubal Goyal (Graduate student, Sp23 - CS 555 - Distributed Systems )**<br>
**Brett Weaver (Under-graduate student, Sp23 - CS 455 - Distributed Systems )**
### Team Contributions:
- Rubal Goyal: Server and Client Development, Design, Readme, Requirement Discussions, Video Recording
- Brett Weaver: Server Development support, Makefile, Gradle build, Discussions, Readme, Requirement Discussions, Video Recording


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
To execute the code, you need to have some applications/software installed on your machine. We used following version of JDK and Gradle.
![Screenshot 2023-03-10 at 1 10 03 PM](https://user-images.githubusercontent.com/105172154/224418290-a7445a42-2e00-4730-b826-3efc8d94a7e1.png)


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
