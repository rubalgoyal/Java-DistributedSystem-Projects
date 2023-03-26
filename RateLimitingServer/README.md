# Assignment -2, Rubal Goyal


In this assignment, I have implemented the '_Limiting number of requests processed by a TCP server within a specified time interval_'. The server responds with the milliseconds that have elapsed since the server started.

## Project Structure
The project is inclueded in the folder `RateLimiting`. This folder contains the below sturucture:<br>
- `src`<br>
|<br>
|- `Client.java` This class implements the Client side code. In this class, I implemented the `run()` method to execute the `sendRequest()` asynchornously. <br>
|<br>
|- `RateLimitedServer.java` This class builds the TCP server. In this class, the field _requests_ holds the requests recieved from the client and then another thread runs the requests one by one from the BlockingQueue _requests_. Following is the psuedo code implementation of Server side request processing in method `buildServer()` 
```
while (true) {
     new Thread(
        if reqTimeInterval is over:
          reset reqTimeInterval
          reset reqProcessed
          sleep(timeInterval)
        if reqProcessed > reqLimit:
          sleep(remaining interval time + 1 sec)
          reset reqTimeInterval
          reset reqProcessed
        process request/response
        reqProcessed++
     ).start()
    
    add requests from client to Queue requests
}
```

### Client Explanation
- Connect to Server using _ADDRESS & PORT_
- Send the _async_ requests to the server as follow in the for loop:
  - The maximum number of requests sent to the server are limited by *NUM_REQUEST*
  - After each request sent to the server, the _async_ implementation is delayed by _DELAY_ field (millisecond).
    - i.e. if *NUM_REQUEST* = 15 &  _DELAY_ = 1000 then the client will send a request after 1 second time gap. So, all 15 requests will be sent in 15 seconds.

## Instruction to run

Open your Terminal on mac or Command Prompt on windows and then follow below instructions.

- First, build the Server and Client using following commands
```
javac RateLimitedServer.java 
javac Client.java 
```
- Then, first start server by using below example command. Here _5050_ is the port, _10_ is the maximum requests server can process in time interval _10000_ milliseconds.
```
java RateLimitedServer 5050 10 10000
```
- Then, run the clien using below example command. Here client connects to server running on _localhost_ address at _5050_ port. Client will send _11_ requests with a delay of _1_ milliseconds
```
java Client localhost 5050 11 1
```

## Testing
![Screenshot 2023-03-17 at 11 21 06 PM](https://user-images.githubusercontent.com/105172154/226086495-b74a3c57-cdb3-4bac-86d5-72abb53fbbd8.png)

