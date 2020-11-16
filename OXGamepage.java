import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class OXGamepage {
/**
* @param args
*/
public static void main(String[] args) {
final Shape cycle =new Ellipse2D.Double(10,10,100,100);
final JFrame f=new JFrame();
f.setDefaultCloseOperation(3);
f.setSize(300,300);
JLabel l=new JLabel(){
private static final long serialVersionUID = 5045688487186402437L;
public void paint(Graphics g){
g.setColor(Color.red);
((java.awt.Graphics2D)g).draw(cycle);
}
};
l.addMouseListener(new MouseAdapter(){
public void mousePressed(MouseEvent e){
    if(cycle.contains(e.getPoint()))
    f.setTitle(String.format("In cycle. x:%d y:%d",e.getX(),e.getY()));
    else
    f.setTitle(null);
    }
});
f.add(l);
f.setVisible(true);
}
}