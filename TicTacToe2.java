import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TicTacToe2 extends JPanel
{
    private oxButton[] oxbtn;
    int round=0;
    
    public TicTacToe2()
    {
      //setLayout(new GridLayout(3,3));
      setLayout(null);
      initializebuttons();
    }
    
    public void initializebuttons()
    {
        ImageIcon   logoIcon = new ImageIcon("Img\\ox.png");
        JLabel      ImgLabel = new JLabel();
        // Show image
        logoIcon.setImage(logoIcon.getImage().getScaledInstance(350,330,Image.SCALE_DEFAULT));
        ImgLabel.setBounds(280,0,600,600);            //Image setting(x,y,width,heigh)
        ImgLabel.setIcon(logoIcon);
        add(ImgLabel);

        oxbtn = new oxButton[9];
        buttonListener bh = new buttonListener();
        for(int x=0; x<9; x++){
            int i=0;
            oxbtn[x] = new oxButton(x);
            oxbtn[x].addActionListener(bh);
            oxbtn[x].setFocusable(false);           //inside line invisible
            oxbtn[x].setBorderPainted(false);       //outside line invisible
            if(x<3)oxbtn[x].setBounds(x*105+302, 153, 100, 95);
            else if(x<6)oxbtn[x].setBounds((x-3)*105+302, 250, 100, 95);
            else if(x<9)oxbtn[x].setBounds((x-6)*105+302, 348, 100, 95);
            add(oxbtn[x]);
            i++;
            if(i==3)i=0;
        }
     
  
        
    }
    public void resetButtons()
    {
        for(int i = 0; i < 9; i++)
        {
            oxbtn[i].setContentAreaFilled(true);
            oxbtn[i].resetPressed(false);
            oxbtn[i].setText("");
            oxbtn[i].setIcon(null);
            round=0;
        }
    }
    
    private class buttonListener implements ActionListener
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
                    myBtn.setText("O");
                    myBtn.setContentAreaFilled(false);   // Remove Btn Background
                   
                }
                else if(round%2==1){
                    iconX.setImage(iconX.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
                    myBtn.setIcon(iconX);
                    myBtn.setText("X");
                    myBtn.setContentAreaFilled(false);   // Remove Btn Background
                }
                round++;
                myBtn.setPressed(true);
            }
            
            
            if(Win() == true&&whowin==1){ 
                JOptionPane.showConfirmDialog(null, "O win");       
                // System.out.println("O win");
                resetButtons();

            }
            else if(Win() == true&&whowin==2){   
                JOptionPane.showConfirmDialog(null, "X win");            
                // System.out.println("X win");
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
            if ( oxbtn[a].getText().equals(oxbtn[b].getText()) &&oxbtn[b].getText().equals(oxbtn[c].getText()) && !oxbtn[a].getText().equals("")){
                if(oxbtn[a].getText().equals("O")){
                    whowin =1;
                }
                else if(oxbtn[a].getText().equals("X")){
                    whowin =2;
                }

                return true;
            }
                
            else
                return false;
        }
        
    }
    
    public static void main(String[] args) 
    {
        JFrame window = new JFrame("Tic-Tac-Toe");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new TicTacToe2());
        //window.setBounds(300,200,700,700);
        window.setSize(900, 630);
        window.setVisible(true);
    }
}
class oxButton extends JButton
{
    private int id;
    private boolean isPressed;

    String buttonName;

    public oxButton(int id){
        this.id =id;
        isPressed = false;        
    }

 
    public int getID() {
        return id;
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
}