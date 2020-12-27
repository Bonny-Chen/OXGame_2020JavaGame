
//************************************************************************
//*  Network Programming - Final Project (OXGame)                        *
//*  Program Name: Page.java                                             *
//*  Run Program Player 1: java Page 127.1 8880                          *
//*  Run Program Player 2: java Page 127.1 8881                          *
//*  The program connects to BR and receive message.					 *
//*  The program join an multicastgroup and receive message from there.  *
//*  2020.11.29                                                          *
//************************************************************************
import java.awt.*;

import javax.management.relation.Role;
import javax.swing.*;

import java.awt.event.*;
import java.net.*;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Page {
    private static boolean onlineCheck = false;

    public static JPanel PlayerPanel = new JPanel(); // PlayerSelectionPage's Panel
    public static JPanel SpacePanel = new JPanel(); // SpaceFlagPage's Panel
    private static JPanel jp;
    public static JFrame screen = new JFrame();
    public static JLabel characterLabel, characterLabel2P1, characterLabel2P2;
    public static Integer player = -1;
    public static Integer Character = 9;
    public static ImageIcon icon;
    public static Integer anRole = 9;
    public static Integer anPlayer = 100;
    public static int inform[] = new int[2];
    public static int x = 500;
    public static int P1y = 500;
    public static int P2y = 500;
    public static int inButtonHandler = 0;
    public static int OKBtnClicked = 0;
    private static JPanel glassPanel = new JPanel();
    private static JPanel GPanel = new JPanel(); // Use For WinPage()
    private static JPanel GPanel2 = new JPanel(); // Use For OXGamePage()
    private static JLabel blockingLabel = new JLabel();
    private static ImageIcon turnIcon = new ImageIcon("Img/TurnIcon.png");
    private static JLabel TurnMsg = new JLabel(turnIcon);
    private static ImageIcon waitiIcon = new ImageIcon("Img/WaitingMsg.png");
    private static JLabel WaitingMsg = new JLabel(waitiIcon);
    public static boolean windowClosed = false;
    public static int role = -1; // Use for SpaceFlagPage (Player's role Get)
    public static int selfrole = -1;
    public static int theRound = 0; // theRound = 1 is Player1's Round , theRound = 2 is Player2's Round , theRound
                                    // = 0 is default(Error)
    public static int winner = 0; // Use for SpaceFlagPage to determine who is the winner , winner = 1 Player 1
                                  // win , winner = 2 Player 2 win , winner = 0 no one win (Default)
    public static int gridChecked = 0; // gridChecked = 0 is Not Clicked (Default) , gridChecked = 1 is Player1 clicked
                                       // , gridChecked = 2 is Player2 clicked
    public static ButtonImageCreate[] Btn = new ButtonImageCreate[9];
    public static ButtonImageCreate[] ReBtn = new ButtonImageCreate[9]; // Use for RedoGrid()
    public static int OKBtnCount = 0;
    public static int[] record = new int[9];
    public static int isclicked = 0; // Use to monitor the click in RedoGrid()
    public static int redo = 0;
    public static int new_winner = 0;
    public static int checkwin_again = 0;
    Page() {
        screen.setVisible(true);
        screen.setResizable(false);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                windowClosed = true;
                if (player == 1) {
                    Connection(4041, "");
                } else if (player == 2) {
                    Connection(4042, "");
                }
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

    public static void Connection(int Port, String Message) {
        try {
            // Creates a socket channel
            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.connect(new InetSocketAddress("127.0.0.1", Port));
            // System.out.println("in Port : " + Port);
            // if the socket has connected, sc.finishConnect() will return false
            for (int loopcount = 0; !sc.finishConnect(); loopcount++) {
                // do something
                System.out.println("Loop count = " + loopcount);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
            // Connection established

            // messages from args[2] and args[3]
            for (int i = 1; i < 2; i++) // messages from args[2] and args[3]
            {
                // Send message to server
                ByteBuffer buffer = ByteBuffer.wrap("Client Login".getBytes());
                if (Port == 8884 || Port == 8885 || Port == 8888 || Port == 8889 || Port == 8890 || Port == 8891 || Port == 2000 || Port == 2001) {
                    // System.out.println("Message = " + Message);
                    buffer = ByteBuffer.wrap(Message.getBytes());
                }
                sc.write(buffer);

                // Receive message
                ByteBuffer b = ByteBuffer.allocate(1000);
                int len = sc.read(b); // read message from sc (non-blocking)
                while (len == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.err.println(e);
                    }
                    // System.out.print("+"); // do something
                    len = sc.read(b);
                }
                // System.out.println(new String(b.array(), 0, len) + " to Port " + Port);

                if (new String(b.array(), 0, len).startsWith("1") && Port == 8880) {
                    player = Integer.parseInt(new String(b.array(), 0, len));
                    if (player == 1) {
                        anPlayer = 2;
                    } else if (player == 2) {
                        anPlayer = 1;
                    }
                } else if (new String(b.array(), 0, len).startsWith("2") && Port == 8881) {
                    player = Integer.parseInt(new String(b.array(), 0, len));
                    if (player == 1) {
                        anPlayer = 2;
                    } else if (player == 2) {
                        anPlayer = 1;
                    }
                } else if (Port == 8886 || Port == 8887) {
                    if (new String(b.array(), 0, len).startsWith("0") || new String(b.array(), 0, len).startsWith("1")
                            || new String(b.array(), 0, len).startsWith("2")
                            || new String(b.array(), 0, len).startsWith("9")) {
                        role = Integer.parseInt(new String(b.array(), 0, len));
                    }
                } else if (Port == 8889) {
                    if (new String(b.array(), 0, len).startsWith("0")
                            || new String(b.array(), 0, len).startsWith("1")) {
                        isclicked = Integer.parseInt(new String(b.array(), 0, len));
                        System.out.println("isclicked = "+isclicked);
                    }
                }

                if(Port == 8891){
                    if (new String(b.array(), 0, len).startsWith("1") || new String(b.array(), 0, len).startsWith("2") || new String(b.array(), 0, len).startsWith("3")) {
                        new_winner = Integer.parseInt(new String(b.array(), 0, len));
                    }
                }
                if (Port == 8892 && !new String(b.array(), 0, len).startsWith("Server")) {
                    winner = Integer.parseInt(new String(b.array(), 0, len));
                }

                if (Port == 8883) {
                    if (new String(b.array(), 0, len).startsWith("true")
                            || new String(b.array(), 0, len).startsWith("false")) {
                        onlineCheck = Boolean.parseBoolean(new String(b.array(), 0, len));
                    }
                }

                if (Port == 8882) {
                    if (new String(b.array(), 0, len).startsWith("2")
                            || new String(b.array(), 0, len).startsWith("1")) {
                        theRound = Integer.parseInt(new String(b.array(), 0, len));
                    }
                }

                if (Port == 2000 && !new String(b.array(), 0, len).startsWith("Server")) {
                    if (new String(b.array(), 0, len).startsWith("10") || new String(b.array(), 0, len).startsWith("20")
                            || new String(b.array(), 0, len).startsWith("0")) {
                        gridChecked = Integer.parseInt(new String(b.array(), 0, len));

                    }
                } else if (Port == 2002 && !new String(b.array(), 0, len).startsWith("Server")) {
                    theRound = Integer.parseInt(new String(b.array(), 0, len));
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
        if (args.length < 1) {
            System.out.println("Usage: java MultiPortClient port ");
            System.exit(1);
        }
        Connection(Integer.parseInt(args[0]), "");
        Connection(8883, "");

        // System.out.println("player = " + player);

        glassPanel.setOpaque(false);
        glassPanel.setLayout(null);
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
                WaitingMsg.setBounds(520, 500, 318, 76);
                glassPanel.setVisible(true);
                blockingLabel.requestFocus();
                Connection(8883, "");
            } else {
                screen.setTitle("HomePage" + "-Player " + player);
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
        JButton HowToBtn = new JButton("EXIT");
        JLabel titLabel = new JLabel("OX Game");
        ImageIcon logoIcon = new ImageIcon("Img\\OXGame.png");
        ImageIcon bgIcon = new ImageIcon("Img\\background.png");
        ImageIcon StartBtnIcon = new ImageIcon("Img\\Homepagestart-click.png");
        ImageIcon StartBtnIconClick = new ImageIcon("Img\\Homepagestart.png");
        JButton StartBtn = new JButton(StartBtnIcon);
        JLabel ImgLabel = new JLabel();
        JLabel ImgLabel2 = new JLabel();

        // Default Setting
        screen.setTitle("Home Page" + "-Player " + player);
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.setLayout(null); // for position

        // Show image
        ImgLabel.setBounds(150, 50, 487, 470); // Image setting(x,y,width,heigh)
        ImgLabel.setIcon(logoIcon);
        jp.add(ImgLabel);

        bgIcon.setImage(bgIcon.getImage().getScaledInstance(900, 630, Image.SCALE_DEFAULT));
        ImgLabel2.setBounds(0, 0, 900, 630); // Image setting(x,y,width,heigh)
        ImgLabel2.setIcon(bgIcon);

        StartBtnIcon.setImage(StartBtnIcon.getImage().getScaledInstance(224, 65, Image.SCALE_DEFAULT));

        // titLabel Setting (Title)
        titLabel.setBounds(350, 100, 300, 50); // Title setting (x,y,width,heigh)
        titLabel.setFont(new java.awt.Font("Dialog", 4, 50)); // Resize Font
        titLabel.setForeground(Color.decode("#FFFFFF"));

        // Background setting
        jp.setBackground(Color.decode("#888CCCA"));

        StartBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                screen.remove(jp); // Clear the screen
                screen.setTitle("Player Select" + "-Player " + player);
                PlayerSelectionPage();
                screen.validate();
            }
        });

        StartBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                StartBtn.setIcon(StartBtnIconClick);
            }

            public void mouseExited(MouseEvent e) {
                StartBtn.setIcon(StartBtnIcon);

            }
        });

        // button setting
        StartBtn.setFocusable(false); // inside line invisible
        StartBtn.setBorderPainted(false); // outside line invisible

        StartBtn.setBounds(315, 430, 224, 65); // set position
        StartBtn.setContentAreaFilled(false);

        jp.add(StartBtn);
        jp.add(ImgLabel2);

        screen.add(jp);
        screen.validate();
    }

    public static JPanel GamePanel = new JPanel(); // Game's Panel
    public static JLabel GridLabel = new JLabel(); // Game's Panel

    public static void OXGamePage() {
        int tmpx;
        JLabel player1label = new JLabel();
        JLabel player2label = new JLabel();

        if (player == 1) {
            Connection(8886, "");
        } else if (player == 2) {
            Connection(8887, "");
        }

        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel.setVisible(true);
        GamePanel.setLayout(null);

        icon = new ImageIcon("Img/background_GamePage.png");
        icon.setImage(icon.getImage().getScaledInstance(900, 630, Image.SCALE_DEFAULT));
        GridLabel.setIcon(icon);

        icon = new ImageIcon("Img/Player" + player + "-" + role + ".png");
        icon.setImage(icon.getImage().getScaledInstance(170, 230, Image.SCALE_DEFAULT));
        player1label.setIcon(icon);
        player1label.setBounds(50, 150, 170, 230);
        GamePanel.add(player1label);
        selfrole = role;
        // Setting another Player
        if (player == 1) {
            anPlayer = 2;
            Connection(8887, "");
            anRole = role;
            while (anRole == 9) {
                Connection(8887, "");
                anRole = role;
            }
        } else if (player == 2) {
            anPlayer = 1;
            Connection(8886, "");
            anRole = role;
            while (anRole == 9) {
                Connection(8886, "");
                anRole = role;
            }
        }
        icon = new ImageIcon("Img/Player" + Integer.toString(anPlayer) + "-" + Integer.toString(anRole) + ".png");
        icon.setImage(icon.getImage().getScaledInstance(170, 230, Image.SCALE_DEFAULT));
        player2label.setIcon(icon);
        player2label.setBounds(670, 150, 170, 230);
        GamePanel.add(player2label);
        for (int x = 0; x < 3; x++) {
            Btn[x] = new ButtonImageCreate(x, "", "", 265 + (x * 120), 140, 120, 118);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }
        for (int x = 3; x < 6; x++) {
            tmpx = x % 3;
            Btn[x] = new ButtonImageCreate(x, "", "", 265 + (tmpx * 120), 250, 120, 118);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }
        for (int x = 6; x < 9; x++) {
            tmpx = x % 3;
            Btn[x] = new ButtonImageCreate(x, "", "", 265 + (tmpx * 120), 365, 120, 118);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }
        GridLabel.setBounds(0, 0, 900, 630);
        GamePanel.add(GridLabel);

        // Decide IsYourTurn
        Thread roundThread = new Thread(new Runnable() {
            int win = 0;
            int check = 0;
            int currentround = 0;

            public void run() {
                // Get start turn
                theRound = 1;
                BlockPage(player, selfrole);
                while(true){
                    for (int i = 0; i < 9; i++) {
                        Connection(2000, Integer.toString(i));
                        if (gridChecked == 10 && check == 0 && record[i] == 0) { // 10 show that this grid drawn from player 1
                            record[i] = 1;
                            // Btn[i].setText("O");
                            icon = new ImageIcon("Img/o.png");
                            icon.setImage(icon.getImage().getScaledInstance(120, 118, Image.SCALE_DEFAULT));
                            Btn[i].setIcon(icon);
                            Btn[i].setClick(1);
                            // check = 1;
                        } else if (gridChecked == 20 && check == 0 && record[i] == 0) { // 20 show that this grid drawn from player 2
                            record[i] = 2;
                            // Btn[i].setText("X");
                            icon = new ImageIcon("Img/x.png");
                            icon.setImage(icon.getImage().getScaledInstance(120, 118, Image.SCALE_DEFAULT));
                            Btn[i].setIcon(icon);
                            Btn[i].setClick(1);
                            // check = 1;
                        }
                        gridChecked = 0;
                    }
    
                    winner = win;
                    win = CheckWin();
                    currentround = theRound;
                    if(winner == 0){
                        if (currentround != theRound) {
                            currentround = theRound;
                        }
                        if(currentround == player){
                            GPanel2.setVisible(false);
                        }else if(currentround != player){
                            GPanel2.setVisible(true);
                            Connection(2002, ""); // Update current turn    
                        }
                    }else if(winner == 1 || winner == 2 || winner == 3){
                        break;
                    }
                }
                
                if (OKBtnCount < 3||winner==3)
                    WinPage(win);
                else if (OKBtnCount >= 3) {
                    if (win != player) {
                        GPanel2.setVisible(false);
                        screen.remove(GPanel2);
                    }
                    if(winner==1||winner==2){
                        RedoGrid(win);
                    }
                    
                }
            }
        });

        roundThread.start();
        screen.add(GamePanel);
        screen.validate();
    }

    // This is use for OXGame Page to determine win status in each round , return 1
    // , 2 , 0 or 3
    // 1 represents winner is player1 , 2 represents winner is player 2
    // 0 represents no winner in this round
    // 3 represents this is peace round (all btn are clicked)
    public static int CheckWin() {
        int unclicked = 0;
        for (int i = 0; i < 9; i++) {
            // Connection(2000, Integer.toString(i));
            // if (gridChecked == 10) { // 10 show that this grid drawn from player 1
            //     record[i] = 1;
            // } else if (gridChecked == 20) { // 20 show that this grid drawn from player 2
            //     record[i] = 2;
            // }
            // gridChecked = 0;
            // System.out.println("record["+i+"] = "+record[i]);
            if (record[i] == 1 || record[i] == 2) {
                unclicked++;
            }
        }

        if (record[0] == 1 && record[1] == 1 && record[2] == 1) {
            return 1;
        } else if (record[0] == 2 && record[1] == 2 && record[2] == 2) {
            return 2;
        }

        if (record[3] == 1 && record[4] == 1 && record[5] == 1) {
            return 1;
        } else if (record[3] == 2 && record[4] == 2 && record[5] == 2) {
            return 2;
        }

        if (record[6] == 1 && record[7] == 1 && record[8] == 1) {
            return 1;
        } else if (record[6] == 2 && record[7] == 2 && record[8] == 2) {
            return 2;
        }

        if (record[0] == 1 && record[4] == 1 && record[8] == 1) {
            return 1;
        } else if (record[0] == 2 && record[4] == 2 && record[8] == 2) {
            return 2;
        }

        if (record[2] == 1 && record[4] == 1 && record[6] == 1) {
            return 1;
        } else if (record[2] == 2 && record[4] == 2 && record[6] == 2) {
            return 2;
        }

        if (record[0] == 1 && record[3] == 1 && record[6] == 1) {
            return 1;
        } else if (record[0] == 2 && record[3] == 2 && record[6] == 2) {
            return 2;
        }

        if (record[1] == 1 && record[4] == 1 && record[7] == 1) {
            return 1;
        } else if (record[1] == 2 && record[4] == 2 && record[7] == 2) {
            return 2;
        }

        if (record[2] == 1 && record[5] == 1 && record[8] == 1) {
            return 1;
        } else if (record[2] == 2 && record[5] == 2 && record[8] == 2) {
            return 2;
        }

        if (unclicked == 9) {
            return 3;
        }else{
            return 0;
        }
        
    }

    public static void BlockPage(int p, int r) {
        GPanel2.add(TurnMsg);
        GPanel2.setOpaque(false);
        GPanel2.add(blockingLabel);
        GPanel2.addKeyListener(new KeyAdapter() {
        });
        GPanel2.addMouseListener(new MouseAdapter() {
        });
        screen.setGlassPane(GPanel2);
        blockingLabel.requestFocus();
        GPanel2.setVisible(true);
        screen.validate();
    }

    public static void PlayerSelectionPage() {
        ImageIcon bgIcon = new ImageIcon("Img\\background_playerselectionpage.png");
        JLabel ImgLabel2 = new JLabel();

        PlayerPanel.setVisible(true);
        screen.setTitle("PlayerSelectionPage" + "-Player " + player);
        bgIcon.setImage(bgIcon.getImage().getScaledInstance(900, 630, Image.SCALE_DEFAULT));
        ImgLabel2.setBounds(0, 0, 900, 630); // Image setting(x,y,width,heigh)
        ImgLabel2.setIcon(bgIcon);

        PlayerPanel.setLayout(null);
        Button = new ButtonImageCreate[6];
        for (int x = 0; x < Button.length; x++) {
            Button[x] = new ButtonImageCreate(10 + x, "Img/Player1-" + Integer.toString(x) + "UnSelected.png",
                    "Img/Player1-" + Integer.toString(x) + ".png", (x + 1) * (x + 200) + (x - 50), 100, 170, 230);
            Button[x].addActionListener(BH);
            Button[x].addMouseListener(MH);
            PlayerPanel.add(Button[x]);
        }

        // OK Button
        Button[4] = new ButtonImageCreate(13, "Img/OKBTN-UnSelected.png", "Img/OKBTN.png", 330, 450, 224, 65);
        Button[4].addActionListener(BH);
        Button[4].addMouseListener(MH);
        PlayerPanel.add(Button[4]);

        // Back To Home Button
        Button[5] = new ButtonImageCreate(14, "Img/Back.png", "Img/Back.png", 30, 30, 50, 50);
        Button[5].addActionListener(BH);
        Button[5].addMouseListener(MH);
        PlayerPanel.add(Button[5]);

        PlayerPanel.add(ImgLabel2);
        screen.add(PlayerPanel);
        Thread Btnlooking = new Thread(new Runnable() {
            public void run() {
                while (OKBtnClicked == 0) {
                    System.out.println("Btnlooking");
                    if (OKBtnClicked == 1) {
                        // System.out.println("OKBtnClicked");
                        try {
                            countThread ct = new countThread();
                            new Thread(ct).start();
                        } catch (Exception E) {
                            // System.out.println("Thread started already " + E);
                        }
                    }
                }
            }
        });
        Btnlooking.start();

    }

    public static void RedoGrid(int winner) {
        int thisTurn = 0;
        int count = 0;
        redo = 1;
        if (winner == 1) {
            theRound = 2;
        } else if (winner == 2) {
            theRound = 1;
        }

        Connection(8889,"0");           // Set isclicked == 0
        gridChecked = 0;

        if(winner != player){
            icon = new ImageIcon("Img/background_RedoGrid.png");
            icon.setImage(icon.getImage().getScaledInstance(900, 630, Image.SCALE_DEFAULT));
            GridLabel.setIcon(icon);

            for (int i = 0; i < 9; i++) {
                if (record[i] == winner) {
                    Connection(8888, Integer.toString(i)); // Reset server grid[i] to 0
                    record[i] = 0;
                    Btn[i].setClick(0);
                    icon = new ImageIcon("");
                    Btn[i].setIcon(icon);
                }
            }

            GPanel2.setVisible(false);
            screen.remove(GPanel2);
            
        }else{
            for (int i = 0; i < 9; i++) {
                if (record[i] == winner) {
                    Connection(8888, Integer.toString(i)); // Reset server grid[i] to 0
                    record[i] = 0;
                    Btn[i].setClick(0);
                    icon = new ImageIcon("");
                    Btn[i].setIcon(icon);
                }
            }
        }
        while(new_winner == 0){  
            
            for (int i = 0; i < 9; i++) {
                Connection(2000, Integer.toString(i));
                if (gridChecked == 10 && record[i] == 0) { // 10 show that this grid drawn from player 1
                    record[i] = 1;
                    icon = new ImageIcon("Img/o.png");
                    icon.setImage(icon.getImage().getScaledInstance(120, 118, Image.SCALE_DEFAULT));
                    Btn[i].setIcon(icon);
                    Btn[i].setClick(1);
                } else if (gridChecked == 20 && record[i] == 0) { // 20 show that this grid drawn from player 2
                    record[i] = 2;
                    icon = new ImageIcon("Img/x.png");
                    icon.setImage(icon.getImage().getScaledInstance(120, 118, Image.SCALE_DEFAULT));
                    Btn[i].setIcon(icon);
                    Btn[i].setClick(1);
                }
                gridChecked = 0;
            }

            if(isclicked == 1 && checkwin_again == 0){
                new_winner = CheckWin();
                checkwin_again = 1;
            }     
            if(checkwin_again == 1 && thisTurn == 1){
                if(new_winner == 0){
                    new_winner = CheckWin();
                    System.out.println("new_winner = "+new_winner);
                }
                while(count < 5 && new_winner == 0){
                    new_winner = CheckWin();
                    count++;
                }
                if(new_winner == 0){
                    new_winner = 3;
                }
            }
            if(isclicked == 1)
                thisTurn = 1;
            if(winner != player && new_winner != 0){
                Connection(8890,Integer.toString(new_winner));
                break;
            }else if(winner == player){
                Connection(8891,"Get");
            }
        }
        
        WinPage(new_winner);
    }


    public static void WinPage(int winner) {
        ImageIcon icon = new ImageIcon("Img/logo.png");
        JLabel panel = new JLabel();
        ButtonImageCreate HereText, HereBtn;
        GPanel.setLayout(null);
        if (winner == 1) {
            icon = new ImageIcon("Img/Player1win.png");
        } else if (winner == 2) {
            icon = new ImageIcon("Img/Player2win.png");
        } else if (winner == 3) {
            icon = new ImageIcon("Img/Draw.png");
        }
        panel.setIcon(icon);
        panel.setBounds(240, 100, 456, 343);

        HereText = new ButtonImageCreate(30, "Img/HereText.png", "Img/HereText.png", 430, 252, 68, 30);
        HereText.addActionListener(BH);
        HereText.addMouseListener(MH);
        GPanel.add(HereText);

        HereBtn = new ButtonImageCreate(31, "Img/Here.png", "Img/Here-clicked.png", 355, 290, 224, 65);
        HereBtn.addActionListener(BH);
        HereBtn.addMouseListener(MH);

        GPanel.add(HereBtn);
        GPanel.remove(WaitingMsg);
        GPanel.setOpaque(false);
        GPanel.add(panel);
        GPanel.add(blockingLabel);
        GPanel.addKeyListener(new KeyAdapter() {
        });
        GPanel.addMouseListener(new MouseAdapter() {
        });
        screen.setGlassPane(GPanel);
        blockingLabel.requestFocus();
        GPanel.setVisible(true);
        screen.validate();
    }

    private static class ButtonHandler implements ActionListener {
        private Integer LastClick = -1;
        private static String[] m1 = { "127.1", "8880" };
        private static String[] m2 = { "127.1", "8881" };

        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            ButtonImageCreate tmpBtn = myBtn;
            inButtonHandler = 1;
            // ?
            if (ExistsLastClick() >= 0 && ExistsLastClick() != 4 && ExistsLastClick() != 13) {
                Character = ExistsLastClick();
                // System.out.println("LastClick " + ExistsLastClick());
                if (Character >= 10 && Character <= 12) {
                    Character = Character - 10;
                    tmpBtn = Button[ExistsLastClick() - 10];
                    tmpBtn.setIcon(tmpBtn.icon);
                    tmpBtn.setClick(0);
                }

            }

            if (theRound == 1 && player == 1) {
                if (myBtn.getID() <= 8 && myBtn.getID() >= 0 && gridChecked == 0 && myBtn.IsClicked() == 0) {
                    System.out.println("in");
                    icon = new ImageIcon("Img/o.png");
                    icon.setImage(icon.getImage().getScaledInstance(120, 118, Image.SCALE_DEFAULT));
                    myBtn.setIcon(icon);
                    // isclicked = 1;
                    Connection(8889,"1");
                    Connection(2001, Integer.toString(myBtn.getID() + 10));
                    Connection(2000,Integer.toString(myBtn.getID()));
                    System.out.println("gridChecked = "+gridChecked);
                    if( gridChecked != 10){
                        Connection(2001, Integer.toString(myBtn.getID() + 10));
                        Connection(2000,Integer.toString(myBtn.getID()));
                    }
                }
                Connection(8882, ""); // update theRound
            } else if (theRound == 2 && player == 2) {
                if (myBtn.getID() <= 8 && myBtn.getID() >= 0 && gridChecked == 0 && myBtn.IsClicked() == 0) {
                    System.out.println("in");
                    icon = new ImageIcon("Img/x.png");
                    icon.setImage(icon.getImage().getScaledInstance(120, 118, Image.SCALE_DEFAULT));
                    myBtn.setIcon(icon);
                    // isclicked = 1;
                    Connection(8889,"1");
                    Connection(2001, Integer.toString(myBtn.getID() + 20));
                    Connection(2000,Integer.toString(myBtn.getID()));
                    System.out.println("gridChecked = "+gridChecked);
                    if( gridChecked != 20){
                        Connection(2001, Integer.toString(myBtn.getID() + 20));
                        Connection(2000,Integer.toString(myBtn.getID()));
                    }
                }
                Connection(8882, "");
            }

            // Switch to HomePage
            if (myBtn.getID() == 14) {
                PlayerPanel.setVisible(false);
                HomePage();
            } else if (myBtn.getID() == 13) { // OKBtn

                if (OKBtnClicked == 0) {
                    OKBtnClicked = 1;
                }
                OKBtnCount++;
            } else if (myBtn.getID() == 30) { // HereText
                GPanel.setVisible(false);
                SpacePanel.setVisible(false);
                if (player == 1) {
                    Connection(4041, "");
                } else if (player == 2) {
                    Connection(4042, "");
                }
                screen.dispose();
                // System.out.println("JFrame Closed!");
                System.exit(0);
            }

            if (myBtn.getID() >= 9)
                myBtn.setIcon(myBtn.iconHover);

            if (myBtn.IsClicked() == 1 && myBtn.getID() >= 9) {
                myBtn.setIcon(myBtn.icon);
                myBtn.setClick(0);
            } else {
                myBtn.setClick(1);
            }

            if (myBtn.getID() >= 9)
                getLastClick(myBtn.getID());

            // System.out.println(myBtn.getID() + " was clicked");
            inButtonHandler = 0;
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
            if (myBtn.getID() >= 9)
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
        // if(this.ID >= 8)
        // this.Clicked = Clicked;
        // else if (this.ID >= 0 && this.ID <= 8)
        // this.Clicked = 1;
        this.Clicked = Clicked;
    }

    public int IsClicked() {
        return Clicked;
    }

}

class countThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1500);
            // System.out.println("Clicked : " + Page.OKBtnCount);
            Page.PlayerPanel.setVisible(false);
            if (Page.player == 1) {
                // System.out.println("Character : " + Integer.toString(Page.Character));
                Page.Connection(8884, Integer.toString(Page.Character));
            } else if (Page.player == 2) {
                Page.Connection(8885, Integer.toString(Page.Character));
            }
            // SpaceFlagPage();
            Page.OXGamePage();
            if (Page.OKBtnCount >= 3) {
                // Mode On
                System.out.println("mode on");
            }
            // System.out.println("ct closed");
        } catch (Exception e) {
        }
    }
}