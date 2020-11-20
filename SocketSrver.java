// Non-Blocking Server
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
public class SocketSrver implements Runnable {
	private ServerSocket srvSocket;
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;

	private Thread thread;						// create for run()
	private boolean yourTurn = false;
	public static boolean accpected = false;			// accpeted to client

	private String ip = "localhost";
	private int port = 6666;
	Page page;
	public SocketSrver(){
		// load background - HomePage
		
		if(!connect()){
			initializeSystem();
		}
		// Frame Set
		
		// Thread
		thread = new Thread(this);
		thread.start();
	}

	
	public void run(){
		// page = new Page();
		while(true){
			CheckConnection();
			if(!accpected){			// accpected == False
				System.out.println("Listen for request...");
				ListenForClientRequest();
			}else{
				try{
					dos.writeInt(1);
					dos.flush();
				}catch(Exception e){
					System.out.println("run() Error : "+e);

				}
			}
			
		}
	}

	public static boolean StableConnect(){
		return accpected;
	}
	private void CheckConnection(){
		int receiveValue = 0;
		try{
			receiveValue =  dis.readInt();
		}catch(Exception e){
			// System.out.println("CheckConnection() Error : "+e);
		}
		if(receiveValue != 1){
			System.out.println("No Client online !");
			accpected = false;
		}
	}
	// Listen the event of Client Connect to Server(Fake)
	private void ListenForClientRequest(){
		Socket socket = null;
		try{
			socket = srvSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accpected = true;
			System.out.println("Accepted Client join");
		}catch(Exception e){
			System.out.println("ListenForClientRequest() Error : "+e);
		}
		yourTurn = true;
	}

	// Listen the event of Server (Fake) connect to Client
	private boolean connect(){
		// System.out.println("No Client join");
		try{
			socket = new Socket(ip,port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accpected = true;
		}catch(Exception e){
			// System.out.println("connect() Error : "+e);
			return false;
		}
		System.out.println("Connected !!!");
		return true;
	}

	private void initializeSystem(){
		try{
			srvSocket = new ServerSocket(port);
		}catch(Exception e){
			System.out.println("initializeSystem() Error : "+e);
		}
		System.out.println("System initialized!");
	}
	public static void main(String args[])
	{
		SocketSrver socketSrver = new SocketSrver();
		
	}
}
