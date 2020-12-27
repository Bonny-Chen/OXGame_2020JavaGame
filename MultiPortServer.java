//***************************************************************************************************
//*  Network Programming - Final Project (OXGame)                        							*
//*  Program Name: MultiPortServer.java                                             				*
//*  Run Program : 																					*
//*  java MultiPortServer 8880 8881 8882 8883 8884 8885 8886 8887 8888 8889 8890 8891 8892          *
//*  The program connects to BR and receive message.					 							*
//*  The program join an multicastgroup and receive message from there.  							*
//*  2020.11.29                                                          							*
//***************************************************************************************************
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
	public static int in8882 = 1;
	public static int round = 0;						// Player 1's Round = 1 ; Player 2's Round = 2;
	public static int[] role = new int[3];				// Saving two player's role
	public static int[] pos = new int [3];				// Saving two player's role position
	public static int[] grid = new int [9];				// Saving Grid clicks with player 1 or player 2
	public static int[] virtualGrid = new int [9];		// Recording Player Turn
	public static int checkGrid = 10;					// Receive the Grid clicked By Client and Check Whether the grid is avaliable to click
	public static int setGrid = 100;
	public static int isclicked = 0;
	public static int new_winner = 0;
	public static int loopbreak = 0;

	public static void main(String args[]) throws Exception 
	{
		Selector 	selector = Selector.open();   	// Create a selector
		role[1] = 9;
		role[2] = 9;
		pos[1] = 500;
		pos[2] = 500;
		pos[0] = 0;
		for(int i = 0 ; i < 9 ; i++){
			grid[i] = 0;
		}
     	// if (args.length <= 0)
    	// {
		// 	System.err.println("Usage: java MultiPortServer port1 [port2 port3 ...]");
		// 	System.exit(1);
		// }
		
		// Create non-blocking sockets on each port 
		// and register each socket channel with selector
		for (int i = 8880; i < 8898 ; i++)
		{ 
			int port=0;
			port = i;
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ServerSocket ss = ssc.socket();
			if(i==8893)port=2000;
			if(i==8894)port=2001;
			if(i==8895)port=2002;
			if(i==8896)port=4041;
			if(i==8897)port=4042;
			
			ss.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), port));
			SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);   
			System.out.println("Listening on " + port + " port...");
		}
		
		// The main loop waiting for events
		while(true)
		{
			if(loopbreak==1)break;
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
						if(sc.getLocalAddress().toString().equals("/127.0.0.1:8880") && in8880 == 0){
							System.out.println("Client in port 8880");
							inPort = 8880;
							in8880 = 1;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8881") && in8881 == 0){
							System.out.println("Client in port 8881");
							inPort = 8881;
							in8881 = 1;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8882")){
							// Decide the round 
							System.out.println("Client in port 8882");
							inPort = 8882;
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
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8888")){
							// Reset Grid[i] to 0
							System.out.println("Client in port 8888");
							inPort = 8888;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8889")){
							// Send and receive isclicked value
							System.out.println("Client in port 8889");
							inPort = 8889;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8890")){
							// Set winner in RedoGrid()
							System.out.println("Client in port 8890");
							inPort = 8890;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8891")){
							// Send winner in RedoGrid()
							System.out.println("Client in port 8891");
							inPort = 8891;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8892")){
							// Send winner in SpaceFlagPage
							System.out.println("Client in port 8892");
							inPort = 8892;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:4041")){
							// Detect player1 offline
							System.out.println("Play1 offline");
							inPort = 4041;
							// Reset all setting 
							round = 1;
							in8880 = 0;		
							role[1] = 9;
							pos[1] = 500;
							pos[0] = 0;
							isclicked = 0;
							new_winner = 0;
							setGrid = 100;
							checkGrid = 10;
							for(int i = 0 ; i < 9 ; i++){
								grid[i] = 0;
							}
							inDetect = false;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:4042")){
							// Send winner in SpaceFlagPage
							System.out.println("Play2 offline");
							inPort = 4042;
							// Reset all setting
							round = 1;
							in8881 = 0;
							role[2] = 9;
							pos[2] = 500;
							pos[0] = 0;
							isclicked = 0;
							new_winner = 0;
							setGrid = 100;
							checkGrid = 10;
							for(int i = 0 ; i < 9 ; i++){
								grid[i] = 0;
							}
							inDetect = false;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:2000")){
							// Send grid status "who clicked this grid" to Client
							System.out.println("Client in port 2000");
							inPort = 2000;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:2001")){
							// Client set grid (who click the grid)
							System.out.println("Client in port 2001");
							inPort = 2001;
						}else if(sc.getLocalAddress().toString().equals("/127.0.0.1:2002")){
							// Send Current Turn to Client
							System.out.println("Client in port 2002");
							inPort = 2002;
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
								}else if(inPort == 8882){
									System.out.println("8882in");
									for(int i = 0 ; i < 9 ; i++){
										if(virtualGrid[i] == 1){
											round = 2;
											virtualGrid[i]=9;				// Prevent "break" 9 is represent done with player 1
											System.out.println("Round : "+round);
											break;
										}else if(virtualGrid[i] == 2){
											round = 1;
											virtualGrid[i]=8;				// Prevent "break" 8 is represent done with player 2
											System.out.println("Round : "+round);
											break;
										}
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
								
								if(inPort == 8888){
									if (new String(b.array(), 0, len).startsWith("0") 
										|| new String(b.array(), 0, len).startsWith("1")
										|| new String(b.array(), 0, len).startsWith("2")
										|| new String(b.array(), 0, len).startsWith("3")
										|| new String(b.array(), 0, len).startsWith("4")
										|| new String(b.array(), 0, len).startsWith("5")
										|| new String(b.array(), 0, len).startsWith("6")
										|| new String(b.array(), 0, len).startsWith("7")
										|| new String(b.array(), 0, len).startsWith("8")){
											grid[Integer.parseInt(new String(b.array(), 0, len))] = 0;
										}
								}else if(inPort == 8889){
									if(new String(b.array(), 0, len).startsWith("0") || new String(b.array(), 0, len).startsWith("1"))
										isclicked = Integer.parseInt(new String(b.array(), 0, len));
								}else if(inPort == 8890 ){
									if (new String(b.array(), 0, len).startsWith("1") || new String(b.array(), 0, len).startsWith("2") || new String(b.array(), 0, len).startsWith("3")){
											new_winner = Integer.parseInt(new String(b.array(), 0, len));
										}
								}
								if(inPort == 2000){
									if (new String(b.array(), 0, len).startsWith("0") 
										|| new String(b.array(), 0, len).startsWith("1")
										|| new String(b.array(), 0, len).startsWith("2")
										|| new String(b.array(), 0, len).startsWith("3")
										|| new String(b.array(), 0, len).startsWith("4")
										|| new String(b.array(), 0, len).startsWith("5")
										|| new String(b.array(), 0, len).startsWith("6")
										|| new String(b.array(), 0, len).startsWith("7")
										|| new String(b.array(), 0, len).startsWith("8")){
											checkGrid = Integer.parseInt(new String(b.array(), 0, len));
										}
								}else if(inPort == 2001 && !new String(b.array(), 0, len).startsWith("Client") && !new String(b.array(), 0, len).startsWith("Get")){
									setGrid = Integer.parseInt(new String(b.array(), 0, len));
									if(setGrid - 10 >= 0 && setGrid - 10 <= 8){
										grid[setGrid-10] = 10;
										virtualGrid[setGrid-10] = 1;
										System.out.println("Set Gird["+(setGrid-10)+"] = 1");
									}else if(setGrid - 20 >= 0 && setGrid - 20 <= 8){
										grid[setGrid-20] = 20;
										virtualGrid[setGrid-20] = 2;
										System.out.println("Set Gird["+(setGrid-20)+"] = 2");
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
								if (inPort == 8889){
									data = Integer.toString(isclicked);
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
								if(inPort == 8891){
									if( new String(b.array(), 0, len).startsWith("Get") ){
										data = Integer.toString(new_winner);
									}
								}
								if(inPort == 8892){
									data = Integer.toString(pos[0]);
								}
								if(inPort == 2000 && checkGrid <= 8  && checkGrid >= 0 && (virtualGrid[checkGrid] == 8 || virtualGrid[checkGrid] == 9) ){
									data = Integer.toString(grid[checkGrid]);
								}else if(inPort == 2002){
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
							System.exit(1);
						}
					}
				// Remove the element from the iteration
				element.remove();
			} // end of handling each event
		} // end of main loop
	}
}