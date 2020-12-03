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

public class serverTmp
{
    public static boolean inDetect = false;
    public static boolean connect = false;
	public static int inPort = 0;
	public static int in8881 = 0;
	public static int in8880 = 0;
    public static int in8882 = 0;
    public static int in8884 = 0;
    public static int in8885 = 0;
    public static int in8888 = 0;
    public static int in8889 = 0;
	public static int BlockWhile = 0;
	public static int round = 0;							// Player 1's Round = 1 ; Player 2's Round = 2;
    public static int[] role = new int[3];				// Saving two player's role
    public static int [] index = new int [9];           // For recording OXGame
    public static int tmp = 100;        
    public static String whowin = "";
    public static int countRound = 0;

	public static void main(String args[]) throws Exception 
	{
		Selector 	selector = Selector.open();   	// Create a selector
		role[1] = 9;
        role[2] = 9;
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
		while(true){
		    // The following method blocks until 
		    // at least one of the registered events occurs.
			selector.select();
			if(BlockWhile==1){
				break;
			}
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
						sc.register(selector, SelectionKey.OP_READ);
						if(sc.getLocalAddress().toString().equals("/127.0.0.1:8880")){
							System.out.println("Client in port 8880");
							inPort = 8880;
							in8880 = 1;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8881")){
							System.out.println("Client in port 8881");
							inPort = 8881;
							in8881 = 1;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8882")){
							System.out.println("Client in port 8882");
							inPort = 8882;
							if(in8882 == 0){
								round = 1;			// Set This is Player2's Round
							}else if(in8882 == 1){
								round = 2;			// Set This is Player1's Round
                            }
                            in8882 = (in8882 + 1)%2;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8883")){
							System.out.println("Client in port 8883");
							inPort = 8883;
							if(in8880 == 1 && in8881 == 1){
								inDetect = true;
							}else{
								inDetect = false;
							}
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8884")){
							System.out.println("Client in port 8884");
                            inPort = 8884;
                            in8884=1;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8885")){
							System.out.println("Client in port 8885");
                            inPort = 8885;
                            in8885=1;
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
							System.out.println("Client in port 8888");
                            inPort = 8888;
                            in8888 = 1;
                        }
                        else if(sc.getLocalAddress().toString().equals("/127.0.0.1:8889")){
							System.out.println("Client in port 8889");
                            inPort = 8889;
                            in8889 = 1;
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
								if(inPort == 8885){
									System.out.println("8885in");
									if( new String(b.array(), 0, len).startsWith("0") || new String(b.array(), 0, len).startsWith("1") || new String(b.array(), 0, len).startsWith("2")){
										// Saving Player 1 's Role in role[1]
										role[2] = Integer.parseInt(new String(b.array(), 0, len));
										System.out.println("role set : "+role);

									}
                                }
                                if(inPort == 8888 && !new String(b.array(), 0, len).startsWith("Client")){
									System.out.println("8888in");
                                    tmp  = Integer.parseInt(new String(b.array(), 0, len));
                                    if(tmp-10<=8&&tmp-10>=0){
                                        if(index[tmp-10] == 100){
                                            index[tmp-10]=1;
                                        }
                                            
                                    }
                                    else if(tmp-20<=8&&tmp-20>=0){
                                        if(index[tmp-20] == 100){
                                            index[tmp-20]=2;
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
                                if(inPort==8889){
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

