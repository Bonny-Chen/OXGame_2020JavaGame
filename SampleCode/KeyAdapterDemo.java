import java.awt.*;
import java.awt.event.*;
  
public class KeyAdapterDemo extends KeyAdapter {
    Frame frame;
    TextArea text;
      
    public static void main(String[] args) {
        new KeyAdapterDemo();
    }
      
    public KeyAdapterDemo() {
        frame = new Frame("AWTDemo");
        frame.addWindowListener(new AdapterDemo());
        frame.setSize(600, 400);
        // text = new TextArea();
        frame.addKeyListener(this);
        // frame.add(text);
        frame.setVisible(true);
    }
      
    public void keyTyped(KeyEvent e) {
        System.out.println("keyTyped");
    }
     
    public void keyPressed(KeyEvent e) {
        System.out.println("keyPressed");
    }
     
    public void keyReleased(KeyEvent e) {
        System.out.println("keyReleased");
    }
}
  
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}