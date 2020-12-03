import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TicTacToe2
{
    private static oxButton[] oxbtn;
    static int round=0;
   
    
    public static void initialize()
    {
        JPanel      jp = new JPanel();
        JFrame window = new JFrame("Tic-Tac-Toe");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // window.getContentPane().add(new TicTacToe2());
        window.setSize(900, 630);
        window.setVisible(true);
        
        jp.setLayout(null);
        jp.setVisible(true);



        ImageIcon   logoIcon = new ImageIcon("Img\\ox.png");
        JLabel      ImgLabel = new JLabel();
        // Show image
        logoIcon.setImage(logoIcon.getImage().getScaledInstance(350,330,Image.SCALE_DEFAULT));
        ImgLabel.setBounds(280,0,600,600);            //Image setting(x,y,width,heigh)
        ImgLabel.setIcon(logoIcon);
        jp.add(ImgLabel);

        oxbtn = new oxButton[9];
        buttonListener bh = new buttonListener();
        for(int x=0; x<9; x++){
            oxbtn[x] = new oxButton(x);
            oxbtn[x].addActionListener(bh);
            oxbtn[x].setFocusable(false);           //inside line invisible
            oxbtn[x].setBorderPainted(false);       //outside line invisible

            if(x<3)oxbtn[x].setBounds(x*105+302, 153, 100, 95);
            else if(x<6)oxbtn[x].setBounds((x-3)*105+302, 250, 100, 95);
            else if(x<9)oxbtn[x].setBounds((x-6)*105+302, 348, 100, 95);
            jp.add(oxbtn[x]);
        }
        window.add(jp);
        window.validate();
     
  
        
    }
    public static void resetButtons()
    {
        for(int i = 0; i < 9; i++)
        {
            oxbtn[i].setContentAreaFilled(true);
            oxbtn[i].resetPressed(false);
            oxbtn[i].setIndex(0);
            oxbtn[i].setIcon(null);
            round=0;
        }
    }
    
    private static class buttonListener implements ActionListener
    {
        private ImageIcon iconO=new ImageIcon("Img\\o.png");
        private ImageIcon iconX = new ImageIcon("Img\\x.png");
        private int whowin=0;
        
        public void actionPerformed(ActionEvent e) 
        {
            oxButton myBtn = (oxButton)e.getSource();
            if(!myBtn.getPressed()){
                if(round%2==0){
                    iconO.setImage(iconO.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                    myBtn.setIcon(iconO);
                    myBtn.setIndex(1);
                    myBtn.setContentAreaFilled(false);   // Remove Btn Background
                   
                }
                else if(round%2==1){
                    iconX.setImage(iconX.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                    myBtn.setIcon(iconX);
                    myBtn.setIndex(2);
                    myBtn.setContentAreaFilled(false);   // Remove Btn Background
                }
                round++;
                myBtn.setPressed(true);
            }
            
            
            if(Win() == true&&whowin==1){ 
                JOptionPane.showConfirmDialog(null, "O win");       
                //System.out.println("O win");
                resetButtons();

            }
            else if(Win() == true&&whowin==2){   
                JOptionPane.showConfirmDialog(null, "X win");            
                //System.out.println("X win");
                resetButtons();

            }
            else if(Win()==false&&round==9){
                JOptionPane.showConfirmDialog(null, "break even"); 
                // System.out.println("e");
                resetButtons();
            }
          
        }
        
        public boolean Win()
        {
            
            //horizontal check
            if( check(0,1,2) ) 
                return true;
            else if(check(3,4,5) )
                return true;
            else if (check(6,7,8))
                return true;
            
            //vertical check
            else if (check(0,3,6))
                return true;  
            else if (check(1,4,7))
                return true;
            else if (check(2,5,8))
                return true;
            
            //diagonal check
            else if (check(0,4,8))
                return true;  
            else if (check(2,4,6))
                return true;
            else 
                return false;
            
        }
        
        public boolean check(int a, int b,int c)
        {
            if ( oxbtn[a].getIndex()==oxbtn[b].getIndex() &&oxbtn[b].getIndex()==oxbtn[c].getIndex() && oxbtn[a].getIndex()!=0){
                if(oxbtn[a].getIndex()==1){
                    whowin =1;
                }
                else if(oxbtn[a].getIndex()==2){
                    whowin =2;
                }
                return true;
            }
            else return false;
        }
        
    }
    
    public static void main(String[] args) 
    {
        initialize();
        
    }
}
class oxButton extends JButton
{
    private int id;
    private int index;
    private boolean isPressed;

    public oxButton(int id){
        this.id =id;
        this.index = 0;
        isPressed = false;        
    }
   public int getID() {
        return id;
    }
    public int getIndex(){
        return index;
    }
    
    public boolean getPressed(){
        return isPressed;
    } 
    public void setPressed(boolean isPressed){
        this.isPressed = true;
    }   
    public void resetPressed(boolean isPressed){
        this.isPressed = false;
    }
    public void setIndex(int num){
        this.index = num;
    }
}