import java.rmi.*;
import java.rmi.server.*;

public class Server
{
	// Bind ArithmeticServer and Registry
	public static void main(String args[])
	{
		//System.setSecurityManager(new RMISecurityManager());
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
	}
}