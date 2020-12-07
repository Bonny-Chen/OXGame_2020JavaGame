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
    public static Interface o = null;
    // public static Thread thread;
    public static SocketSrver socketSrver;
    public static SocketClient socketClient;
    private static boolean onlineCheck = false;

    public static JPanel PlayerPanel = new JPanel(); // PlayerSelectionPage's Panel
    public static JPanel SpacePanel = new JPanel(); // SpaceFlagPage's Panel
    private static JPanel jp;
    public static JFrame screen = new JFrame();
    public static JLabel characterLabel, characterLabel2P1, characterLabel2P2;
    public static Integer player = -1;
    public static Integer onlinePlayer = 0;
    public static Integer Character = 9;
    public static ImageIcon icon;
    public static Integer anRole = 9;
    public static Integer anPlayer = 100;
    public static int inform[] = new int[2];
    public static int x = 500;
    public static int P1y = 500;
    public static int P2y = 500;
    public static int s = 500; // use for recording server moving
    private static ImageIcon player1;
    private static JLabel player1Lab = new JLabel();
    private static JPanel glassPanel = new JPanel();
    private static JPanel GPanel = new JPanel(); // Use For WinPage()
    private static JPanel GPanel2 = new JPanel(); // Use For OXGamePage()
    private static JLabel blockingLabel = new JLabel();
    private static JLabel TurnMsg = new JLabel("Not Your Turn");
    private static ImageIcon waitiIcon = new ImageIcon("Img/WaitingMsg.png");
    private static JLabel WaitingMsg = new JLabel(waitiIcon);
    public static boolean windowClosed = false;
    public static int role = -1; // Use for SpaceFlagPage (Player's role Get)
    public static int selfrole = -1;
    public static int theRound = 0; // theRound = 1 is Player1's Round , theRound = 2 is Player2's Round , theRound = 0 is default(Error)
    public static int winner = 0; // Use for SpaceFlagPage to determine who is the winner , winner = 1 Player 1 win , winner = 2 Player 2 win , winner = 0 no one win (Default)
    public static int gridChecked = 0; // gridChecked = 0 is Not Clicked (Default) , gridChecked = 1 is Player1 clicked , gridChecked = 2 is Player2 clicked
    public static ButtonImageCreate[] Btn = new ButtonImageCreate[9];
    public static int IsYourTurn = 0; // IsYourTurn = 0 is not your turn , 1 is your turn
    public static int OKBtnCount = 0;
    public static int timeOn = 0;

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
         //OXGamePage();
        screen.validate();

    }

    public static boolean ClosedWindow() {
        return windowClosed;
    }

    private static void Reset() {
        player = -1;
        onlinePlayer = 0;
        Character = 9;
        anRole = 9;
        anPlayer = 100;
    }

    public static void Connection(int Port, String Message) {
        try {
            // Creates a socket channel
            SocketChannel sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.connect(new InetSocketAddress("127.1", Port));
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
                if (Port == 8884 || Port == 8885 || Port == 8888 || Port == 8889 || Port == 2000 || Port == 2001) {
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
                }

                if (Port == 8890 && new String(b.array(), 0, len).startsWith("Server") == false) {
                    P1y = Integer.parseInt(new String(b.array(), 0, len));
                } else if (Port == 8891 && new String(b.array(), 0, len).startsWith("Server") == false) {
                    P2y = Integer.parseInt(new String(b.array(), 0, len));
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
                    if (new String(b.array(), 0, len).startsWith("10")
                            || new String(b.array(), 0, len).startsWith("20")) {
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
        socketSrver = new SocketSrver();
        if (args.length < 2) {
            System.out.println("Usage: java MultiPortClient server_ip port ");
            System.exit(1);
        }
        Connection(Integer.parseInt(args[1]), "");
        Connection(8883, "");

        System.out.println("player = " + player);

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

        icon = new ImageIcon("Img/ox.png");
        icon.setImage(icon.getImage().getScaledInstance(398, 398, Image.SCALE_DEFAULT));
        GridLabel.setIcon(icon);
        GridLabel.setBounds(250, 50, 500, 500);
        GamePanel.add(GridLabel);

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
            Btn[x] = new ButtonImageCreate(x, "", "", 274 + (x * 120), 123, 115, 115);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }
        for (int x = 3; x < 6; x++) {
            tmpx = x % 3;
            Btn[x] = new ButtonImageCreate(x, "", "", 274 + (tmpx * 120), 240, 115, 115);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }
        for (int x = 6; x < 9; x++) {
            tmpx = x % 3;
            Btn[x] = new ButtonImageCreate(x, "", "", 274 + (tmpx * 120), 357, 115, 115);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }

        // Decide IsYourTurn
        Thread roundThread = new Thread(new Runnable() {
            int win = 0;

            public void run() {
                // Get start turn
                theRound = 1;
                while (winner == 0) {
                    // Check win
                    winner = win;
                    win = CheckWin();
                    // NoYourTurn
                    if (theRound != player) {
                        // Block the player windows and Check current turn
                        BlockPage(player, selfrole);
                        Connection(2002, ""); // Update current turn
                        if (win == 1 || win == 2) {
                            if (OKBtnCount < 3)
                                WinPage(win);
                            else if (OKBtnCount >= 3) {
                                GamePanel.setVisible(false);
                                GPanel2.setVisible(false);
                                screen.remove(GPanel2);
                                SpaceFlagPage();
                            }
                            break;
                        }
                    } else if (theRound == player) {
                        // IsYourTurn
                        if (win == 0) {
                            GPanel2.setVisible(false);
                            screen.remove(GPanel2); // Remove GlassPanel(GPanel2)
                            if (player == 2)
                                System.out.println("is my turn");
                        } else if (win == 1 || win == 2) {
                            if (OKBtnCount < 3)
                                WinPage(win);
                            else if (OKBtnCount >= 3) {
                                SpaceFlagPage();
                            }
                            break;
                        }

                        // continue;
                    }
                    // Display O or X of each grid
                    for (int i = 0; i < 9; i++) {
                        // Get the O or X with specific number
                        Connection(2000, Integer.toString(i));

                        if (gridChecked == 10) { // 10 show that this grid drawn from player 1
                            Btn[i].setText("O");
                            Btn[i].setFont(new Font("Arial", Font.PLAIN, 72));
                            Btn[i].setForeground(Color.RED);
                        } else if (gridChecked == 20) { // 20 show that this grid drawn from player 2
                            Btn[i].setText("X");
                            Btn[i].setFont(new Font("Arial", Font.PLAIN, 72));
                            Btn[i].setForeground(Color.BLUE);
                        }

                        gridChecked = 0; // reset gridChecked
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
        if (Btn[0].getText().compareTo("O") == 0 && Btn[1].getText().compareTo("O") == 0
                && Btn[2].getText().compareTo("O") == 0) {
            return 1;
        } else if (Btn[0].getText().compareTo("X") == 0 && Btn[1].getText().compareTo("X") == 0
                && Btn[2].getText().compareTo("X") == 0) {
            return 2;
        }

        if (Btn[3].getText().compareTo("O") == 0 && Btn[4].getText().compareTo("O") == 0
                && Btn[5].getText().compareTo("O") == 0) {
            return 1;
        } else if (Btn[3].getText().compareTo("X") == 0 && Btn[4].getText().compareTo("X") == 0
                && Btn[5].getText().compareTo("X") == 0) {
            return 2;
        }

        if (Btn[6].getText().compareTo("O") == 0 && Btn[7].getText().compareTo("O") == 0
                && Btn[8].getText().compareTo("O") == 0) {
            return 1;
        } else if (Btn[6].getText().compareTo("X") == 0 && Btn[7].getText().compareTo("X") == 0
                && Btn[8].getText().compareTo("X") == 0) {
            return 2;
        }

        if (Btn[0].getText().compareTo("O") == 0 && Btn[4].getText().compareTo("O") == 0
                && Btn[8].getText().compareTo("O") == 0) {
            return 1;
        } else if (Btn[0].getText().compareTo("X") == 0 && Btn[4].getText().compareTo("X") == 0
                && Btn[8].getText().compareTo("X") == 0) {
            return 2;
        }

        if (Btn[2].getText().compareTo("O") == 0 && Btn[4].getText().compareTo("O") == 0
                && Btn[6].getText().compareTo("O") == 0) {
            return 1;
        } else if (Btn[2].getText().compareTo("X") == 0 && Btn[4].getText().compareTo("X") == 0
                && Btn[6].getText().compareTo("X") == 0) {
            return 2;
        }

        if (Btn[0].getText().compareTo("O") == 0 && Btn[3].getText().compareTo("O") == 0
                && Btn[6].getText().compareTo("O") == 0) {
            return 1;
        } else if (Btn[0].getText().compareTo("X") == 0 && Btn[3].getText().compareTo("X") == 0
                && Btn[6].getText().compareTo("X") == 0) {
            return 2;
        }

        if (Btn[1].getText().compareTo("O") == 0 && Btn[4].getText().compareTo("O") == 0
                && Btn[7].getText().compareTo("O") == 0) {
            return 1;
        } else if (Btn[1].getText().compareTo("X") == 0 && Btn[4].getText().compareTo("X") == 0
                && Btn[7].getText().compareTo("X") == 0) {
            return 2;
        }

        if (Btn[2].getText().compareTo("O") == 0 && Btn[5].getText().compareTo("O") == 0
                && Btn[8].getText().compareTo("O") == 0) {
            return 1;
        } else if (Btn[2].getText().compareTo("X") == 0 && Btn[5].getText().compareTo("X") == 0
                && Btn[8].getText().compareTo("X") == 0) {
            return 2;
        }

        for (int i = 0; i < 9; i++) {
            if (Btn[i].getText().compareTo("") == 0) {
                unclicked++;
            }
        }

        if (unclicked == 0) {
            return 3;
        }

        return 0;
    }

    public static void BlockPage(int p, int r) {
        GPanel2.add(TurnMsg);
        GPanel2.setOpaque(false);
        GPanel2.add(blockingLabel);
        TurnMsg.setFont(new Font("Serif", Font.BOLD, 48));
        TurnMsg.setForeground(Color.RED);
        GPanel2.addKeyListener(new KeyAdapter() {});
        GPanel2.addMouseListener(new MouseAdapter() {});
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

    }

    public static void SpaceFlagPage() {
        ImageIcon flagIcon, flagIcon2, character, character2, srver; // srver : Client VS Server(PC)
        Image image;
        JLabel flagLabel, flagLabel2, srverLabel;
        SpacePanel.setVisible(true);
        screen.setTitle("SpaceFlagPage" + "-Player " + player);
        SpacePanel.setLayout(null);

        // Detect Key Press
        addKeyBind(SpacePanel, "SPACE");
        // if (player == 1) {
        // Connection(8886, "");
        // } else if (player == 2) {
        // Connection(8887, "");
        // }

        flagIcon = new ImageIcon("Img/Flag.png");
        image = flagIcon.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        flagIcon = new ImageIcon(image);
        flagIcon2 = flagIcon;

        character = new ImageIcon("Img/Player" + player + "-" + selfrole + ".png");
        image = character.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        character = new ImageIcon(image);

        // Setting another Player
        // if (player == 1) {
        // anPlayer = 2;
        // Connection(8887, "");
        // anRole = role;
        // while (anRole == 9) {
        // Connection(8887, "");
        // anRole = role;
        // }
        // } else if (player == 2) {
        // anPlayer = 1;
        // Connection(8886, "");
        // anRole = role;
        // while (anRole == 9) {
        // Connection(8886, "");
        // anRole = role;
        // }
        // }

        character2 = new ImageIcon("Img/Player" + Integer.toString(anPlayer) + "-" + Integer.toString(anRole) + ".png");
        image = character2.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        character2 = new ImageIcon(image);

        // Setting Srver Player
        srver = new ImageIcon("Img/Player" + 2 + "-" + 1 + ".png");
        image = srver.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        srver = new ImageIcon(image);

        flagLabel = new JLabel(flagIcon);
        flagLabel2 = new JLabel(flagIcon2);
        characterLabel = new JLabel(character);
        characterLabel2P1 = new JLabel(character2);
        characterLabel2P2 = new JLabel(character2);
        srverLabel = new JLabel(srver);

        flagLabel.setBounds(200, 0, 50, 70);
        flagLabel2.setBounds(600, 0, 50, 70);

        if (player == 1) {
            characterLabel.setBounds(200, x, 50, 70);
            characterLabel2P2.setBounds(600, P2y, 50, 70);

        } else if (player == 2) {
            characterLabel.setBounds(200, x, 50, 70);
            characterLabel2P1.setBounds(600, P1y, 50, 70);

        }

        Thread getPostThread = new Thread(new Runnable() {
            public void run() {
                while (P1y != 0) {
                    Connection(8890, "");
                    characterLabel2P1.setBounds(600, P1y, 50, 70);
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (P2y != 0) {
                    WinPage(1);
                }
                System.out.println("End of Thread");

            }
        });

        Thread p2Thread = new Thread(new Runnable() {
            public void run() {
                while (P2y != 0) {
                    Connection(8891, "");
                    characterLabel2P2.setBounds(600, P2y, 50, 70);
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (P1y != 0) {
                    WinPage(2);
                    // JOptionPane.showMessageDialog(SpacePanel,"Player 2 win");
                }
                System.out.println("End of Thread");

            }
        });
        if (anPlayer == 1)
            getPostThread.start();
        if (anPlayer == 2)
            p2Thread.start();
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
        // svrThread.start();

        SpacePanel.add(flagLabel);
        SpacePanel.add(flagLabel2);
        SpacePanel.add(characterLabel);
        SpacePanel.add(characterLabel2P1);
        SpacePanel.add(characterLabel2P2);
        SpacePanel.add(srverLabel);
        screen.add(SpacePanel);
        screen.validate();

    }

    public static void WinPage(int winner) {
        ImageIcon icon = new ImageIcon("Img/logo.png");
        JLabel panel = new JLabel();
        ButtonImageCreate playAgainBtn, exitBtn;
        GPanel.setLayout(null);
        if (winner == 1) {
            icon = new ImageIcon("Img/Player1win.png");
        } else if (winner == 2) {
            icon = new ImageIcon("Img/Player2win.png");
        }
        panel.setIcon(icon);
        panel.setBounds(240, 100, 456, 343);
        GPanel.add(blockingLabel);

        playAgainBtn = new ButtonImageCreate(30, "Img/playAgainBtn.png", "Img/playAgainBtn-click.png", 310, 290, 154,
                65);
        playAgainBtn.addActionListener(BH);
        playAgainBtn.addMouseListener(MH);

        exitBtn = new ButtonImageCreate(31, "Img/ExitBtn.png", "Img/ExitBtn-click.png", 470, 290, 154, 65);
        exitBtn.addActionListener(BH);
        exitBtn.addMouseListener(MH);

        GPanel.remove(WaitingMsg);
        GPanel.setOpaque(false);
        GPanel.add(playAgainBtn);
        GPanel.add(exitBtn);
        GPanel.add(panel);
        GPanel.addKeyListener(new KeyAdapter() {});
        GPanel.addMouseListener(new MouseAdapter() {});
        screen.setGlassPane(GPanel);
        blockingLabel.requestFocus();
        GPanel.setVisible(true);
        screen.validate();
    }

    static Action spaceAction = new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (x != 0) {
                // KeyPressed Action
                x -= 20;
                if (player == 1) {
                    Connection(8888, Integer.toString(x));
                } else if (player == 2) {
                    Connection(8889, Integer.toString(x));
                }

                characterLabel.setBounds(200, x, 50, 70);
            } else if (x == 0) {
                if (player == 1) {
                    winner = 1;
                    WinPage(1);
                } else if (player == 2) {
                    winner = 2;
                    WinPage(2);
                    // JOptionPane.showMessageDialog(SpacePanel,"Player 2 win");
                }
            }
        }
    };

    private static final String DISABLE_CLICKER = "disableClicker";

    private static void addKeyBind(JComponent contentPane, String key) {
        InputMap iMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap aMap = contentPane.getActionMap();
        iMap.put(KeyStroke.getKeyStroke(key), DISABLE_CLICKER);
        aMap.put(DISABLE_CLICKER, spaceAction);
    }

    private static class ButtonHandler implements ActionListener {
        private Integer LastClick = -1;
        countThread ct = new countThread();

        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            ButtonImageCreate tmpBtn = myBtn;
            // ?
            if (ExistsLastClick() >= 0 && ExistsLastClick() != 4 && ExistsLastClick() != 13) {
                Character = ExistsLastClick();
                System.out.println("LastClick " + ExistsLastClick());
                if (Character >= 10 && Character <= 12) {
                    Character = Character - 10;
                    tmpBtn = Button[ExistsLastClick() - 10];
                    tmpBtn.setIcon(tmpBtn.icon);
                    tmpBtn.setClick(0);
                } else if (Character >= 0 && Character <= 8) {
                    tmpBtn = Btn[ExistsLastClick()]; // Control LastClick Button
                    tmpBtn.setIcon(tmpBtn.icon);
                    tmpBtn.setClick(0); // Formate the setting of Button Click
                }

            }

            if (theRound == 1 && player == 1) {
                if (myBtn.getID() <= 8 && myBtn.getID() >= 0) {
                    // Determind the grid is available to Click
                    // Connection(2000,Integer.toString(myBtn.getID()));
                    if (myBtn.IsClicked() != 1 && gridChecked == 0) {
                        myBtn.setText("O");
                        myBtn.setFont(new Font("Arial", Font.PLAIN, 72));
                        myBtn.setForeground(Color.RED);
                        Connection(2001, Integer.toString(myBtn.getID() + 10));
                    }
                }
                Connection(8882, "");
            } else if (theRound == 2 && player == 2) {
                if (myBtn.getID() <= 8 && myBtn.getID() >= 0) {
                    // Connection(2000,Integer.toString(myBtn.getID()));
                    if (myBtn.IsClicked() != 1 && gridChecked == 0) {
                        myBtn.setText("X");
                        myBtn.setFont(new Font("Arial", Font.PLAIN, 72));
                        myBtn.setForeground(Color.BLUE);
                        Connection(2001, Integer.toString(myBtn.getID() + 20));

                    }
                }
                Connection(8882, "");
            }

            // Switch to HomePage
            if (myBtn.getID() == 14) {
                PlayerPanel.setVisible(false);
                HomePage();
            } else if (myBtn.getID() == 13) { // OKBtn
                try {
                    ct.start();
                } catch (Exception E) {
                    System.out.println("Thread started already");
                }
                OKBtnCount++;
            } else if (myBtn.getID() == 30) { // PlayAgainBtn
                GPanel.setVisible(false);
                SpacePanel.setVisible(false);
                HomePage();
            } else if (myBtn.getID() == 31) { // ExitBtn
                GPanel.setVisible(false);
                SpacePanel.setVisible(false);
                screen.dispose();
                System.out.println("JFrame Closed!");
                System.exit(0);
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
        if (this.ID >= 0 && this.ID <= 8) {
            this.setContentAreaFilled(true); // Remove Btn Background
            this.setFocusable(true);
            this.setBorderPainted(true); // Remove Btn Border
        } else {
            this.setContentAreaFilled(false); // Remove Btn Background
            this.setFocusable(false);
            this.setBorderPainted(false); // Remove Btn Border
        }

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

class countThread extends Thread {
    public void run() {
        try {
            Thread.sleep(1500);
            System.out.println("Clicked : " + Page.OKBtnCount);
            Page.PlayerPanel.setVisible(false);
            if (Page.player == 1) {
                System.out.println("Character : " + Integer.toString(Page.Character));
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
        } catch (Exception e) {
        }
    }
}