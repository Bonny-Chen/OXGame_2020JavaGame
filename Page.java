import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.rmi.*;

import java.net.*;
import java.io.*;

public class Page extends JFrame implements KeyListener {
    public static Interface o = null;

    public static JPanel PlayerPanel = new JPanel(); // PlayerSelectionPage's Panel
    public static JPanel SpacePanel = new JPanel(); // SpaceFlagPage's Panel
    public static JFrame screen = new JFrame();
    public static JLabel characterLabel;
    public static Integer player;
    public static Integer Character = 9;

    public static int inform[] = new int[2];
    public static int x = 500;

    Page() {

        try {
            o = (Interface) Naming.lookup("rmi://192.168.0.3:1099/OXGame");
            System.out.println("RMI server connected");
            player = o.GetPlayerNum();
            System.out.println("Player "+player +" login");

        } catch (Exception e) {
            System.out.println("Server lookup exception: " + e.getMessage());
        }
        screen.setTitle("HomePage");
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.addKeyListener(this);
        PlayerSelectionPage();
        screen.validate();

    }

    public static void main(String args[]) {

        Socket client = null;
        int port = 6666;

        try {
            // Creates a stream socket and connects it to the specified port number
            // at the specified IP address.
            client = new Socket(args[0], port);
            System.out.println("Client In");
            new Page();

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ButtonImageCreate[] Button;
    static ButtonHandler BH = new ButtonHandler();
    static MouseHandler MH = new MouseHandler();

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
        int Player, role, y = 500, s = 500;
        int anPlayer, anRole = 9;
        SpacePanel.setVisible(true);
        screen.setTitle("SpaceFlagPage");
        SpacePanel.setLayout(null);
        // screen.addKeyListener(Spc);

        role = GetRole();
        Player = GetPlayer() + 1;

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

        // if(Player == 1)
        // anPlayer = 2;
        // else
        // anPlayer = 1;

        // try{
        // anRole = o.GetInform(anPlayer);
        // }catch (Exception o){
        // System.out.println(o);
        // }

        // character2 = new ImageIcon("Img/Player"+anPlayer+"-"+anRole+".png");
        // image = character2.getImage();
        // image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        // character2 = new ImageIcon(image);

        srver = new ImageIcon("Img/Player" + 2 + "-" + 1 + ".png");
        image = srver.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        srver = new ImageIcon(image);

        flagLabel = new JLabel(flagIcon);
        flagLabel2 = new JLabel(flagIcon2);
        characterLabel = new JLabel(character);
        // characterLabel2 = new JLabel(character2);
        srverLabel = new JLabel(srver);

        flagLabel.setBounds(200, 0, 50, 70);
        flagLabel2.setBounds(600, 0, 50, 70);

        // try{
        // y = o.Getmove(anPlayer);
        // }catch(Exception error){
        // System.out.println(error);
        // }

        try {
            s = o.srverMove(s);
        } catch (Exception error) {
            System.out.println("s :" + error);
        }

        if (Player == 1) {
            characterLabel.setBounds(600, x, 50, 70);
            srverLabel.setBounds(200, s, 50, 70);
            // characterLabel2.setBounds(200, y, 50, 70);

        } else {
            characterLabel.setBounds(200, x, 50, 70);
            srverLabel.setBounds(600, s, 50, 70);
            // characterLabel2.setBounds(600, y, 50, 70);
        }

        SpacePanel.add(flagLabel);
        SpacePanel.add(flagLabel2);
        SpacePanel.add(characterLabel);
        // SpacePanel.add(characterLabel2);
        SpacePanel.add(srverLabel);

        screen.add(SpacePanel);

    }

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
        try {
            o.move((player + 1), x);
        } catch (Exception le) {
            System.out.println(le);
        }
        // if x == 0 -> win
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
            } else if (myBtn.getID() == 4) {
                PlayerPanel.setVisible(false);
                try {
                    Character = o.PlayerSelection(GetPlayer(), ExistsLastClick());
                    System.out.println("You chose Role " + Character);
                } catch (Exception ke) {
                    System.out.println(ke);
                }

                try {
                    o.SetInform(player, Character);
                    System.out.println("In");

                } catch (Exception er) {
                    System.out.println(er);
                }
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