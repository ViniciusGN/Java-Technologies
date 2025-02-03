# Java-Technologies
### Chat Application using RMI - TP1
Vinicius Nascimento - ENSICAEN 2025 2eme annee Ing. Infor.

---

### Overview
This project is part of TP1 for the Java Technologies course, focusing on client-server communication using TCP (Part I) and RMI with star topology for a chat application (Part II).

---

## Project Structure
/Java-Technologies <br>
├── /src <br>
│    ├── /client-server   # Part I: Client-Server using TCP <br>
│    │   ├── Client.java <br>
│    │   └── Server.java <br>
│    ├── /rmi-server      # Part II.A: RMI Tests <br>
│    │   ├── InterfaceHourServer.java <br>
│    │   ├── ServerRMI.java       <br>
│    ├── /interfaces                # Part II.B: Chat Application using RMI (Star Topology) <br>
│    │   ├── InterfaceChatServer.java <br>
│    │   ├── InterfaceChatClient.java <br>
│    │   └── InterfaceHourServer.java <br>
│    ├── /server <br>
│    │   ├── ChatServer.java <br>
│    │   ├── HourServer.java <br>
│    ├── /client <br>
│    │      └── ChatClient.java <br>
│    ├── /model <br>
│    │      └── Message.java <br>
└── /bin              # Compiled .class files <br>

---

## How to Run the Project

### Part I - TCP Client-Server (Folder: `client-server`)
1. **Compile the code:**
```
javac src/client-server/Client.java src/client-server/Server.java -d bin
```

2. **Run the `server`:**
```
java -cp bin Server
```

3. **Run the `client` (in another terminal):**
```
java -cp bin Client
```

---

### Part II.A - RMI Tests (Folder: `rmi-server`)
1. **Compile the code:**
```
javac -d bin src/interfaces/*.java src/rmi-server/*.java
```

2. **Start the RMI Registry:**
- Windows:
```
cd bin
start rmiregistry 1099
```
- Linux
```
cd bin
rmiregistry 1099 &
```

3. **Run the RMI Server:**
```
java -cp bin ServerRMI
```
or, If the code uses a package (e.g., `package rmi-server`; in `ServerRMI.java`):
```
java -cp bin rmi-server.ServerRMI
```

### Part II.B - RMI Chat Application (Star Topology)
1. **Compile the code:**
```
javac -d bin src/interfaces/*.java src/server/*.java src/client/*.java src/model/*.java
```

2. **Start the RMI Registry:**
- Windows:
```
cd bin
start rmiregistry 1099
```
- Linux
```
cd bin
rmiregistry -J-Djava.class.path=. 1099 &
```

3. **Run the `HourServer`:**
```
java -cp bin HourServer
```
with the Expected output:
```
HourServer is running and ready to provide the current time...
```

4. **Run the `ChatServer`:**
```
java -cp bin ChatServer
```
with the Expected output:
```
ChatServer is running and registered on rmi://localhost:1099/ChatServer
```

5. **Run the `ChatClient` (in another terminal):**
```
java -cp bin ChatClient
```
with the Expected output as example:
```
Enter your username: VINI
[USR: VINI - ServerHour: 20:01 ClientHour: 22:05]> Hello everyone!
[SRV: SERVER - ServerHour: 22:06]> ANNE has joined the chat
```

6. **To Exit the Chat:**

Type `/exit` to disconnect from the chat gracefully.

### To clean all `*.class` objects
```
rm -rf bin/*
```

### To kill the process rmiregistry
If the RMI registry is running in the background and you need to stop it:
```
pkill -f rmiregistry
```

---

## Proposed Question: Why Does the Message Class Implement `Serializable`?
The `Message` class implements the `Serializable` interface because Java RMI (Remote Method Invocation) requires objects to be serializable when transmitting them over the network. Serialization allows objects to be converted into a stream of bytes so they can be sent from one Java Virtual Machine (JVM) to another and then reconstructed (deserialized) on the receiving side. Without serialization, the object data couldn't be transferred via RMI.
