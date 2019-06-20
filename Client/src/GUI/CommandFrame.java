package GUI;

import javax.swing.*;

public class CommandFrame extends JFrame{
    private JTextField      SCTF;
    private JTextField      OCTF;
    private JTextField      HCTF;
    private JTextField      colorTF;
    private JTextField      RCTF;
    private JButton         OKButton;
    private JButton         CANCELButton;
    private JLabel          colorLabel;
    private JLabel          SCLabel;
    private JLabel          OCLabel;
    private JLabel          HCLabel;
    private JLabel          RCLabel;
    private JLabel          LogLabel;
    private JPanel commandPanel;

    public CommandFrame(){
        this.remove(commandPanel);

    }

    public JPanel getCommandPanel() {
        return commandPanel;
    }

    public JButton getCANCELButton() {
        return CANCELButton;
    }

    public JButton getOKButton() {
        return OKButton;
    }
}
