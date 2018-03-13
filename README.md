How to run:

You can run from IDE or from terminal. 
From terminal: compile with "javac Class.java" then run with "java Class." If you run after the first compile, you don't need to keep compiling. 

This program has both Client.java and Server.java as well as the KeyValueHandler.java classes. Run the Server class and the Client class. Server.java is set to run on 'localhost' on port '8080.' 

Once you run Client.java, the command line UI will ask for commands "POST key value," "GET key," or "quit" which run the three commands. POST will post the key/value pair onto the server. Get will get the value from the server using the key. Quit will terminate the client.  If the user puts an incorrect command, it will tell the user to input a correct command. The key for GET and POST is case sensitive as well as the value in the POST command. 

This handles multiple clients and can get the same key at the same time which is in the scope of the coding questions. The persist storage is a text file that saves a hashMap to the file and updates every time a post is created which keeps the same keys/values.

ClientServerTest and ClientTest are unit tests. I was not able to run both the Server and Client on the unit tests which might be from the need to run it on different threads. Run Server.java first and then run ClientServerTest.java and then stop the server. Start it up again and then run ClientTest.java which will passs all cases. 

