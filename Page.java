import java.awt.*;
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

public class Page extends Thread {
    public static Interface o = null;
    // public static Thread thread;
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
    private static JLabel blockingLabel = new JLabel();
    private static JLabel WaitingMsg = new JLabel("Waiting For Client...");
    public static boolean windowClosed = false;
    public static int role = -1;            // use for spaceFlag Page (Player's role Get)
    public static int theRound = 4;         // theRound = 1 is Player1's Round , theRound = 2 is Player2's Round , theRound = 4 is default(Error)

    Page() {
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
			for(int i = 1 ; i < 3 ; i++) // messages from args[2] and args[3]
			{
				// Send message to server
                ByteBuffer buffer = ByteBuffer.wrap("Client Login".getBytes());
                if(Port == 8884 || Port == 8885){
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
					System.out.print("+"); // do something
					len = sc.read(b);
				}
                System.out.println(new String(b.array(), 0, len));
                
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
                
                
                if(Port == 8882){
                    if( new String(b.array(), 0, len).startsWith("0") ||new String(b.array(), 0, len).startsWith("1")){
                        
                    }
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
        Page page = new Page();
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

    public static void OXGamePage() {
        jp = new JPanel();
        ImageIcon oxIcon = new ImageIcon("Img\\ox.png");
        JLabel ImgLabel = new JLabel();

        // Default Setting
        screen.setTitle("OXGame"+"-Player "+player);
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.setLayout(null); // for position
        //
        Button = new ButtonImageCreate[9];
        for (int x = 0; x < Button.length; x++) {
            Button[x] = new ButtonImageCreate(x, "Img/o.png", "Img/Player1-" + Integer.toString(x + 1) + ".png",
                    (x + 1) * (x + 200) + (x - 50), 100, 170, 230);
            Button[x].addActionListener(BH);
            Button[x].addMouseListener(MH);

        }
        // player1.setBounds(0,0,0,0);
        jp.add(player1Lab);
        // Show ox image
        oxIcon.setImage(oxIcon.getImage().getScaledInstance(460, 440, Image.SCALE_DEFAULT));
        ImgLabel.setBounds(220, 50, 600, 500); // Image setting(x,y,width,heigh)
        ImgLabel.setIcon(oxIcon);
        jp.add(ImgLabel);

        // player1.setImage(player1.getImage().getScaledInstance(460,440,Image.SCALE_DEFAULT));
        // ImgLabel.setBounds(220,50,600,500); //Image setting(x,y,width,heigh)
        // ImgLabel.setIcon(player1);
        // jp.add(ImgLabel);

        screen.add(jp);
        screen.validate();
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

    public static void SpaceFlagPage() {
        ImageIcon flagIcon, flagIcon2, character, character2, srver; // server : Client VS Server(PC)
        Image image;
        JLabel flagLabel, flagLabel2, characterLabel2, srverLabel;
        
        SpacePanel.setVisible(true);
        screen.setTitle("SpaceFlagPage"+"-Player "+player);
        SpacePanel.setLayout(null);
        SpacePanel.setFocusable(true);
        SpacePanel.requestFocus(); 
        if(player == 1){
            Connection(8886,"");
        }else if(player == 2){
            Connection(8887,"");
        }
        System.out.println("Player: " + player);
        System.out.println("role = " + role);
        flagIcon = new ImageIcon("Img/Flag.png");
        image = flagIcon.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        flagIcon = new ImageIcon(image);

        flagIcon2 = flagIcon;

        character = new ImageIcon("Img/Player" + player + "-" + role + ".png");
        image = character.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        character = new ImageIcon(image);

        // Get another Player Inform
        // Problem : Connected Server but two client not connected!!

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

        
        System.out.println("anPlayer = " + anPlayer);
        System.out.println("anRole = " + anRole);
        character2 = new ImageIcon("Img/Player" + Integer.toString(anPlayer) + "-" + Integer.toString(anRole) + ".png");
        image = character2.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        character2 = new ImageIcon(image);

        srver = new ImageIcon("Img/Player" + 2 + "-" + 1 + ".png");
        image = srver.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        srver = new ImageIcon(image);

        flagLabel = new JLabel(flagIcon);
        flagLabel2 = new JLabel(flagIcon2);
        characterLabel = new JLabel(character);
        characterLabel2 = new JLabel(character2);
        srverLabel = new JLabel(srver);

        flagLabel.setBounds(200, 0, 50, 70);
        flagLabel2.setBounds(600, 0, 50, 70);

        // RMI Function
        // try {
        // y = o.Getmove(anPlayer);
        // } catch (Exception error) {
        // System.out.println(error);
        // }

        if (player == 1) {
            characterLabel.setBounds(600, x, 50, 70);
            characterLabel2.setBounds(200, y, 50, 70);

        } else {
            characterLabel.setBounds(200, x, 50, 70);
            characterLabel2.setBounds(600, y, 50, 70);

        }

        KeyListener listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // keyPressed = Invoked when a physical key is pressed down. Uses KeyCode, int
                // output
                System.out.println("KeyPressed");

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    System.out.println("move");

                    if (player == 1){
                        characterLabel.setBounds(600, x, 50, 70);
                        characterLabel2.setBounds(200, y, 50, 70);
                    }else if (player == 2){
                        characterLabel.setBounds(200, x, 50, 70);
                        characterLabel2.setBounds(600, y, 50, 70);
                    }
                        
                    

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(player == 1)
                    x -= 5; // Change the moving speed Reference 20
                else if(player == 2)
                    y -= 5;
            }
        };
        // Thread playerThread = new Thread(new Runnable(){
        // public void run(){
        // if (Player == 1) {
        // characterLabel.setBounds(600, x, 50, 70);
        // } else {
        // characterLabel.setBounds(200, x, 50, 70);
        // }
        // }
        // });

        // Thread svrThread = new Thread(new Runnable(){
        // public void run(){
        // while(s!=0){
        // if (Player == 1) {
        // srverLabel.setBounds(200, s, 50, 70);
        // } else {
        // srverLabel.setBounds(600, s, 50, 70);
        // }

        // try {
        // s = o.srverMove(10);
        // Thread.sleep(500);
        // } catch (Exception error) {
        // System.out.println("s :" + error);
        // }
        // }
        // }
        // });

        // playerThread.start();
        // svrThread.start();
        SpacePanel.add(flagLabel);
        SpacePanel.add(flagLabel2);
        SpacePanel.add(characterLabel);
        SpacePanel.add(characterLabel2);
        SpacePanel.add(srverLabel);
        SpacePanel.addKeyListener(listener);
        screen.add(SpacePanel);

    }

    private static class ButtonHandler implements ActionListener {
        private Integer LastClick = -1;
        Page page;

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
                SpaceFlagPage();
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