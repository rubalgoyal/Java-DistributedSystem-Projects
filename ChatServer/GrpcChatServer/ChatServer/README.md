**Code Implementation**
<br>
We have created two classes in java ChatServer.java and CharServerImpl.java and proto file Chat.proto on Server side. ChatClient.py, client implemetation is done in python, one proto file Chat.proto and two stub files chat_pb2.py and chat_pb2_grpc.py which are generated from proto file.
<br>
- `Chat.proto` : This file contains all the services that are used to generate stub. We have declared a service ChatService, which consist of collection of services. We have implemented six rpc which takes request of type message and returns a message. 

- `ChatServer` : To start grpc server we need to specify the port number. To start the server we have defined the service ChatServer. The server will stop either we receive termination from user or client will not join the server in 30 seconds.

- `ChatServerImpl` : To run the server we need to assign the server a service (ChatServerGrpc) in which server can run. ChatServerImpl is an instance of service contains an implementation of the methods/functions which are present in the ".proto" file.<br>
  
