package GUI;

import javax.swing.*;

public class CommandPanel extends JPanel{
    private JTextField SCTF;
    private JTextField OCTF;
    private JTextField HCTF;
    private JTextField colorTF;
    private JTextField RCTF;
    private JButton OKButton;
    private JButton CANCELButton;
    private JLabel colorLabel;
    private JLabel SCLabel;
    private JLabel OCLabel;
    private JLabel HCLabel;
    private JLabel RCLabel;
    private JLabel LogLabel;

    public CommandPanel(){

    }

    

    public JButton getCANCELButton() {
        return CANCELButton;
    }

    public JButton getOKButton() {
        return OKButton;
    }
}
