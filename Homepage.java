import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

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
    }

    public static void main(String args[]) {
        Page();

    }
}