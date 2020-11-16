import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SpaceFlagPage extends JFrame implements KeyListener{

    private JPanel SpacePanel;
    private ImageIcon flagIcon,flagIcon2,character,character2;
    private Image image;
    private JLabel flagLabel ,flagLabel2,characterLabel,characterLabel2;
    private int  x = 500;
    int player,role;
    EmptyGUI tmp = new EmptyGUI();
    // PlayerSelectionPage p = new PlayerSelectionPage();
    SpaceFlagPage(){
        // p.window.setVisible(false);             // Close the PlayerSelectionPage Display
        tmp.setTitle("SpaceFlagPage");
        tmp.addKeyListener(this);
        role = PlayerSelectionPage.GetRole();
        player = (PlayerSelectionPage.GetPlayer()+1);
        System.out.println("Role : " + role);
        System.out.println("You're Player "+ player);
        SpacePanel = new JPanel();
        SpacePanel.setVisible(true);
        SpacePanel.setLayout(null);

        flagIcon = new ImageIcon("Img/Flag.png");
        image = flagIcon.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        flagIcon = new ImageIcon(image);
        flagLabel = new JLabel (flagIcon);

        flagIcon2 = flagIcon;
        flagLabel2 = new JLabel (flagIcon2);

        character = new ImageIcon("Img/Player"+player+"-"+role+".png");
        image = character.getImage();
        image = image.getScaledInstance(50, 70, java.awt.Image.SCALE_SMOOTH);
        character = new ImageIcon(image);

        characterLabel = new JLabel(character);

        flagLabel.setBounds(200, 0, 50, 70);
        flagLabel2.setBounds(600, 0, 50, 70);
        if(player == 1)
            characterLabel.setBounds(200, x, 50, 70);
        else
            characterLabel.setBounds(600, x, 50, 70);

        SpacePanel.add(flagLabel);
        SpacePanel.add(flagLabel2);
        SpacePanel.add(characterLabel);

        tmp.add(SpacePanel);
        
    }

    @Override
	public void keyTyped(KeyEvent e) {
        //keyTyped = Invoked when a key is typed. Uses KeyChar, char output
        // if(e.getKeyCode()  == KeyEvent.VK_SPACE ){
        //     x -= 20;
        //     characterLabel.setBounds(200, x, 50, 70);
        // }
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//keyPressed = Invoked when a physical key is pressed down. Uses KeyCode, int output
		if(e.getKeyCode()  == KeyEvent.VK_SPACE ){
            if(player == 1)
                characterLabel.setBounds(200, x, 50, 70);
                
            else
                characterLabel.setBounds(600, x, 50, 70);

        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
        x -= 5;             // Change the moving speed Reference 20
        // if x == 0 -> win
	}
}