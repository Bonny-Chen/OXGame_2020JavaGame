import java.awt.*;
import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.event.*;
import java.rmi.*;
import java.net.*;
import java.io.*;
import java.nio.channels.AcceptPendingException;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class pagetest extends Thread {
    public static Interface o = null;
    public static SocketSrver socketSrver;
    public static SocketClient socketClient;
    private static Socket s1 = null;
    private static String line = null;
    private static BufferedReader br = null;
    private static BufferedReader is = null;
    private static PrintWriter os = null;
    private static String response = null;
    private static Socket socket = null;
    private static ServerSocket srvsocket = null;
    private static boolean accepted = false;
    private static boolean onlineCheck = false;
    private static boolean onlinegame = false;


    public static JPanel game = new JPanel();
    public static JPanel PlayerPanel = new JPanel(); // PlayerSelectionPage's Panel
    public static JPanel SpacePanel = new JPanel(); // SpaceFlagPage's Panel
    private static JPanel jp;
    public static JFrame screen = new JFrame();
    public static JLabel characterLabel;
    public static Integer player = -1;
    public static Integer onlinePlayer = 0;
    public static Integer Character = 9;
    public static Integer anRole = 9;
    public static Integer anPlayer;
    public static int inform[] = new int[2];
    public static int x = 500;
    public static int y = 500;
    public static int s = 500; // use for recording server moving
    private static ImageIcon player1;
    private static JLabel player1Lab = new JLabel();
    private static JPanel glassPanel = new JPanel();
    private static JPanel glassoxPanel = new JPanel();
    private static JLabel blockingLabel = new JLabel();
    private static JLabel WaitingMsg = new JLabel("Waiting For Client...");
    public static boolean windowClosed = false;
    public static int role = -1;            // use for spaceFlag Page (Player's role Get)
    public static int theRound = 4;         // theRound = 1 is Player1's Round , theRound = 2 is Player2's Round , theRound = 4 is default(Error)
    public static int oxround = -1;
    public static int oxindex = 10;
    private static oxButton[] oxbtn;
    private static ImageIcon iconO = new ImageIcon("Img\\o.png");
    private static ImageIcon iconX = new ImageIcon("Img\\x.png");
    public static int PressedBtn = 100;                 //oxgame(), return clicked button index (0~8)
    public static String checkwin = "noWiner";          //oxgame() , checkwin = "O win" Player1 win , checkwin = "X win" Player2 win , checkwin = "peace" is peace, checkwin = "noWiner" Default
    public static String returnOX = "";                 //oxgame() , return "O" or "X" (Set and Display Button Icon - O or X)
    public static boolean displayed = false;            //oxgame() , a flag to make sure ox displayed on screen , true -> can check winner 
    private static JPanel oxglassPanel = new JPanel();
    private static JLabel blockLabel = new JLabel();
    pagetest() {    
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                windowClosed = true;
                e.getWindow().dispose();
                System.out.println("JFrame Closed!");
            }
        });
        HomePage();
        
        screen.validate();

    }

    public static boolean ClosedWindow() {
        return windowClosed;
    }

    private static boolean Connected() {
        return accepted;
    }

    private static void Connection(int Port , String Message){
        try
		{
			// Creates a socket channel 
			SocketChannel sc = SocketChannel.open();
			sc.configureBlocking(false);
			sc.connect(new InetSocketAddress("127.1", Port));

            // if the socket has connected, sc.finishConnect() will return false
            for (int loopcount = 0 ; !sc.finishConnect() ; loopcount++)
            {
                // do something 
                System.out.println("Loop count = " + loopcount);
                try 
                {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException e) 
                {
                    System.err.println(e);
                }
            }
			// Connection established
			// messages from args[2] and args[3]
			for(int i = 1 ; i < 2 ; i++) // messages from args[2] and args[3]
			{
				// Send message to server
                ByteBuffer buffer = ByteBuffer.wrap("Client Login".getBytes());
                if(Port == 8884 || Port == 8885 || Port==8888){
                    buffer = ByteBuffer.wrap(Message.getBytes());
                }
				sc.write(buffer);

				// Receive message
				ByteBuffer 	b = ByteBuffer.allocate(1000); 
				int			len = sc.read(b);	// read message from sc (non-blocking)
				while(len == 0)
				{
                	try 
                	{
                    	Thread.sleep(100);
                	} 
                	catch (InterruptedException e) 
                	{
                    	System.err.println(e);
                	}
					// System.out.print("+"); // do something
					len = sc.read(b);
				}
                // System.out.println(new String(b.array(), 0, len));
                
                if(new String(b.array(), 0, len).startsWith("1") && Port == 8880){
                    player = Integer.parseInt(new String(b.array(), 0, len));
                }else if(new String(b.array(), 0, len).startsWith("2") && Port == 8881){
                    player = Integer.parseInt(new String(b.array(), 0, len));
                }else if(Port == 8886 || Port == 8887){
                    if( new String(b.array(), 0, len).startsWith("0") ||new String(b.array(), 0, len).startsWith("1") || new String(b.array(), 0, len).startsWith("2") || new String(b.array(), 0, len).startsWith("9")){
                        role = Integer.parseInt(new String(b.array(), 0, len));
                    }
                }

                if(Port == 8883){
                    if(new String(b.array(), 0, len).startsWith("true") || new String(b.array(), 0, len).startsWith("false")){
                        onlineCheck = Boolean.parseBoolean(new String(b.array(), 0, len));
                    }
                }
                else if(Port == 8882){
                    if( new String(b.array(), 0, len).startsWith("2") ||new String(b.array(), 0, len).startsWith("1")){
                        oxround=Integer.parseInt(new String(b.array(), 0, len));
                        System.out.println("round = "+oxround);
                    }
                }
                else if(Port==8889){ 
                    // PressedBtn - For Syn(), get pressed button index
                    if( Integer.parseInt(new String(b.array(), 0, len)) >= 0 && Integer.parseInt(new String(b.array(), 0, len)) <=8){
                        PressedBtn=Integer.parseInt(new String(b.array(), 0, len));
                        System.out.println("PressedBtn = "+PressedBtn);
                    }
                   
                }
                else if(Port==6667&&!new String(b.array(), 0, len).startsWith("Server")){
                    if(new String(b.array(), 0, len).startsWith("O")||new String(b.array(), 0, len).startsWith("X")){
                        // returnOX - For Syn(), to draw o or x
                        returnOX = new String(b.array(), 0, len);
                        System.out.println("returnOX = " + returnOX);
                    }
                    
                }
                else if(Port == 6666 && !new String(b.array(), 0, len).startsWith("Server")){
                    checkwin=new String(b.array(), 0, len);
                    System.out.println("checkwin = " + checkwin);
                }

			}
			sc.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
        }
    }
    public static void main(String args[]) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        socketSrver = new SocketSrver();
        if(args.length < 2)
		{
		    System.out.println("Usage: java MultiPortClient server_ip port ");
		    System.exit(1);
		}
        Connection(Integer.parseInt(args[1]),"");
        Connection(8883,"");

        System.out.println("player = " + player);
        
        glassPanel.setOpaque(false);
        glassPanel.add(blockingLabel);
        glassPanel.addMouseListener(new MouseAdapter() {
        });
        glassPanel.addKeyListener(new KeyAdapter() {
        });
        screen.setGlassPane(glassPanel);
        pagetest pagetest = new pagetest();
        while (true) {
           
            if (!onlineCheck) {
                screen.setTitle("Waiting For Connection");
                glassPanel.add(WaitingMsg);
                WaitingMsg.setFont(new Font("Serif", Font.BOLD, 48));
                WaitingMsg.setForeground(Color.RED);
                glassPanel.setVisible(true);
                blockingLabel.requestFocus();
                Connection(8883,"");
            } else {
                screen.setTitle("HomePage"+"-Player "+player);
                glassPanel.setVisible(false);
                screen.remove(glassPanel);

                break;
            }
        }

    }

    static ButtonImageCreate[] Button;
    static ButtonHandler BH = new ButtonHandler();
    static MouseHandler MH = new MouseHandler();

    public static void HomePage() {
        // System.out.println("You are Player "+ player);
        jp = new JPanel();
        JButton StartBtn = new JButton("START");
        JButton HowToBtn = new JButton("HOW TO");
        JButton ExitBtn = new JButton("EXIT");
        JLabel titLabel = new JLabel("OX Game");
        ImageIcon logoIcon = new ImageIcon("Img\\logo.png");
        JLabel ImgLabel = new JLabel();

        // Default Setting
        screen.setTitle("Home Page"+"-Player "+player);
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.setLayout(null); // for position

        // Show image
        logoIcon.setImage(logoIcon.getImage().getScaledInstance(350, 330, Image.SCALE_DEFAULT));
        ImgLabel.setBounds(280, 100, 500, 500); // Image setting(x,y,width,heigh)
        ImgLabel.setIcon(logoIcon);
        jp.add(ImgLabel);

        // titLabel Setting (Title)
        titLabel.setBounds(350, 100, 300, 50); // Title setting (x,y,width,heigh)
        titLabel.setFont(new java.awt.Font("Dialog", 4, 50)); // Resize Font
        titLabel.setForeground(Color.decode("#FFFFFF"));
        jp.add(titLabel);

        // Background setting
        jp.setBackground(Color.decode("#888CCCA"));

        // HowTo button change
        HowToBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Btn Click");
                HowToBtn.setContentAreaFilled(false); // Remove Btn Background
                HowToBtn.setFocusable(false); // inside line invisible
                HowToBtn.setBorderPainted(false); // outside line invisible
            }
        });
        StartBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Btn Click");
                screen.remove(jp); // Clear the screen
                screen.setTitle("Player Select"+"-Player "+player);
                PlayerSelectionPage();
                screen.validate();
            }
        });

        // button setting
        StartBtn.setFocusable(false); // inside line invisible
        StartBtn.setBorderPainted(false); // outside line invisible
        HowToBtn.setFocusable(false);
        HowToBtn.setBorderPainted(false);
        ExitBtn.setFocusable(false);
        ExitBtn.setBorderPainted(false);

        StartBtn.setBounds(630, 300, 160, 30); // set position
        HowToBtn.setBounds(630, 350, 160, 30);
        ExitBtn.setBounds(630, 400, 160, 30);
        StartBtn.setBackground(Color.decode("#EEEABA")); // set background color
        HowToBtn.setBackground(Color.decode("#EEEABA"));
        ExitBtn.setBackground(Color.decode("#EEEABA"));
        StartBtn.setForeground(Color.decode("#FF12345")); // set text color
        HowToBtn.setForeground(Color.decode("#FF12345"));
        ExitBtn.setForeground(Color.decode("#FF12345"));

        jp.add(StartBtn);
        jp.add(HowToBtn);
        jp.add(ExitBtn);

        screen.add(jp);
        screen.validate();
        // HomePanel.add(Label);
        // screen.add(HomePanel);
    }
  
    public static void oxgame()
    {
        JPanel      jp = new JPanel();
        ImageIcon   boardIcon = new ImageIcon("Img\\ox.png");
        JLabel      boardLabel = new JLabel();
        ImageIcon   selfIcon,anIcon;        //self image , anothor player's image
        JLabel      self1Label = new JLabel();
        JLabel      anLabel = new JLabel();

        screen.setTitle("GamePage"+"-Player "+player);
        jp.setLayout(null);
        jp.setVisible(true);
        // Show image
        boardIcon.setImage(boardIcon.getImage().getScaledInstance(350,330,Image.SCALE_DEFAULT));
        boardLabel.setBounds(280,0,600,600);            //Image setting(x,y,width,heigh)
        boardLabel.setIcon(boardIcon);
        jp.add(boardLabel);
        //player get self image
        if(player == 1){
            Connection(8886,"");
        }
        else if(player == 2){
            Connection(8887,""); 
        }
        selfIcon = new ImageIcon("Img/Player" + player + "-" + role + ".png");
        selfIcon.setImage(selfIcon.getImage().getScaledInstance(150,180,Image.SCALE_DEFAULT));
        if(player==1){          //player one at left side
            self1Label.setBounds(90,130,300,300);
        }
        else if(player==2){     //player two at right side
            self1Label.setBounds(650,130,300,300);
        }
        self1Label.setIcon(selfIcon);
        jp.add(self1Label);
        //get anothor player's image
        if (player == 1){
            anPlayer = 2;
            Connection(8887,"");
            anRole = role;
            while(anRole == 9){
                Connection(8887,"");
                anRole = role;
            }
        }
        else if (player == 2){
            anPlayer = 1;
            Connection(8886,"");
            anRole = role;
            while(anRole == 9){
                Connection(8886,"");
                anRole = role;
            }
        }
        anIcon = new ImageIcon("Img/Player" + Integer.toString(anPlayer) + "-" + Integer.toString(anRole) + ".png");
        anIcon.setImage(anIcon.getImage().getScaledInstance(150,180,Image.SCALE_DEFAULT));

        if(player==1){      //playerone's parner image at right side
            anLabel.setBounds(650,130,300,300);
        }
        else if(player==2){ //playertwo's parner image at left side
            anLabel.setBounds(90,130,300,300);
        }
        anLabel.setIcon(anIcon);
        jp.add(anLabel);       

        oxbtn = new oxButton[9];
        buttonListener bh = new buttonListener();
        //set button
        for(int x=0; x<9; x++){
            oxbtn[x] = new oxButton(x);
            oxbtn[x].addActionListener(bh);
            oxbtn[x].setFocusable(false);           //inside line invisible
            oxbtn[x].setBorderPainted(false);       //outside line invisible
            if(x<3)oxbtn[x].setBounds(x*105+302, 153, 100, 95);  //set button position
            else if(x<6)oxbtn[x].setBounds((x-3)*105+302, 250, 100, 95);
            else if(x<9)oxbtn[x].setBounds((x-6)*105+302, 348, 100, 95);
            jp.add(oxbtn[x]);
        }    
        screen.add(jp);
        screen.validate();

        //Connection(8882,"");
        
        // if(player==2){
        //    Connection(8882,"");
        //    glassoxPanel.setOpaque(false);
        //    glassoxPanel.add(blockLabel);
        //    glassoxPanel.addMouseListener(new MouseAdapter() {});
        //    glassoxPanel.addKeyListener(new KeyAdapter() {});
        //    screen.setGlassPane(glassoxPanel);
        //    glassoxPanel.setVisible(true);
           
        //    //blockLabel.requestFocus();
            
        // }
    

        //create thread to keep transmission and button display
        Thread synThread = new Thread(
            new Runnable(){
                public void run(){
                    while(true){
                        if(checkwin.compareTo("noWiner")!=0){  
                            for(int i=0;i<9;i++){       //game end, set all button pressed to prevent player keep clicking
                                oxbtn[i].setPressed(true);
                            }
                            break;
                        }
                        else{   //no winer, keep syn
                            syn();
                            try{
                                Thread.sleep(150);  //slow down thread
                            }
                            catch (InterruptedException e) 
                            {
                                System.err.println(e);
                            }

                        }
                        
                    }
                } 
            }
        );
        synThread.start(); 
    }
    // reset all button set
    // public static void resetButtons()
    // {
    //     for(int i = 0; i < 9; i++)
    //     {
    //         oxbtn[i].setContentAreaFilled(true);
    //         oxbtn[i].resetPressed(false);
    //         oxbtn[i].setIndex(0);
    //         oxbtn[i].setIcon(null);
    //         //coundRound=0;
    //     }
    // }
    
    public static void block()
    {
        Connection(8882,"");
        if(player!=oxround){
           glassoxPanel.setOpaque(false);
           glassoxPanel.add(blockLabel);
           glassoxPanel.addMouseListener(new MouseAdapter() {});
           glassoxPanel.addKeyListener(new KeyAdapter() {});
           screen.setGlassPane(glassoxPanel);
           glassoxPanel.setVisible(true);
           blockLabel.requestFocus();
            
        }
        else if(player==oxround){
           glassoxPanel.setVisible(false);
           screen.remove(glassoxPanel);
           
        //    screen.validate();
        //    screen.setVisible(true);

        }
    }
    
    private static class buttonListener implements ActionListener
    {
        
        public void actionPerformed(ActionEvent e) 
        {
            

            oxButton myBtn = (oxButton)e.getSource();
            
            if(!myBtn.getPressed()){    //button not pressed
                // Btn -> send         
                // Player 1 -> O Player2-> X , port 8888-> get button index(0~8) 
                if(player == 1){
                        Connection(8888, Integer.toString(myBtn.getID()+10));   //player1 button index +10 send to port 8888
                        iconO.setImage(iconO.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                        myBtn.setIcon(iconO);
                        myBtn.setIndex(1);
                        myBtn.setContentAreaFilled(false);
                        displayed= true;     //a flag to make sure o or x diaplayed on screen then can check win
                }
                else if(player == 2){
                        Connection(8888, Integer.toString(myBtn.getID()+20));   //player2 button index+20 send to port 8888
                        iconX.setImage(iconX.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                        myBtn.setIcon(iconX);
                        myBtn.setIndex(2);
                        myBtn.setContentAreaFilled(false);
                        displayed= true;  
                }
                myBtn.setPressed(true); //set button[index] to clicked
                
              
            }  
            if(player==1||player==2){
              //  block();
            }
                
           
        }

       
        
    }
    public static void syn()
    {
         // Before win / lose -> loop : oxbtn[PressedBtn].setPressed(); draw;
        Connection(8889, "");   //get which index of button anothor player had clicked (PressedBtn 0~8)
        try{
                Thread.sleep(150);  //make port 8889 not to read strange data
            }
            catch (InterruptedException e) 
            {
                System.err.println(e);
        }
        if(PressedBtn>=0&&PressedBtn<=8){   //get button index(0~8)
            Connection(6667, "");   //draw o or x on screen(returnOX "O" or "X")
            if(returnOX.compareTo("X")==0&&!oxbtn[PressedBtn].getPressed()){    //equel "X" and not pressed
                iconX.setImage(iconX.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                oxbtn[PressedBtn].setIcon(iconX);
                oxbtn[PressedBtn].setPressed(true); //set this button to pressed
                oxbtn[PressedBtn].setContentAreaFilled(false);  //remove btn line (GUI)
                displayed=true;     //a flag to make sure o or x diaplayed on screen then can check win
            }
            if(returnOX.compareTo("O")==0&&!oxbtn[PressedBtn].getPressed()){    //equel "O" and not pressed
                 iconO.setImage(iconO.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                 oxbtn[PressedBtn].setIcon(iconO);
                 oxbtn[PressedBtn].setPressed(true);
                 oxbtn[PressedBtn].setContentAreaFilled(false);
                 displayed=true;
            }
            if(displayed){  //if button sucess to display on screen -> check winer
                Connection(6666, "");   //port 6666 to get winer information(checkwin "O win", "X win", "peace")
                //message show on screen
                if(checkwin.compareTo("O win")==0){  
                    JOptionPane.showConfirmDialog(null, "O win");
                }
                else if(checkwin.compareTo("X win")==0){
                    JOptionPane.showConfirmDialog(null, "X win"); 
   
                }
                else if(checkwin.compareTo("peace")==0){
                    JOptionPane.showConfirmDialog(null, "Peace"); 
                  
                }
                displayed = false;  //checked winer -> set flag to false

               
                
            }
        }
        
        
      
    }

    public static void PlayerSelectionPage() {
        PlayerPanel.setVisible(true);
        screen.setTitle("PlayerSelectionPage"+"-Player "+player);
        PlayerPanel.setBackground(Color.decode("#000339"));
        PlayerPanel.setLayout(null);
        Button = new ButtonImageCreate[6];
        for (int x = 0; x < Button.length; x++) {
            Button[x] = new ButtonImageCreate(x, "Img/Player1-" + Integer.toString(x) + "UnSelected.png",
                    "Img/Player1-" + Integer.toString(x) + ".png", (x + 1) * (x + 200) + (x - 50), 100, 170, 230);
            Button[x].addActionListener(BH);
            Button[x].addMouseListener(MH);
            PlayerPanel.add(Button[x]);
        }

        // OK Button
        Button[4] = new ButtonImageCreate(4, "Img/OKBTN-UnSelected.png", "Img/OKBTN.png", 355, 450, 150, 70);
        Button[4].addActionListener(BH);
        Button[4].addMouseListener(MH);
        PlayerPanel.add(Button[4]);

        // Back To Home Button
        Button[5] = new ButtonImageCreate(5, "Img/Back.png", "Img/Back.png", 30, 30, 50, 50);
        Button[5].addActionListener(BH);
        Button[5].addMouseListener(MH);
        PlayerPanel.add(Button[5]);
        screen.add(PlayerPanel);

    }

    // public static void SpaceFlagPage() {
    //     ImageIcon flagIcon, flagIcon2, character, character2, srver; // server : Client VS Server(PC)
    //     Image image;
    //     JLabel flagLabel, flagLabel2, characterLabel2, srverLabel;
        
    //     SpacePanel.setVisible(true);
    //     screen.setTitle("SpaceFlagPage"+"-Player "+player);
    //     SpacePanel.setLayout(null);
    //     SpacePanel.setFocusable(true);
    //     SpacePanel.requestFocus(); 
    //     if(player == 1){
    //         Connection(8886,"");
    //     }else if(player == 2){
    //         Connection(8887,"");
    //     }
    //     System.out.println("Player: " + player);
    //     System.out.println("role = " + role);
    //     flagIcon = new ImageIcon("Img/Flag.png");
    //     image = flagIcon.getImage();
    //     image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
    //     flagIcon = new ImageIcon(image);

    //     flagIcon2 = flagIcon;

    //     character = new ImageIcon("Img/Player" + player + "-" + role + ".png");
    //     image = character.getImage();
    //     image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
    //     character = new ImageIcon(image);

    //     // Get another Player Inform
    //     // Problem : Connected Server but two client not connected!!

    //     if (player == 1){
    //         anPlayer = 2;
    //         Connection(8887,"");
    //         anRole = role;
    //         while(anRole == 9){
    //             Connection(8887,"");
    //             anRole = role;
    //         }
    //     }
    //     else if (player == 2){
    //         anPlayer = 1;
    //         Connection(8886,"");
    //         anRole = role;
    //         while(anRole == 9){
    //             Connection(8886,"");
    //             anRole = role;
    //         }
    //     }

        
    //     System.out.println("anPlayer = " + anPlayer);
    //     System.out.println("anRole = " + anRole);
    //     character2 = new ImageIcon("Img/Player" + Integer.toString(anPlayer) + "-" + Integer.toString(anRole) + ".png");
    //     image = character2.getImage();
    //     image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
    //     character2 = new ImageIcon(image);

    //     srver = new ImageIcon("Img/Player" + 2 + "-" + 1 + ".png");
    //     image = srver.getImage();
    //     image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
    //     srver = new ImageIcon(image);

    //     flagLabel = new JLabel(flagIcon);
    //     flagLabel2 = new JLabel(flagIcon2);
    //     characterLabel = new JLabel(character);
    //     characterLabel2 = new JLabel(character2);
    //     srverLabel = new JLabel(srver);

    //     flagLabel.setBounds(200, 0, 50, 70);
    //     flagLabel2.setBounds(600, 0, 50, 70);

    //     if (player == 1) {
    //         characterLabel.setBounds(600, x, 50, 70);
    //         characterLabel2.setBounds(200, y, 50, 70);

    //     } else {
    //         characterLabel.setBounds(200, x, 50, 70);
    //         characterLabel2.setBounds(600, y, 50, 70);

    //     }

    //     KeyListener listener = new KeyListener() {
    //         @Override
    //         public void keyTyped(KeyEvent e) {
    //         }

    //         @Override
    //         public void keyPressed(KeyEvent e) {
    //             // keyPressed = Invoked when a physical key is pressed down. Uses KeyCode, int
    //             // output
    //             System.out.println("KeyPressed");

    //             if (e.getKeyCode() == KeyEvent.VK_SPACE) {
    //                 System.out.println("move");

    //                 if (player == 1){
    //                     characterLabel.setBounds(600, x, 50, 70);
    //                     characterLabel2.setBounds(200, y, 50, 70);
    //                 }else if (player == 2){
    //                     characterLabel.setBounds(200, x, 50, 70);
    //                     characterLabel2.setBounds(600, y, 50, 70);
    //                 }
                        
                    

    //             }
    //         }

    //         @Override
    //         public void keyReleased(KeyEvent e) {
    //             if(player == 1)
    //                 x -= 5; // Change the moving speed Reference 20
    //             else if(player == 2)
    //                 y -= 5;
    //         }
    //     };
    
    //     SpacePanel.add(flagLabel);
    //     SpacePanel.add(flagLabel2);
    //     SpacePanel.add(characterLabel);
    //     SpacePanel.add(characterLabel2);
    //     SpacePanel.add(srverLabel);
    //     SpacePanel.addKeyListener(listener);
    //     screen.add(SpacePanel);

    // }

    private static class ButtonHandler implements ActionListener {
        private Integer LastClick = -1;
        pagetest page;

        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            ButtonImageCreate tmpBtn = myBtn;
            // ?
            if (ExistsLastClick() >= 0 && ExistsLastClick() != 4) {
                Character = ExistsLastClick();
                System.out.println("LastClick " + ExistsLastClick());
                tmpBtn = Button[ExistsLastClick()]; // Control LastClick Button
                tmpBtn.setIcon(tmpBtn.icon);
                tmpBtn.setClick(0); // Formate the setting of Button Click

            }

            // Switch to HomePage
            if (myBtn.getID() == 5) {
                PlayerPanel.setVisible(false);
                HomePage();
            } else if (myBtn.getID() == 4) { // OKBtn
                PlayerPanel.setVisible(false);
                if(player == 1){
                    System.out.println("Character : "+Integer.toString(Character));
                    Connection(8884,Integer.toString(Character));
                }else if(player == 2){
                    Connection(8885,Integer.toString(Character));
                }
                oxgame();
                //SpaceFlagPage();
            }
            myBtn.setIcon(myBtn.iconHover);
            if (myBtn.IsClicked() == 1) {
                myBtn.setIcon(myBtn.icon);
                myBtn.setClick(0);
            } else {
                myBtn.setClick(1);
            }

            getLastClick(myBtn.getID());

            System.out.println(myBtn.getID() + " was clicked");
        }

        // Use for detect whethere exists last clicked button
        // If yes , cancel the button click
        public void getLastClick(int LastClick) {
            this.LastClick = LastClick;
        }

        public int ExistsLastClick() {
            return this.LastClick;
        }
    }

    private static class MouseHandler extends MouseAdapter {
        // @Override
        public void mouseEntered(MouseEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            myBtn.setIcon(myBtn.iconHover);
        }

        public void mouseExited(MouseEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            if (myBtn.IsClicked() != 1)
                myBtn.setIcon(myBtn.icon);
        }
    }

}

class ButtonImageCreate extends JButton {
    private Image image, imageHover;
    private boolean hover;
    private int ID;
    String SelectedURL;
    ImageIcon iconHover, icon;
    private int Clicked = 0;

    public ButtonImageCreate(int ID, String URL, String SelectedURL, int x, int y, int h, int w) {
        this.SelectedURL = SelectedURL;
        icon = new ImageIcon(URL);
        image = icon.getImage();
        image = image.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);

        this.iconHover = new ImageIcon(SelectedURL);
        imageHover = this.iconHover.getImage();
        imageHover = imageHover.getScaledInstance(h, w, java.awt.Image.SCALE_SMOOTH);
        this.iconHover = new ImageIcon(imageHover);
        this.ID = ID;
        this.setIcon(icon);
        this.setContentAreaFilled(false); // Remove Btn Background
        this.setFocusable(false);
        this.setBorderPainted(false); // Remove Btn Border
        this.setBounds(x, y, h, w);
    }

    public int getID() {
        return ID;
    }

    public void setClick(int Clicked) {
        this.Clicked = Clicked;
    }

    public int IsClicked() {
        return Clicked;
    }

}

class oxButton extends JButton
{
    private int id;
    private int index;
    private boolean isPressed;
    private boolean isBlock;

    public oxButton(int id){
        this.id =id;
        this.index = 0;
        isPressed = false; 
        isBlock = false;       
    }
   public int getID() {
        return id;
    }
    public int getIndex(){
        return index;
    }
    public void setIndex(int num){
        this.index = num;
    }
    public boolean getPressed(){
        return isPressed;
    } 
    public void setPressed(boolean isPressed){
        this.isPressed = true;
    }   
    public void resetPressed(boolean isPressed){
        this.isPressed = false;
    }
    public boolean getBlock(){
        return isBlock;
    }
    public void setBlock(boolean isBlock){
        this.isBlock = true;
    }
   
}
