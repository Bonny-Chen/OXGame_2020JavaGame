import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
/*www .  ja  va 2s  . co m*/
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Main {
  JButton clicker;

  public Main() {
    JFrame frame = new JFrame();

    JPanel contentPane = (JPanel) frame.getContentPane();
    addKeyBind(contentPane, "F10");
    clicker = new JButton("Clicker");
    contentPane.add(clicker);

    frame.setLayout(new GridBagLayout());
    frame.setSize(300, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  Action BtnAction = new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
      clicker.setEnabled(!clicker.isEnabled());
        System.out.println("Hi");
    }
  };

  private static final String DISABLE_CLICKER = "disableClicker";

  private void addKeyBind(JComponent contentPane, String key) {
    InputMap iMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap aMap = contentPane.getActionMap();
    iMap.put(KeyStroke.getKeyStroke(key), DISABLE_CLICKER);
    aMap.put(DISABLE_CLICKER, BtnAction);
  }

  public static void main(String[] args) {
    new Main();
  }
}