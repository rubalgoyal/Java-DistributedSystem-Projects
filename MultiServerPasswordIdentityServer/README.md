# Programming Project-2 Identity Server with GRPC
Rubal Goyal (Graduate student, Sp23 - CS 555 - Distributed Systems )<br>
Below are my key learnings from this project:
1. Encrypted communication between server and client. I learned how organizations might be authenticating the requests and preventing any cybersecurity attackes.
2. Password hashing and salting. I didn't know how passwords are stored into the databased and encrypting it.
3. Working with NoSQL database such as Redis and how we can store Key-Value data. Also, how we can store an object into databases and deserialize it using JSON.


## Implemetation <br>
I have implemented the GRPC for client server communication, programmed in Java language and Redis database to store the information of users.
The implementation of the code is below:-
- `IdentityServer` repersents GRPC server provides user authentication functionality. It listens to a specified port and requires SSL/TLS encryption .This class do following things:-
  - Implements automatic redis server start at the default port (6379) whenever the server is started and also added a shutdown hook which will terminate the redis server whenever Identity Server is killed.
  - The server creates an SSL Context and add it to the Netty Server builder and also adds the instance of service class `UserAuthenticationImpl()`.
  
- `UserAuthenticationImpl` To run the server I need to assign to server a service(UserAuthenticationServiceGRPC) in which server can run. This is an instance of service contains implemnetation of the methods which are present in .proto file. 
  - I used `ConcurrentHashMap` to make the Server thread safe. All the Create/Modify operations uses this data structure to interact with the Redis databased which ensure every transaction is thread safe.
  - __UserId Logic__ I am assigning integer as the user id starting from 1, the last user user id generated is stored in redis database with key as `maxUserId`. Everytime a new user is created the value of this key incremented by 1, if any user is deleted then corrosponding user id will be removed from the database.(This logic is available in IdentityUtil class)
- `UserModel` This class includes a default and parameterized constructor annotated with `@JsonCreator` and `@JsonProperty` annotation to serialize and deserialize JSON data to and from the class. 
are storing the Json format of this class into Redis database.
- `IdentityUtil` This is util class which contains all the helpful methods such as `bulkInsert` , `isUserExist`, `returnUser` etc. which help interacting with redis server. This methods are called in ServiceImplemnetation class.
- `Client` repersents GRPC client which interacts with UserAuthenticationService.The class has several methods that provide functionality for creating, modifying, and deleting users, looking up user information, and listing user IDs and login names. The class uses io.grpc and io.grpc.netty packages for the gRPC communication and takes in a port number, address, and certificate file path as constructor parameters.
  - The start() method sets up a secure channel using the NettyChannelBuilder and GrpcSslContexts classes from the io.grpc.netty package.
  - The run() method reads user input from the command line and calls the appropriate method based on the input.
  
 #### Redis Server Persistence
  To make server persistence I am saving all the data in backgroud by using `jedis.bgsave` in every transaction before closing the jedis connection as shown in below piece of code.<br>
  ```
  public static void bulkInsert(ConcurrentHashMap<String, UserModel> inSessionUsers, boolean isNewUser){

        try {
            Jedis jedis = new Jedis(ADDRESS, JEDIS_PORT);
            try {
                ObjectMapper mapper = new ObjectMapper();
                inSessionUsers.forEach((key, value) -> {
                            try {
                                String valueJson = mapper.writeValueAsString(value);
                                jedis.set(key, valueJson);
                                // Increase the max user id key in the db
                                if(isNewUser)
                                    jedis.set(MAX_USER_ID, String.valueOf(value.getUserId() + 1));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
            } finally {
                jedis.bgsave();
                jedis.close();
            }
        } catch (JedisConnectionException e) {
            System.out.println(e);
        }
    }
 ```

### Dependecies
I have used following dependencies in this project. All are added to `build.gradle`
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
    // https://mvnrepository.com/artifact/redis.clients/jedis
    implementation group: 'redis.clients', name: 'jedis', version: '4.3.1'


    implementation 'commons-cli:commons-cli:1.5.0'
    implementation 'commons-codec:commons-codec:1.15'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.13.0'


    compileOnly("javax.annotation:javax.annotation-api:1.3.2")

}
```

# Instructions for execution

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
make runServer @port=5051 @certPath=certificate.cer @keyPath=mykey.pem.pkcs8
```



## Client Execution
1. Please open Windows Command Prompt(cmd) or Terminal on Mac.
2. Change the directory to `../IdentityServer/`.
3. To run Client: Here in @certPath you may pass the path your certificate. Below is an example to show how to run client.

```
make runClient @address=localhost @port=5051 @certPath=certificate.cer
```
To run multiple Clients, you may repeat steps from 1 to 3.

## Sample Output-Testing
I started a server on 1 Terminal and started 2 clients on 2 different terminals at port=5051, and passed the certificate and key as certificate.cer and mykey.pem.pkcs8 respectively. Below is the output of testing different RPC.

<img width="1437" alt="Screenshot 2023-04-01 at 12 43 44 PM" src="https://user-images.githubusercontent.com/105172154/229309256-bfc418d6-a780-446d-81e2-8e3e1588188a.png">

### Testing with different SSL Certificate
I generated a new certificate `test.cer` and passed it to the client. When I tried making call to any of the RPC, the server fails to authenticate the client.
<img width="1440" alt="Screenshot 2023-04-01 at 1 02 26 PM" src="https://user-images.githubusercontent.com/105172154/229309741-8865b717-da62-4e6c-8b2c-c16c609bf977.png">

## Demonstration Video
