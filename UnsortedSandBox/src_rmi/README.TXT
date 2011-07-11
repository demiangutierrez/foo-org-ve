1.- Let eclipse compile the files
2.- Go to wherever the .class files are
3.- use "rmic" to create stubs:
	> rmic HelloWorldServer
	
	this will create HelloWorldServer_Stub.class
4.- Run the registry
	> rmiregistry
	(the process will block and wait for connections)
5.- Run the server:
	> java HelloWorldServer
6.- Run the client:
	> java HelloWorldClient

If all goes OK, you should see in the server console:

HelloWorld!

and in the client:

HelloWorld from server!

