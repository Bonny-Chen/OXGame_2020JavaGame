import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketSrver {
	public static boolean accepted = false;
	public static boolean online = false;
	public static int player;
	public int[] inform = new int[2];

	public static boolean TwoClientOnline() {
		return online;
	}

	public static boolean StableConnect() {
		return accepted;
	}

	public static void main(String args[]) {
		SocketSrver socketSrver = new SocketSrver();
		socketSrver.SocketSrver();
	}

	public void SocketSrver() {
		player = -1;
		Socket s = null;
		ServerSocket ss2 = null;
		System.out.println("Server Listening......");
		try {
			ss2 = new ServerSocket(4445); // can also use static final PORT_NUM , when defined

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server error");

		}

		while (true) {
			try {
				s = ss2.accept();
				System.out.println("connection Established");
				accepted = true;
				player = (player + 1) % 2;
				if (player == 1) {
					online = true;
				} else {
					online = false;
				}
				System.out.println("Current Player Number = " + player);

				ServerThread st = new ServerThread(s);
				st.start();

			}

			catch (Exception e) {
				e.printStackTrace();
				System.out.println("Connection Error");

			}
		}

	}

}

class ServerThread extends Thread {

	String line = null;
	BufferedReader is = null;
	PrintWriter os = null;
	Socket s = null;

	SocketSrver socketSrver = new SocketSrver();

	public ServerThread(Socket s) {
		System.out.println(socketSrver.TwoClientOnline());
		
		this.s = s;

	}

	public void run() {
		try {
			is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = new PrintWriter(s.getOutputStream());

		} catch (IOException e) {
			System.out.println("IO error in server thread");
		}

		try {
			line = String.valueOf(socketSrver.TwoClientOnline());
			while (line.compareTo("QUIT") != 0) {

				os.println(line);
				os.flush();
				if (!line.startsWith("true") && !line.startsWith("false"))
					System.out.println("Response to Client  :  " + line);
				line = is.readLine();
				if (line.startsWith("Client"))
					line = String.valueOf(socketSrver.TwoClientOnline());
				else if (line.startsWith("Obtain")) {
					line = "Other one role : " + Integer.toString(socketSrver.inform[(socketSrver.player + 1) % 2]);
				} else if (line.startsWith("0") || line.startsWith("1") || line.startsWith("2")) {
					socketSrver.inform[socketSrver.player] = Integer.parseInt(line);
				}
			}
		} catch (IOException e) {

			line = this.getName(); // reused String line for getting thread name
			System.out.println("IO Error/ Client " + line + " terminated abruptly");
		} catch (NullPointerException e) {
			line = this.getName(); // reused String line for getting thread name
			System.out.println("Client " + line + " Closed");
			SocketSrver.accepted = false;
		}

		finally {
			try {
				System.out.println("Connection Closing..");
				if (is != null) {
					is.close();
					System.out.println(" Socket Input Stream Closed");
				}

				if (os != null) {
					os.close();
					System.out.println("Socket Out Closed");
				}
				if (s != null) {
					s.close();
					System.out.println("Socket Closed");
				}
				SocketSrver.accepted = false;
				SocketSrver.online = false;
				socketSrver.player = -1;
				System.out.println("Server Listening......");
			} catch (IOException ie) {
				System.out.println("Socket Close Error");
			}
		} // end finally
	}

}