import java.io.*;
import java.util.*;
import java.security.*;
// import javax.xml.bind.*;
import java.lang.*;
import java.awt.*;
import javax.swing.*;

public class OXGamepage
{

    public static class GridPanel extends JPanel 
    {
        public GridPanel()
        {
            setLayout(new GridLayout(2,3));
            setBackground(Color.GREEN);
            this.setPreferredSize(new Dimension(500,200));

            JButton b1 = new JButton ("Button 1");
            JButton b2 = new JButton ("Button 2");
            JButton b3 = new JButton ("Button 3");
            JButton b4 = new JButton ("Button 4");
            JButton b5 = new JButton ("Button 5");
            JButton b6 = new JButton ("Button 6");

            add(b1);
            add(b2);
            add(b3);
            add(b4);
            add(b5);
            add(b6);
        }

    }



    public static void main(String[] args) 

    {
       GridPanel gp = new GridPanel();
       JFrame jf = new JFrame();
       jf.add(gp);
       jf.pack(); //code wouldnt work if i comment out this line
       jf.setVisible(true);

    }

}