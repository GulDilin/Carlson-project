package GUI;

import javax.swing.*;
import java.awt.*;

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
    private JTextField indexTF;
    private JLabel indexLabel;

    public CommandFrame(){
        this.remove(commandPanel);
        for (JComponent component: new JComponent [] {SCTF, OCTF, HCTF,
                colorTF, RCTF, colorLabel,
                SCLabel, OCLabel, HCLabel,
                RCLabel, LogLabel, indexLabel, indexTF}){
            component.setFont(new Font("Consolas", Font.PLAIN, 16));
        }
        colorTF.addActionListener((e) -> SCTF.requestFocus());
        SCTF.addActionListener((e) -> OCTF.requestFocus());
        OCTF.addActionListener((e) -> HCTF.requestFocus());
        HCTF.addActionListener((e) -> RCTF.requestFocus());
//        RCTF.addActionListener(e -> OKButton.notify());
        GUITools.logLabelSettings(LogLabel);
        LogLabel.setText(" ");
        indexTF.setVisible(false);
        indexLabel.setVisible(false);

    }

    public void setIndexVisible(boolean flag){
        indexTF.setVisible(flag);
        indexLabel.setVisible(flag);
    }

    public JTextField getColorTF() {
        return colorTF;
    }

    public JTextField getHCTF() {
        return HCTF;
    }

    public JTextField getOCTF() {
        return OCTF;
    }

    public JTextField getRCTF() {
        return RCTF;
    }

    public JTextField getSCTF() {
        return SCTF;
    }

    public JTextField getIndexTF() {
        return indexTF;
    }

    public JLabel getLogLabel() {
        return LogLabel;
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
