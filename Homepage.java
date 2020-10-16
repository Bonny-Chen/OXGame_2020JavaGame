import java.awt.*;
import javax.swing.*;

public class Homepage {

    public static void Page() {
        JFrame      screen = new JFrame("Home Page");
        JPanel      jp = new JPanel();
        JButton     StartBtn = new JButton("START");
        JButton     HowToBtn = new JButton("HOW TO");
        JButton     ExitBtn = new JButton("EXIT");
        JLabel      titLabel = new JLabel("OX Game");
        ImageIcon   icon = new ImageIcon("Img\\Tic-Tag-Toe.png");
        Image       imageIcon;
        Image       tmpimg;
        ImageIcon       HowtoImg;
        JLabel      ImgLabel = new JLabel();
        JLabel      jl = new JLabel();
        JLabel      TEST = new JLabel();            // TEST FOR PUSH


        // Default Setting
        screen.setTitle("OXGame");
        screen.setVisible(true);
        screen.setSize(900, 630);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.setLayout(null);     //for position

        // Show image
        imageIcon = icon.getImage();
        tmpimg = imageIcon.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(tmpimg);
        ImgLabel.setBounds(290,190,300,300);            //Image setting(x,y,width,heigh)
        ImgLabel.setIcon(icon);
        jp.add(ImgLabel);

        // titLabel Setting (Title)
        titLabel.setBounds(350, 100, 300, 50);                   // Title setting (x,y,width,heigh)
        titLabel.setFont(new java.awt.Font("Dialog", 4, 50));   // Resize Font
        titLabel.setForeground(Color.decode("#FFFFFF")); 
        jp.add(titLabel);

        // Background setting
        jp.setBackground(Color.decode("#123456"));

        // button setting
        StartBtn.setBounds(630, 300, 160, 30);                  //set position
        HowToBtn.setBounds(630,350,160,30);
        ExitBtn.setBounds(630, 400, 160, 30);
        StartBtn.setBackground(Color.decode("#1CBACDE"));        //set background color
        HowToBtn.setBackground(Color.decode("#1CBACDE"));
        ExitBtn.setBackground(Color.decode("#1CBACDE"));
        StartBtn.setForeground(Color.decode("#123AAA"));        //set text color
        HowToBtn.setForeground(Color.decode("#123AAA"));
        ExitBtn.setForeground(Color.decode("#123AAA"));

        jp.add(StartBtn);
        jp.add(HowToBtn);
        jp.add(ExitBtn);

        screen.add(jp);
        screen.validate();
    }

    public static void main(String args[]) {
        Page();

    }
}