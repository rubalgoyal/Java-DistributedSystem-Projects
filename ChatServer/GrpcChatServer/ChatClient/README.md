**Code Implementation**
<br>
We have created two classes in java ChatServer.java and CharServerImpl.java and proto file Chat.proto on Server side. ChatClient.py, client implemetation is done in python, one proto file Chat.proto and two stub files chat_pb2.py and chat_pb2_grpc.py which are generated from proto file.
<br>
- `Chat.proto` : This file contains all the services that are used to generate stub. We have declared a service ChatService, which consist of collection of services. We have implemented six rpc which takes request of type message and returns a message. 

&emsp; &emsp; Once we are done with the creation of the .proto file, we need to generate the stubs. For that, we will execute the below command:-
 ```
 python3 -m grpc_tools.protoc --proto_path=. ./chat.proto --python_out=. --grpc_python_out=.
```
 &emsp; &emsp; The above command will generate two stubs `chat_pb2.py` and `chat_pb2_grpc.py`, which are used in the client implementation.
  
- `ChatClient.py` : We created the class of client `ChatClient` which implements and runs to asynchornous methods `run_user_input` and `get_message_from_server` to which the stub is passed that is created in another asynchronous method `run`.
