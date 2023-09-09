# Programming Project 3 Replicated Identity Server
**Team Members: <br>
Rubal Goyal (Graduate student, Sp23 - CS 555 - Distributed Systems )**<br>
**Brayden Thompson (Under-graduate student, Sp23 - CS 455 - Distributed Systems )**

#### Design document
`CS_455_555_Project_3_Design_Paper.pdf` is our proposed design for this project

Video Link -> https://youtu.be/DxU2OLj4X6U


# Implemenation Explanation
## Definitions

- __Master Server__: We were unable to implement a good way of creating a DNS servers which will host all the information about the Nodes. In our design document, we talked about 2 different approaches for implementing Coordinator server identification code. DNS option seemed easier and timely to implement however we were not able to gather all the sources to implement that. Therefore, created another server from class `MasterServer' which acts as the DNS server for this project. It has following main tasks:
  - Create a cluster of Server. It uses the 'maxNodes' parameter passed. 
  - Then it starts each of these Servers in a new thread. After that, it starts the election process for Coordinator.
  - It also starts the Redis server in the backend and kills the redis sever once this Master Server is killed.
  - Interatcs with the Client and communicate information about Coordinator so that Client can connect to the Coordinator.
  - This has the list of all active nodes and passes that list to the Nodes.
  - Checks the status of Coordinator and re-elect if the existing coordinator fails.
  - It also enables the feature of adding a new Node and deleting an existing Node.
- __Server(s)/Node(s)/Coordinator__: An instance of the `IdentityServer` class. This server is responsible for read and write operations of user/password creation/modification/show etc for all requests received from the Client. The Server is called as Node as well at some places. Where not specifically mentioned, Node will imply the Child Node. A cluster of Nodes mean we run multiple Servers.
  - __Coordinator__: Among the cluster of Server, a Coordinator is chosen and all the remaining Nodes act as the child of that Coordinator.
  
## Features
- __Consistency__ In our implementation, we have choosen a Strict Consistency model. Below points explain the fundamentals how we are acheieving this.
    1. In our present implementation of code, we assumed the Nodes are running on loacl machine.
    2. We are using Redis Database to store the data. The redis server is started on its default configuration.
    3. We designed the application for Nodes to be using the same redis-server, there every Node has the access to the same data.
    4. Every node has its respective `inSession` users data concurrent hashmap. After every successful write operation, the Node removes the corresponding hashmaps.
    5. We have added a Task Scheduler to run at every 5 mins interval to save the redis data using method `IdentityUtil.jedisSaveTaskScheduler();`.
    6. We also have added a shutdown hook to save any in-memory data to redis database before shutting down the server. Thus this option and above point ensure the strict consistency. 

  __Coordinator__ 
  - In order to implement strict consistency, we have also chosen to have the single coordinator node manage all client traffic. It works as follows:
      - When the coordinator recieves a write request from the client, a read / write lock will be placed on that entry in the database.
      - The coordinator then propogates that write out to the child nodes, which will perform the write.
      - After every child node completes the write, the read / write lock is removed from that entry, and the coordinator returns to the client. 
      - While the lock is in place, any reads or writes that want to access that entry will be blocked. 
   ![Screenshot 2023-04-30 191347](https://user-images.githubusercontent.com/77991576/235387410-ba126c4a-8639-4503-a34c-e536fe673cff.png)
  - By doing this, we can garuntee all of the client-centric consistency models such as eventual consistency, read your writes, writes follow reads, and monotonic writes. Additionally, since we are also blocking reads while a write is in progress, we can also garuntee monotonic reads. 

- __Checkpointing/Snapshoting__ Since, we are implementing strict consistency, it becomes imperative to preseve any data if any of the server is interuppted or stops responding. We acheive this by updating all the insession users, if any exist, on any Node to the coordinator node. We have added a shut down hook as well as server shutdown to take the snapshot and update the in session users to the coordinator. It can be seen from the below output.
  ![Screenshot 2023-04-30 at 4 45 28 PM](https://user-images.githubusercontent.com/105172154/235379428-8dd8b497-c414-4dfa-a673-859b4369a789.png)

- __Elasticity__ Our implementation makes it easier to add or remove a Node. In execution from terminal:
  - __Add Node__ Simply type in `add-node` and our program will add a new Node at PORT = PORT of Max Node + 1. Initially this new Node will act as the Child Node and will be considered in Coordinator election whenever the existing Coordinator crashes or becomes non-responding. Below is the snapshot of such output.
![Screenshot 2023-04-30 at 3 33 51 PM](https://user-images.githubusercontent.com/105172154/235377274-4ca08c71-ec40-4b6e-a96c-d86dfae17952.png)

  - __Remove Node__ Simply type in `SIGKILL` followed by node id like '_SIGKILL 10_' if 10 is the node id. Our program will remove the Node if it exists otherwise will show available node to pick from and kill. Depending upon the circumstance, our program will start re-election process, we also update the available nodes in the entire cluster. Below is the snapshot of such output.
![Screenshot 2023-04-30 at 3 14 08 PM](https://user-images.githubusercontent.com/105172154/235376562-35267aac-a6d5-40c5-adf0-3f31649c8355.png)

- __Fault Tolerance__ Our implementation takes care of if there is any failure of the nodes specifically for Coordinator server. We have added a Task Scheduler to check the health of Coordinator at a fixed interval. In this implementation we have limited the task schedluer to Coordinator only and it runs at every 15 minuets. Again this time interval can converted to user input, however we have hard code it in this implementation. If the Coordinator is dead or not working, it will initiate the re-election process.
![Screenshot 2023-04-30 at 3 21 26 PM](https://user-images.githubusercontent.com/105172154/235376793-cc03a1d4-67e4-4da2-bf56-1004c5edeaaf.png)

- __Scalebility__ As explained in __Elasticity__, it is easier to add any number of nodes in our this implementation and also easy to remove any existing node. Therefore, our this implementation is scalable in terms of number of available Nodes in the cluster to perform Read/Write. However, to ensure Strict Consistency, we are routing all the requests from Client through a Coordinator, therefore the overall throughput will not be scaled much.

- __Performance__ Similar to Scalebility about the throughput from the cluster of Nodes, our this implementation doesn't offer much for the performance. In out implementation Coordinator is a bottleneck. However we have an idea to improve both Scalebility and Perforamce. However, that idea required a considerable efforts, that is why we haven't implemented that idea however list as below:

  'We can create multiple Coordinator Nodes and then route different Clients through different Coordinators. One way of doing that is through HASH value of username request. We can add a certain criteria which will ensure certain HASH values are routed through Coordinator 1, and others through Coordinator 2 and so on.'
    
##
Below is the diagram which depicts the implementation of the project followed be code implementation explanation.
![IMG_F134F7AEF4A6-1](https://user-images.githubusercontent.com/105172154/235329932-2ef9aa2a-744f-4b0b-95c2-dbcd50ff4dc2.jpeg)

  
## Protobufs
- `ClientCommunication.proto`: Impelements the services for communication between Client and Master server. Through this service the Client identifies which node is the Coordinator node and it needs to connect to. 
- `Identity.proto`: Implements the services for main funcationalities related to read & writed. Using thsi proto the client communicates with Coordinator. It defines two main messages, either a Write request/response, or a Read request/response. Each of the more granular commands are contained within these two, and the writes are also passed between the coordinator and child nodes. 
- `ServerCommunication.proto`: Implements the services for communication between Nodes. Using this service, the Nodes communicate with each other to check if the Nodes are running and electing the Coordinator.

## Server Side Code
- `MasterServer.java`: It creates the cluster of Nodes and runs each of the node in a new thread using method `createClusterOfNodes()` and then starts the election for a Coordinator and updates the list of alive Nodes to each of the Nodes using `startElection()`. All the Nodes are started on port 50511 ownwards. We can add a new Node in a new thread using `addNodes()`. The method `checkCoordinatorServerStatusRelection()` is a task scheduler which runs at fixed interval of time and checks the health of the Coordinator and initiates the `election()` if the existing Coordinator fails.

- `IdentityServer.java`: This is the main server class in our system. It is designated either as the coordinator node, or as a child node to be written to. Most of the server's functionality is contained in the `UserAuthenticationImpl.java` class. If it is the coordninator, then when a write is recieved, it will put a lock on that entry and pass the write on to the child nodes. If it is a child node, it just recieves the write and performs it on the redis database. On a read, the coordinator will check if a lock is in place, and when there is not, it will just return the requested info. 


# Instructions for execution

We are using Redis and tried to include Docker Image. None of us have ever worked with Docker before and couldn't find any example about how to use Docker with gradle build, therefore, we are unable to include Docker Image for Redis server.

My project uses redis database for data storage therefore, please ensure your machine has redis installed. You can follow [this link](https://redis.io/docs/getting-started/) for redis installation directions. Also, I used default configurations for redis server and it is started on default port 6379.

## SSL Certificate Generation
To generate the self signed certificate for Netty Server, I used the same instructions as shared in the project requirement PDF and re-written here below:
```
openssl genrsa -out mykey.pem
openssl pkcs8 -topk8 -nocrypt -in mykey.pem -out mykey.pem.pkcs8
openssl req -new -x509 -key mykey.pem -out certificate.cer -days 1825 \
-addext "subjectAltName = DNS:localhost"
```

## Server Execution 

1. Please open Windows Command Prompt(cmd) or Terminal on Mac.
2. Change the directory to `../IdentityServer/`.
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
5. To run Server: Here in @certPath and @keyPath you may pass the path your certificate and keys generated. Below is an example to show how to run server
```
make runServer @port=5051 @address=localhost @certPath=certificate.cer @keyPath=mykey.pem.pkcs8 @maxNodes=4
```
## Client Execution
1. Please open Windows Command Prompt(cmd) or Terminal on Mac.
2. Change the directory to `../IdentityServer/`.
3. To run Client: Here in @certPath you may pass the path your certificate. Below is an example to show how to run client.

```
make runClient @address=localhost @port=5051 @certPath=certificate.cer
```
To run multiple Clients, you may repeat steps from 1 to 3.

__Note__ If Coordinator is changed, then client needs to be restatred.

## Sample Output-Testing
### Automated Tests
We have created some unit test suits which run few test methods for testing.
__checkValidCertificateException__ tests whether an exception is thrown when an invalid certificate file is provided for secure communication.__checkUserExists__ simulates client making a request to a known node in the cluster for the address of the coodinator node, It establishes a secure connection using SSL/TLS, retrieves the coordinator's port, and creates a channel to connect to the coordinator node, then checks if user exists in the user authentication service, succeeds if user doesnot exists. __checkPasswordAuthentication__ test is designed to validate the service's password authentication behavior. It expects the response message to indicate that the password entered is incorrect. If the actual response matches the expected error message, the test passes. __testWriteLock__ simulates a client requesting the address of the coordinator node in a cluster, connects with cordinator node and performs a test "write lock. The test verifies if the user can be modified by concurrent calls. If at least one of the concurrent calls successfully modifies the user, the test passes. __testReadLock__ simulates client making a request to a known node in the cluster for the address of the coodinator node onnects with cordinator node and performs a test "read lock.". The test verifies if the lookup operation blocks on the server side as expected. 




This is a sample testing ouput showing the Client is able to first get Coordinator node from a central server [mostly acting as a DNS server] then client connects to the Coordinator Server. Once connected, it shows reading and writing from coordinator server.

Now need to implement the Slave Node impelementations.

<img width="1440" alt="Screenshot 2023-04-25 at 10 13 33 PM" src="https://user-images.githubusercontent.com/105172154/234468781-5fabd4ba-9fbb-480a-b5f4-05eaac4c7545.png">



