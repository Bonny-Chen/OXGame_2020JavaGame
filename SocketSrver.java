import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import java.rmi.*;
import java.rmi.server.*;

public class SocketSrver {
    public static void main(String args[])
	{
		ServerSocket	srverSocket = null;
		Socket			sc = null;
		InputStream		in = null;
		OutputStream	out = null;
		int				port = 6666;
		byte []			buf = new byte[1024];
		int				Number = 0;
		try
		{
		    // Creates a server socket, bound to the specified port.
			srverSocket = new ServerSocket(port);
			
			System.out.println("Waiting for request ...");
			try
			{
			    // Listens for a connection to be made to this socket and accepts it.
				sc = srverSocket.accept();
				System.out.println("Connected!");
				// Connect To RMI
				try
				{
					RMIImplement name = new RMIImplement();
					System.out.println("Registering ...");
					Naming.rebind("OXGame", name);	// arithmetic is the name of the service
					System.out.println("Register success");
				}
				catch(Exception e)
				{
					System.out.println("Exception: " + e.getMessage());
					e.printStackTrace();
				}
				sc.close();
			}
			catch(IOException e)
			{
				System.err.println(e);
			}
			finally
			{
				srverSocket.close();
			}
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}
}
