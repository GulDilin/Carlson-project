package GUITolya;

import GUI.CommandFrame;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class JFrameTest extends JFrame
{
    public JFrameTest(){
        initialize();
    }

    private void initialize(){
        this.add(new CommandFrame());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null, "Ths");
                System.exit(0);
            }
        });
        this.setSize(1000, 700);
        this.setPreferredSize(new Dimension(500, 500));
        this.setResizable(false);
        this.setTitle("Test");
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e){

        }
        JFrame.setDefaultLookAndFeelDecorated(true);
//        javax.swing.SwingUtilities.invokeLater(() ->{new MainWindow();});
        JFrameTest window = new JFrameTest();
    }
}