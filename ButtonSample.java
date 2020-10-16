import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonSample {

    public static void BTN(){
        JFrame      screen = new JFrame();
        JLabel      jl = new JLabel();    
        JPanel      jp = new JPanel();
        ImageIcon   imageIcon = new ImageIcon();    // Used for resize background 
        Image       image;
        Image       tmpimg;
        image       TEST;

        //
        screen.setTitle("ButtonSample");
        screen.setVisible(true);
        screen.setSize(1024,780);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //
        imageIcon = new ImageIcon("Img/info.png");
        image = imageIcon.getImage();
        tmpimg = image.getScaledInstance(200, 120,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(tmpimg);


        // 
        JButton OKBtn = new JButton("OK");
        jp.add(OKBtn);
        OKBtn.setPreferredSize(new Dimension(100,50));   // Resize Btn (w,h)
        
        //
        imageIcon = new ImageIcon("Img\\info.png");
        JButton ImgBtn = new JButton(imageIcon);
        ImgBtn.setContentAreaFilled(false);             //
        ImgBtn.setFocusable(false);                     
        ImgBtn.setBorderPainted(false);                 //
        jp.add(ImgBtn);

        //
        ImgBtn.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ImgBtn.setContentAreaFilled(true); 
                ImgBtn.setBackground(Color.GREEN);
            }
        
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ImgBtn.setBackground(UIManager.getColor("control"));
                ImgBtn.setContentAreaFilled(false); 
            }
        });
        
        //
        ImgBtn.addActionListener(new ActionListener() {
            int         count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                ImgBtn.setText("Click " + count ++);
            }
        });
        screen.add(jp);

       
        screen.validate();
    }
    public static void main(String[] args){
        BTN();
    }
}
