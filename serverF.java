import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class serverF
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
    public static int quitWhile = 0;
    //new
    public static int [] index = new int [9];           // For recording OXGame
    public static int tmp = 100;        
    public static String whowin = "";
    public static int countRound = 0;

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
        //new
        for(int x = 0 ; x < 9 ; x++){
            index[x] = 100;
        }

     	if (args.length <= 0)
    	{
			System.err.println("Usage: java MultiPortServer port1 [port2 port3 ...]");
			System.exit(1);
		}
		
		// Create non-blocking sockets on each port 
		// and register each socket channel with selector
		for (int i = 0; i < 18 ; i++)
		{
			int port = Integer.parseInt(args[i]);	// we don't handle the format error

			
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
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
			if(quitWhile==1)
				break;
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
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8881") && in8881 == 0){
							System.out.println("Client in port 8881");
							inPort = 8881;
							in8881 = 1;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8882")){
							// Decide the round 
							System.out.println("Client in port 8882");
							inPort = 8882;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8883")){
							System.out.println("Client in port 8883");
							inPort = 8883;
							if(in8880 == 1 && in8881 == 1){
								inDetect = true;
                            }
                            else{
								inDetect = false;
							}
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8884")){
							System.out.println("Client in port 8884");
							inPort = 8884;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8885")){
							System.out.println("Client in port 8885");
							inPort = 8885;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8886")){
							System.out.println("Client in port 8886");
							inPort = 8886;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8887")){
							System.out.println("Client in port 8887");
							inPort = 8887;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8888")){
							// Recieve Player1's Role Position in SpaceFlagPage
							System.out.println("Client in port 8888");
							inPort = 8888;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8889")){
							// Recieve Player2's Role Position in SpaceFlagPage
							System.out.println("Client in port 8889");
							inPort = 8889;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8890")){
							// Send Player1's Role Position in SpaceFlagPage
							System.out.println("Client in port 8890");
							inPort = 8890;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8891")){
							// Send Player2's Role Position in SpaceFlagPage
							System.out.println("Client in port 8891");
							inPort = 8891;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8892")){
							// Send winner in SpaceFlagPage
							System.out.println("Client in port 8892");
							inPort = 8892;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:4041")){
							// Detect player1 offline
							System.out.println("Play1 offline");
							inPort = 4041;
							// Reset all setting 
							in8880 = 0;		
							role[1] = 9;
							pos[1] = 500;
							pos[0] = 0;
							for(int i = 0 ; i < 9 ; i++){
								grid[i] = 0;
							}
							inDetect = false;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:4042")){
							// Send winner in SpaceFlagPage
							System.out.println("Play2 offline");
							inPort = 4042;
							// Reset all setting 
							in8881 = 0;
							role[2] = 9;
							pos[2] = 500;
							pos[0] = 0;
							for(int i = 0 ; i < 9 ; i++){
								grid[i] = 0;
							}
							inDetect = false;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:2000")){
							// Send grid status "who clicked this grid" to Client
							System.out.println("Client in port 2000");
							inPort = 2000;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:2001")){
							// Client set grid (who click the grid)
							System.out.println("Client in port 2001");
							inPort = 2001;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:2002")){
							// Send Current Turn to Client
							System.out.println("Client in port 2002");
							inPort = 2002;
                        }
                        //new
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:6668")){
							System.out.println("Client in port 6668");
                            inPort = 6668;
                           // in8888 = 1;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:6669")){
							System.out.println("Client in port 6669");
                            inPort = 6669;
                            //in8889 = 1;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:6666")){
							System.out.println("Client in port 6666");
                            inPort = 6666;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:6667")){
							System.out.println("Client in port 6667");
                            inPort = 6667;
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
                                else if(inPort == 8882){
									System.out.println("8882in");
									for(int i = 0 ; i < 9 ; i++){
										if(virtualGrid[i] == 1){
											round = 2;
											virtualGrid[i]=9;				// Prevent "break" 9 is represent done with player 1
											System.out.println("Round : "+round);
											break;
										}else if(virtualGrid[i] == 2){
											round = 1;
											virtualGrid[i]=8;				// Prevent "break" 9 is represent done with player 2
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
								
								if(inPort == 8888 && !new String(b.array(), 0, len).startsWith("Client")){
									pos[1] = Integer.parseInt(new String(b.array(), 0, len));
									if(pos[1] == 0 && pos[0] == 0){
										pos[0] = 1;
									}
                                }
                                else if(inPort == 8889 && !new String(b.array(), 0, len).startsWith("Client")){
									pos[2] = Integer.parseInt(new String(b.array(), 0, len));
									if(pos[2] == 0 && pos[0] == 0){
										pos[0] = 2;
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
                                }
                                else if(inPort == 2001 && !new String(b.array(), 0, len).startsWith("Client")){
									setGrid = Integer.parseInt(new String(b.array(), 0, len));
									if(setGrid - 10 >= 0 && setGrid - 10 <= 8){
										grid[setGrid-10] = 10;
										virtualGrid[setGrid-10] = 1;
										System.out.println("Set Gird["+(setGrid-10)+"] = 1");
                                    }
                                    else if(setGrid - 20 >= 0 && setGrid - 20 <= 8){
										grid[setGrid-20] = 20;
										virtualGrid[setGrid-20] = 2;
										System.out.println("Set Gird["+(setGrid-20)+"] = 2");
									}
                                }
                                //new
                                if(inPort == 6668 && !new String(b.array(), 0, len).startsWith("Client")){
									System.out.println("6668in");
                                    tmp  = Integer.parseInt(new String(b.array(), 0, len));
                                    if(tmp-10<=8&&tmp-10>=0){
                                        if(index[tmp-10] == 100){
                                            index[tmp-10]=1;
                                            virtualGrid[tmp-10] = 1;
                                        }
                                            
                                    }
                                    else if(tmp-20<=8&&tmp-20>=0){
                                        if(index[tmp-20] == 100){
                                            index[tmp-20]=2;
                                            virtualGrid[tmp-20] = 2;
                                        }
                                            
                                    }
                                    countRound++;
                                  //  System.out.println(countRound);

                                }
								// Send message to client
								String data = "Server hello!! (" + sc + ")";
								if(inPort == 8883){
									data = Boolean.toString(inDetect);		// Check two Client are online
								}
								if(inPort == 8880){
									data = "1";				// Player 1
                                }
                                else if(inPort == 8881){
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
								if(inPort == 8890){
									data = Integer.toString(pos[1]);
                                }
                                else if(inPort == 8891){
									data = Integer.toString(pos[2]);
								}
								if(inPort == 8892){
									data = Integer.toString(pos[0]);
								}
								if(inPort == 2000 && checkGrid <= 8  && checkGrid >= 0 && (virtualGrid[checkGrid] == 8 || virtualGrid[checkGrid] == 9) ){
									data = Integer.toString(grid[checkGrid]);
                                }
                                else if(inPort == 2002){
									data = Integer.toString(round); 
                                }
                                //new
                                if(inPort==6669){
                                    if(tmp-10<=8&&tmp-10>=0){
                                        tmp = tmp - 10;
                                        data = Integer.toString(tmp);	
                                    }
                                    else if(tmp-20<=8&&tmp-20>=0){
                                        tmp = tmp - 20;
                                        data = Integer.toString(tmp);	
                                    }
                                    else{
                                        data = Integer.toString(tmp);
                                    }
                                }
                                if(inPort==6667){       //check is O or X to display on two player's oxgame
                                    if(tmp<=8&&tmp>=0){
                                        int OX = index[tmp];  //OX = 1 is "O" , OX = 2 is "X"
                                        if(OX==1){
                                            data = "O";
                                        }
                                        else if(OX==2){
                                            data = "X";
                                        }	
                                    }
                                }
                                if(inPort==6666){   //check for win, 1= O win, 2= X win , 3= peace
                                    if(Win().compareTo("O win")==0){
                                        data = "O win";
                                    }
                                    else if(Win().compareTo("X win")==0){
                                        data = "X win";
                                    }
                                    else if(Win().compareTo("peace")==0){
                                        data = "peace";
                                    }
                                    else if(Win().compareTo("noWiner")==0){
                                        data = "noWiner";
                                    }
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
    public static String Win()
    {
        
        //horizontal check
        if( check(0,1,2) != "" ) 
            return whowin;
        else if(check(3,4,5) != "" )
            return whowin;
        else if (check(6,7,8) != "")
            return whowin;
        
        //vertical check
        else if (check(0,3,6) != "")
            return whowin;  
        else if (check(1,4,7) != "")
            return whowin;
        else if (check(2,5,8) != "")
            return whowin;
        
        //diagonal check
        else if (check(0,4,8) != "")
            return whowin;  
        else if (check(2,4,6) != "")
            return whowin;
        else if (countRound==9)
            return "peace";
        else 
            return "noWiner";
        
    }
    public static String check(int a, int b,int c)
    {
        if ( index[a] == index[b] && index[b] == index[c] && index[a] !=0){
            if(index[a] == 1){
                whowin = "O win";
            }
            else if(index[a] == 2){
                whowin = "X win";
            }
            
        }
        return whowin;
    }
}