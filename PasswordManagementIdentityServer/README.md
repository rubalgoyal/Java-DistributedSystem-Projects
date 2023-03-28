# project-2-identity-server-rubalgoyal
project-2-identity-server-rubalgoyal created by GitHub Classroom


## Communication Encryption
In this project I am using a self signed SSL certificate generated from OpenSSl (write here how??). 
#### Server Side
First, we are loading the key into Java application and storing the password of the path into key store using following set of code. [Read this for understanding difference between KeyStore vs TrustStore](https://stackoverflow.com/questions/13997419/difference-between-keystore-and-keymanager-trustmanager)
```
FileInputStream keyPath = new FileInputStream(keyStorePath);
KeyStore keyStore = KeyStore.getInstance("JKS");
keyStore.load(keyPath, keyStorePassword.toCharArray());
```

## Execution
```
make runServer @port=5051 @certPath=certificate.cer @keyPath=identity.pem.pkcs8
```
