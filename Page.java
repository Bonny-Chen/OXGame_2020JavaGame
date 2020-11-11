import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Page extends JFrame{
    public static JPanel PlayerPanel = new JPanel();            // PlayerSelectionPage's Panel
    public static JFrame screen = new JFrame();


    public static void main(String args[]) {
        screen.setTitle("HomePage");
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PlayerSelectionPage();
        screen.validate();

    }
    

    static ButtonImageCreate[] Button;
    static ButtonHandler BH = new ButtonHandler();
    static MouseHandler MH = new MouseHandler();


    public static void PlayerSelectionPage(){
        screen.setTitle("PlayerSelectionPage");
        PlayerPanel.setVisible(true);
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

    private static class ButtonHandler implements ActionListener {
        private Integer LastClick = -1;

        @Override
        public void actionPerformed(ActionEvent e) {
            ButtonImageCreate myBtn = (ButtonImageCreate) e.getSource();
            ButtonImageCreate tmpBtn = myBtn;
            if (ExistsLastClick() >= 0 && ExistsLastClick() != 4) {

                System.out.println("LastClick " + ExistsLastClick());
                tmpBtn = Button[ExistsLastClick()];                 // Control LastClick Button
                tmpBtn.setIcon(tmpBtn.icon);
                tmpBtn.setClick(0);                                 // Formate the setting of Button Click
            }
            
            // Switch to HomePage
            if (myBtn.getID() == 5) {                               
                PlayerPanel.setVisible(false);
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