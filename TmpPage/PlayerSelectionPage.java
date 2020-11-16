import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.rmi.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

<<<<<<< HEAD:TmpPage/PlayerSelectionPage.java
public class PlayerSelectionPage {
    public static Interface		o = null;
    // Main connect = new Connection
    EmptyGUI window = new EmptyGUI();
    private JPanel PlayerPanel;
    public static Integer player;
    public static Integer Character = 9;
    public static int inform[] = new int[2];
    ButtonImageCreate[] Button;
    ButtonHandler BH = new ButtonHandler();
    MouseHandler MH = new MouseHandler();
    // public static Interface o = Main.Connection();
    public static void Connection(){
        try
	    {
	    	o = (Interface) Naming.lookup("rmi://127.0.0.1/OXGame");
            System.out.println("RMI server connected");
            player = o.GetPlayerNum();
            
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Server lookup exception: " + e.getMessage());
        }
    }
    PlayerSelectionPage() {
        
        window.setTitle("PlayerSelectionPage");

        Connection();
        PlayerPanel = new JPanel();
        PlayerPanel.setBackground(Color.decode("#000339"));
        PlayerPanel.setLayout(null);
=======
public class PlayerSelectionPage extends JFrame {
    private JPanel jp;
    private PlayerSelectionPage screen;
<<<<<<< HEAD
    public static void main(String[] args) {
=======

    public static void main() {
>>>>>>> main
        PlayerSelectionPage GUI = new PlayerSelectionPage();
        GUI.Init();
    }

    public void Init() {
        screen = new PlayerSelectionPage();
        screen.setTitle("HomePage");
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // First Page
        PlayerSelectionPage();
        screen.validate();
    }

    ButtonImageCreate[] Button;
    ButtonHandler BH = new ButtonHandler();
    MouseHandler MH = new MouseHandler();

    public void PlayerSelectionPage() {
        jp = new JPanel();
        jp.setBackground(Color.decode("#299666"));
        jp.setLayout(null);
>>>>>>> da8e34b57e5806e7f6dea635ffc9ffa65dfbb3d0:PlayerSelectionPage.java
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
        window.add(PlayerPanel);
        window.validate();
    }

    public static int GetPlayer(){
        return player;
    }

    public static int GetRole(){
        return Character;
    }

<<<<<<< HEAD:TmpPage/PlayerSelectionPage.java
    public static void SetPlayerInfor(){
       inform[player] = Character;
       System.out.println(inform[player]);
    }
    public static int GetPlayerInfor(){
        return inform[player];
    }
=======
<<<<<<< HEAD
    public void HomePage() {
        
        jp = new JPanel();
        JButton     StartBtn = new JButton("START");
        JButton     HowToBtn = new JButton("HOW TO");
        JButton     ExitBtn = new JButton("EXIT");
        JLabel      titLabel = new JLabel("OX Game");
        ImageIcon   logoIcon = new ImageIcon("Img\\logo.png");
        JLabel      ImgLabel = new JLabel();

        // Default Setting
        screen.setTitle("Home Page");
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.setLayout(null);     //for position

        // Show image
        logoIcon.setImage(logoIcon.getImage().getScaledInstance(350,330,Image.SCALE_DEFAULT));
        ImgLabel.setBounds(280,100,500,500);            //Image setting(x,y,width,heigh)
        ImgLabel.setIcon(logoIcon);
        jp.add(ImgLabel);

        // titLabel Setting (Title)
        titLabel.setBounds(350, 100, 300, 50);                   // Title setting (x,y,width,heigh)
        titLabel.setFont(new java.awt.Font("Dialog", 4, 50));   // Resize Font
        titLabel.setForeground(Color.decode("#FFFFFF")); 
        jp.add(titLabel);

        // Background setting
        jp.setBackground(Color.decode("#888CCCA"));

        // HowTo button change
        HowToBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Btn Click");
                HowToBtn.setContentAreaFilled(false);   // Remove Btn Background
                HowToBtn.setFocusable(false);           //inside line invisible
                HowToBtn.setBorderPainted(false);       //outside line invisible
            }
        });
        StartBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Btn Click");
                screen.remove(jp);                                  // Clear the screen
                screen.setTitle("Player Select");
                PlayerSelectionPage();
                screen.validate();
            }
        });
        
        // button setting
        StartBtn.setFocusable(false);                           //inside line invisible               
        StartBtn.setBorderPainted(false);                       //outside line invisible
        HowToBtn.setFocusable(false);                                         
        HowToBtn.setBorderPainted(false);                      
        ExitBtn.setFocusable(false);                                       
        ExitBtn.setBorderPainted(false);                       

        StartBtn.setBounds(630, 300, 160, 30);                  //set position
        HowToBtn.setBounds(630,350,160,30);
        ExitBtn.setBounds(630, 400, 160, 30);
        StartBtn.setBackground(Color.decode("#EEEABA"));        //set background color
        HowToBtn.setBackground(Color.decode("#EEEABA"));
        ExitBtn.setBackground(Color.decode("#EEEABA"));
        StartBtn.setForeground(Color.decode("#FF12345"));        //set text color
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
    ImageIcon player1;
    JLabel    player1Lab = new JLabel();
    int round =0;
    
    public void OXGamePage(){
        jp = new JPanel();
        ImageIcon   oxIcon = new ImageIcon("Img\\ox.png");
        JLabel      ImgLabel = new JLabel();

        // Default Setting
        screen.setTitle("OXGame");
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.setLayout(null);     //for position
        //
        Button = new ButtonImageCreate[9];
        for (int x = 0; x < Button.length; x++) {
            Button[x] = new ButtonImageCreate(x, "Img/o.png",
                    "Img/Player1-" + Integer.toString(x + 1) + ".png", (x + 1) * (x + 200) + (x - 50), 100, 170, 230);
            Button[x].addActionListener(BH);
            Button[x].addMouseListener(MH);
            
        }
        // player1.setBounds(0,0,0,0);
        jp.add(player1Lab);
        // Show ox image
        oxIcon.setImage(oxIcon.getImage().getScaledInstance(460,440,Image.SCALE_DEFAULT));
        ImgLabel.setBounds(220,50,600,500);            //Image setting(x,y,width,heigh)
        ImgLabel.setIcon(oxIcon);
        jp.add(ImgLabel);

        // player1.setImage(player1.getImage().getScaledInstance(460,440,Image.SCALE_DEFAULT));
        // ImgLabel.setBounds(220,50,600,500);            //Image setting(x,y,width,heigh)
        // ImgLabel.setIcon(player1);
        // jp.add(ImgLabel);
       

        screen.add(jp);
        screen.validate();
    }

=======
>>>>>>> main
>>>>>>> da8e34b57e5806e7f6dea635ffc9ffa65dfbb3d0:PlayerSelectionPage.java
    private class ButtonHandler implements ActionListener {
        private Integer LastClick = -1;
        @Override
        public synchronized void actionPerformed(ActionEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            ButtonImageCreate tmpBtn = myBtn;
            // ButtonImageCreate sendBtn = tmpBtn;
            if (ExistsLastClick() >= 0 && ExistsLastClick() != 4) {

                System.out.println("LastClick " + ExistsLastClick());
                tmpBtn = Button[ExistsLastClick()];                 // Control LastClick Button
                tmpBtn.setIcon(tmpBtn.icon);
                tmpBtn.setClick(0);                                 // Formate the setting of Button Click
            }
            
            // Switch to HomePage
            if (myBtn.getID() == 5) {                               
                window.setVisible(false);
            }else if (myBtn.getID() == 4){          // OKButton Clicked
                try{
                    Character = o.PlayerSelection(GetPlayer() , ExistsLastClick());
                    System.out.println("You chose Role " + Character);
                }catch(Exception ke)
                {
                    System.out.println(ke);
                }
                SetPlayerInfor();

                window.setVisible(false);
                new SpaceFlagPage();
            }

            //Switch to OXGamePage
            if (myBtn.getID() == 4) {                               
                screen.remove(jp); 
                int x = tmpBtn.getID();
                player1 = new ImageIcon("Img/Player1-" + Integer.toString(x + 1) + ".png");
                player1.setImage(player1.getImage().getScaledInstance(180,250,Image.SCALE_DEFAULT));
                player1Lab.setBounds(30,70,300,400);            //Image setting(x,y,width,heigh)
                player1Lab.setIcon(player1);
                // player1.setIcon(tmpBtn.iconHover);
                OXGamePage();
                screen.validate();
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
        public synchronized void getLastClick(int LastClick) {
            this.LastClick = LastClick;
        }

        public synchronized int ExistsLastClick() {
            return this.LastClick;
        }
    }

    private class MouseHandler extends MouseAdapter {
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