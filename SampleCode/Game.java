//************************************************************************
//*  Network Programming - Final Project (OXGame)                        *
//*  Program Name: Game.java                                             *
//*  The program create the grid panel of game .                         *
//*  2020.11.29                                                          *
//************************************************************************

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Game{
    public static JFrame screen = new JFrame();
    public static JPanel GamePanel = new JPanel(); // Game's Panel
    public static JLabel GridLabel = new JLabel(); // Game's Panel
    public static Integer Character = 9;
    public static ButtonImageCreate[] Btn;
    public static ButtonHandler BH = new ButtonHandler();
    public static MouseHandler MH = new MouseHandler();
    public static int Player = 1;           // Get From Server
    Game(){
        ImageIcon icon;
        int tmpx;
        Btn = new ButtonImageCreate[9];
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GamePanel.setVisible(true);
        GamePanel.setLayout(null);

        icon = new ImageIcon("../Img/ox.png");
        icon.setImage(icon.getImage().getScaledInstance(398, 398, Image.SCALE_DEFAULT));
        GridLabel.setIcon(icon);
        GridLabel.setBounds(200, 50, 500, 500);
        GamePanel.add(GridLabel);

        for (int x = 0; x < 3; x++) {
            Btn[x] = new ButtonImageCreate(x, "","",224 + (x*120),123, 115, 115);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }
        for (int x = 3; x < 6; x++) {
            tmpx = x%3;
            Btn[x] = new ButtonImageCreate(x, "","",224 + (tmpx*120),240, 115, 115);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }
        for (int x = 6; x < 9; x++) {
            tmpx = x%3;
            Btn[x] = new ButtonImageCreate(x, "","",224 + (tmpx*120),357, 115, 115);
            Btn[x].addActionListener(BH);
            Btn[x].addMouseListener(MH);
            GamePanel.add(Btn[x]);
        }

        screen.add(GamePanel);
        screen.validate();
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

    private static class ButtonHandler implements ActionListener {
        private Integer LastClick = -1;
        Game game;

        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            ButtonImageCreate tmpBtn = myBtn;
            // ?
            if (ExistsLastClick() >= 0 && ExistsLastClick() != 4) {
                Character = ExistsLastClick();
                System.out.println("LastClick " + ExistsLastClick());
                tmpBtn = Btn[ExistsLastClick()]; // Control LastClick Button
                tmpBtn.setIcon(tmpBtn.icon);
                tmpBtn.setClick(0); // Formate the setting of Button Click

            }

            if(Player == 1){
                if(myBtn.getID() <= 8 && myBtn.getID() >= 0){
                    if(myBtn.IsClicked() != 1){
                        myBtn.setText("O");
                        myBtn.setFont(new Font("Arial", Font.PLAIN, 72));
                        myBtn.setForeground(Color.RED);
                    }
                }
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
        this.setContentAreaFilled(true); // Remove Btn Background
        this.setFocusable(true);
        this.setBorderPainted(true); // Remove Btn Border
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