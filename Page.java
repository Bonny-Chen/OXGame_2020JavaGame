import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.rmi.*;

import java.net.*;
import java.io.*;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;

public class Page extends Thread {
    public static Interface o = null;
    // public static Thread thread;
    public static SocketSrver socketSrver;
    public static JPanel PlayerPanel = new JPanel(); // PlayerSelectionPage's Panel
    public static JPanel SpacePanel = new JPanel(); // SpaceFlagPage's Panel
    private static JPanel jp;
    public static JFrame screen = new JFrame();
    public static JLabel characterLabel;
    public static Integer player;
    public static Integer onlinePlayer=0;
    public static Integer Character = 9;
    public static Integer anRole = 9;
    public static Integer anPlayer;
    public static int inform[] = new int[2];
    public static int x = 500;
    public static int s = 500; // use for recording server moving
    private static ImageIcon player1;
    private static JLabel player1Lab = new JLabel();
    private static JPanel glassPanel = new JPanel();
    private static JLabel blockingLabel = new JLabel();
    private static JLabel WaitingMsg = new JLabel("Waiting For Client...");

    // private static JButton StartBtn = new JButton("START");
    // private static JButton HowToBtn = new JButton("HOW TO");
    int round = 0;

    Page() {
        // RMI Function
        // try {
        //     o = (Interface) Naming.lookup("rmi://127.0.0.1/OXGame");
        //     System.out.println("RMI server connected");
        //     player = o.GetPlayerNum();
        //     System.out.println("Player " + player + " login");

        // } catch (Exception e) {
        //     System.out.println("Server lookup exception: " + e.getMessage());
        // }

        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HomePage();
        screen.validate();

    }

    public static void main(String args[]) {
        Page page = new Page();
        socketSrver = new SocketSrver(); 
        glassPanel.setOpaque(false);
        glassPanel.add(blockingLabel);
        glassPanel.addMouseListener(new MouseAdapter() {});
        // glassPanel.addMouseMotionListener(new MouseMotionAdapter() {});
        glassPanel.addKeyListener(new KeyAdapter() {});
        screen.setGlassPane(glassPanel);
        while(true){
            // Block All Button Until Client Join
            if(!socketSrver.StableConnect()){
                screen.setTitle("Waiting For Connection");
                glassPanel.add(WaitingMsg);
                WaitingMsg.setFont(new Font("Serif", Font.BOLD, 48));
                WaitingMsg.setForeground(Color.RED);
                glassPanel.setVisible(true);
                blockingLabel.requestFocus();
            }
            else{
                screen.setTitle("HomePage");
                glassPanel.setVisible(false);
                // screen.remove(glassPanel);
            }
        }

    }

    public void run(){
        // System.out.println(socketSrver.StableConnect());
        
    }

    static ButtonImageCreate[] Button;
    static ButtonHandler BH = new ButtonHandler();
    static MouseHandler MH = new MouseHandler();

    public static void HomePage() {
        jp = new JPanel();
        JButton StartBtn = new JButton("START");
        JButton HowToBtn = new JButton("HOW TO");
        JButton ExitBtn = new JButton("EXIT");
        JLabel titLabel = new JLabel("OX Game");
        ImageIcon logoIcon = new ImageIcon("Img\\logo.png");
        JLabel ImgLabel = new JLabel();

        // Default Setting
        screen.setTitle("Home Page");
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
                screen.setTitle("Player Select");
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
        screen.setTitle("OXGame");
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
        screen.setTitle("PlayerSelectionPage");
        PlayerPanel.setBackground(Color.decode("#000339"));
        PlayerPanel.setLayout(null);

        Button = new ButtonImageCreate[6];
        for (int x = 0; x < Button.length; x++) {
            Button[x] = new ButtonImageCreate(x, "Img/Player1-" + Integer.toString(x + 1) + "UnSelected.png",
                    "Img/Player1-" + Integer.toString(x + 1) + ".png", (x + 1) * (x + 200) + (x - 50), 100, 170, 230);
            Button[x].addActionListener(BH);
            Button[x].addMouseListener(MH);
            PlayerPanel.add(Button[x]);
        }

        Button[4] = new ButtonImageCreate(4, "Img/OKBTN-UnSelected.png", "Img/OKBTN.png", 355, 450, 150, 70);
        Button[4].addActionListener(BH);
        Button[4].addMouseListener(MH);
        PlayerPanel.add(Button[4]);

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
        int Player, role, y = 500;
        
        SpacePanel.setVisible(true);
        screen.setTitle("SpaceFlagPage");
        SpacePanel.setLayout(null);
        // screen.addKeyListener(Spc);

        role = GetRole();
        Player = GetPlayer();

        flagIcon = new ImageIcon("Img/Flag.png");
        image = flagIcon.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        flagIcon = new ImageIcon(image);

        flagIcon2 = flagIcon;

        character = new ImageIcon("Img/Player" + Player + "-" + role + ".png");
        image = character.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        character = new ImageIcon(image);

        // Get another Player Inform
        // Problem : Connected Server but two client not connected!!

        if (Player == 1)
            anPlayer = 2;
        else
            anPlayer = 1;


        // RMI Function
        // try{
        //     anRole = o.GetInform(anPlayer);
        // }catch(Exception e){
        //     System.out.println("anRole error " + e);
        // }
        
        character2 = new ImageIcon("Img/Player" + anPlayer + "-" + anRole + ".png");
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
        //     y = o.Getmove(anPlayer);
        // } catch (Exception error) {
        //     System.out.println(error);
        // }

        if (Player == 1) {
            characterLabel.setBounds(600, x, 50, 70);
            characterLabel2.setBounds(200, y, 50, 70);

        } else {
            characterLabel.setBounds(200, x, 50, 70);
            characterLabel2.setBounds(600, y, 50, 70);

        }

        KeyListener listener = new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                // keyTyped = Invoked when a key is typed. Uses KeyChar, char output
                // if(e.getKeyCode() == KeyEvent.VK_SPACE ){
                // x -= 20;
                // characterLabel.setBounds(200, x, 50, 70);
                // }
        
            }
        
            @Override
            public void keyPressed(KeyEvent e) {
                // keyPressed = Invoked when a physical key is pressed down. Uses KeyCode, int
                // output
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (player == 1)
                        characterLabel.setBounds(200, x, 50, 70);
        
                    else
                        characterLabel.setBounds(600, x, 50, 70);
        
                }
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                x -= 5; // Change the moving speed Reference 20
                System.out.println("Player " + player + " move");
                // RMI Function
                // try {
                //     o.move((player + 1), x);
                // } catch (Exception le) {
                //     System.out.println(le);
                // }
                // if x == 0 -> win
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

    

    public static void SetPlayerInfor() {
        inform[player] = Character;
    }

    public static int GetPlayer() {
        return player;
    }

    public static int GetRole() {
        return Character;
    }

    private static class ButtonHandler implements ActionListener {
        private Integer LastClick = -1;

        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            ButtonImageCreate tmpBtn = myBtn;
            if (ExistsLastClick() >= 0 && ExistsLastClick() != 4) {

                System.out.println("LastClick " + ExistsLastClick());
                tmpBtn = Button[ExistsLastClick()]; // Control LastClick Button
                tmpBtn.setIcon(tmpBtn.icon);
                tmpBtn.setClick(0); // Formate the setting of Button Click
            }

            // Switch to HomePage
            if (myBtn.getID() == 5) {
                PlayerPanel.setVisible(false);
                HomePage();
            } else if (myBtn.getID() == 4) {            //OKBtn
                PlayerPanel.setVisible(false);
                // RMI Function
                // try {
                //     Character = o.PlayerSelection(GetPlayer(), ExistsLastClick());
                //     System.out.println("You chose Role " + Character);
                // } catch (Exception ke) {
                //     System.out.println(ke);
                // }

                // try {
                //     o.SetInform(player, Character);
                //     System.out.println("Player "+player +" chose "+Character);
                // } catch (Exception er) {
                //     System.out.println(er + " " + player);
                // }
                // Local Function
                // SetPlayerInfor();
                // System.out.println("SetPlayerInfor : "+ "[" + player +"]" + " = "
                // +inform[player]);

                // switch to GamePage()
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