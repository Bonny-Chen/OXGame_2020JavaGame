import java.net.*;
import java.io.*;

public class Client {
    public static void main(String args[])
	{
		Socket			client = null;
		InputStream 	in = null;
		OutputStream 	out = null;
		int				port = 6666;
		byte []			buf = new byte[1024];
		int				Number = 0;

		if(args.length < 0)
		{
		    System.out.println("Usage: java SimpleClient1 server_ip");
		}
		else
		{
			try
			{
			    // Creates a stream socket and connects it to the specified port number 
			    // at the specified IP address.
                client = new Socket(args[0], port);
		        System.out.println("Client In");
                

				client.close();
			}
			catch(UnknownHostException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
