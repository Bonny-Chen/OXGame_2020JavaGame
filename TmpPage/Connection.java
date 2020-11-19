import java.net.*;
import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Connection  implements Runnable{
    private String ip = "localhost";
    private int port = 666;
    private Scanner scanner = new Scanner(System.in);

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ServerSocket srvsocket;
    private Thread thread;

    // private JFrame screen;
    private boolean accepted = false;
    private boolean yourTurn = false;
    public Connection(){
        if(!connect())
            initializeServer();
        // thread = new Thread(this);
        // thread.start(); 
    }

    public void run(){
        System.out.println("Listen for Server connection...");
        initializeServer();
        // listenForServerRequest();
    }

    private void initializeServer(){
        try{
            srvsocket = new ServerSocket(port , 8 , InetAddress.getByName(ip));
        }catch(Exception e){
            System.out.println("initialize Server Error : "+e);
        }
        yourTurn = true;

    }
    private void listenForServerRequest(){
        Socket socket = null;
        try{
            socket = srvsocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("Server Connected");
        }catch (IOException e){
            System.out.println(e);
        }

    }
    private boolean connect(){
        try{
            socket = new Socket(ip,port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("Client Connected");
        }catch (IOException e){
            System.out.println("connect error : "+e);
            return false;
        }
        System.out.println("Connection Done!");
        return true;
    }
    public static void main(String[] args){
        Connection c = new Connection();

    }
}
