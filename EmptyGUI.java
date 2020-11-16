package Pages;

import java.awt.*;
import javax.swing.*;
public class EmptyGUI {
    
    public static void Page(){
        JFrame      screen = new JFrame();

        screen.setTitle("OXGame");
        screen.setVisible(true);
        screen.setSize(1024,780);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        screen.validate();

    }

    // public static void main(String args[]){
    //     Page();
    // }
}
