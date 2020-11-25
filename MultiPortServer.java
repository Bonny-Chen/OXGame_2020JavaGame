//*******************************************************************
//*  Network Programming - Unit 8 Non-blocking Socket               *
//*  Program Name: MultiPortServer                                  *
//*  The program creates a server that registers on multiple ports. *
//*     Usage: java MultiPortServer port1 [port2 port3 ...]       *
//*  2016.02.04                                                     *
//*******************************************************************
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class MultiPortServer
{
	public static boolean inDetect = false;
	public static int inPort = 0;
	public static int in8881 = 0;
	public static int in8880 = 0;
	public static int in8882 = 0;
	public static int round = 0;							// Player 1's Round = 1 ; Player 2's Round = 2;
	public static int[] role = new int[3];				// Saving two player's role

	public static void main(String args[]) throws Exception 
	{
		Selector 	selector = Selector.open();   	// Create a selector
		role[1] = 9;
		role[2] = 9;
    	if (args.length <= 0)
    	{
			System.err.println("Usage: java MultiPortServer port1 [port2 port3 ...]");
			System.exit(1);
		}
		
		// Create non-blocking sockets on each port 
		// and register each socket channel with selector
		for (int i = 0; i < args.length ; i++)
		{
			int port = Integer.parseInt(args[i]);	// we don't handle the format error

			// Create a server socket channel and bind it to the specified port
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false); // Config the channel to non-blocking mode
			ServerSocket ss = ssc.socket();
			ss.bind(new InetSocketAddress(port));

			// Register the socket channel with selector
			// and listen on the SOCKET-ACCEPT event
			SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);			
			
			System.out.println("Listening on " + port + " port...");
		}
		
		// The main loop waiting for events
		while(true)
		{
		    // The following method blocks until 
		    // at least one of the registered events occurs.
			int num = selector.select();
			
			// Returns a Set of the SelectionKey objects 
			// for which events have occurred
			Set selectedKeys = selector.selectedKeys();
			// Organize the elements in set into iterator
			Iterator element = selectedKeys.iterator(); 
			
			while(element.hasNext()) // Handle each event in the set
			{
				// Returns the next element in the iteration.
				SelectionKey key = (SelectionKey)element.next();
				
					if(key.isAcceptable()) // Accept the new connection
					{
						// Return the ServerSocketChannel
						ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
						SocketChannel sc = ssc.accept();
						sc.configureBlocking(false); // Config the channel to non-blocking mode
						
						// Register the new connection with the selector
						// and listen on the SOCKET-READ event
						SelectionKey newKey = sc.register(selector, SelectionKey.OP_READ);
						if(sc.getLocalAddress().toString().equals("/127.0.0.1:8880")){
							System.out.println("Client in port 8880");
							inPort = 8880;
							in8880 = 1;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8881")){
							System.out.println("Client in port 8881");
							inPort = 8881;
							in8881 = 1;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8882")){
							System.out.println("Client in port 8882");
							inPort = 8882;
							in8882 = (in8882 + 1)%2;
							if(in8882 == 0){
								round = 2;			// Set This is Player2's Round
							}else if(in8882 == 1){
								round = 1;			// Set This is Player1's Round
							}
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8883")){
							System.out.println("Client in port 8883");
							inPort = 8883;
							if(in8880 == 1 && in8881 == 1){
								inDetect = true;
							}else{
								inDetect = false;
							}
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8884")){
							System.out.println("Client in port 8884");
							inPort = 8884;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8885")){
							System.out.println("Client in port 8885");
							inPort = 8885;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8886")){
							System.out.println("Client in port 8886");
							inPort = 8886;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8887")){
							System.out.println("Client in port 8887");
							inPort = 8887;
						}
						// System.out.println("Got connection from " + sc);
					}
					else if(key.isReadable())
					{
						// Return the SocketChannel
						SocketChannel sc = (SocketChannel)key.channel();
					    try
					    {
							// Receive message
							ByteBuffer 	b = ByteBuffer.allocate(1000); 
							int			len = sc.read(b);	// read message from sc
							if(len > 0)
							{
								System.out.println("Receive message : "	+ new String(b.array(), 0, len) +
				    			" from " + sc);

								if(inPort == 8884){
									System.out.println("8884in");
									if( new String(b.array(), 0, len).startsWith("0") || new String(b.array(), 0, len).startsWith("1") || new String(b.array(), 0, len).startsWith("2") ){
										// Saving Player 1 's Role in role[1]
										role[1] = Integer.parseInt(new String(b.array(), 0, len));
										System.out.println("role set : "+role);

									}
								} 
								if(inPort == 8885){
									System.out.println("8885in");
									if( new String(b.array(), 0, len).startsWith("0") || new String(b.array(), 0, len).startsWith("1") || new String(b.array(), 0, len).startsWith("2")){
										// Saving Player 1 's Role in role[1]
										role[2] = Integer.parseInt(new String(b.array(), 0, len));
										System.out.println("role set : "+role);

									}
								}
								
								// Send message to client
								String data = "Server hello!! (" + sc + ")";
								if(inPort == 8883){
									data = Boolean.toString(inDetect);		// Check two Client are online
								}
								if(inPort == 8880){
									data = "1";				// Player 1
								}else if(inPort == 8881){
									data = "2";				// Player 2
								}
								if(inPort == 8886){
									data = Integer.toString(role[1]);			// Get Player'1 role
								}
								if(inPort == 8887){
									data = Integer.toString(role[2]);			// Get Player'2 role
								}
								if(inPort == 8882){
									data = Integer.toString(round);
								}
								System.out.println("Reply to Client : "+data);
								ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());
								sc.write(buffer);
							}
						}
						catch(IOException e)
						{
							System.out.println("Connection reset by peer :" + sc);
							sc.close();
						}
					}
				
				// Remove the element from the iteration
				element.remove();
			} // end of handling each event
		} // end of main loop
	}
}
