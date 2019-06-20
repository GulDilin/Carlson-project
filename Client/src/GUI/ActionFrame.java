package GUI;

import javax.swing.*;
import java.awt.*;

public class ActionFrame extends JFrame {
    private JTextField  LogTF;
    private JComboBox   langComboBox;
    private JButton     chngUsrBtn;
    private JTextField  loginTextField;
    private JPanel      mainPanel;
    private JPanel      buttonUsrPanel;
    private JLabel      winName;
    private JPanel      elemPanel;
    private JScrollPane elemScroll;
    private JLabel      elemLabel;
    private JButton     startButton;
    private JPanel      usrPanel;
    private JPanel      userLogoPanel;
    private JLabel      buttonLabel;
    private JButton     addButton;
    private JButton     removeButton;
    private JButton     removeLastButton;
    private JButton     sortButton;
    private JButton     insertButton;
    private JPanel      buttonPanel;
    private JPanel      commandPanel;
    private JPanel      cardPanel;
    private LayoutManager   layout;
    private CardLayout      card ;
    private CommandFrame    commandFrame;

    public ActionFrame( String [] langs){
        commandFrame    = new CommandFrame();
        commandPanel    = commandFrame.getCommandPanel();
        card            = new CardLayout();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (String lang: langs){
            langComboBox.addItem(lang);
        }

        setSize(800, 500);
        setContentPane(mainPanel);

        cardPanel.remove(buttonPanel);
        cardPanel.setLayout(card);
        cardPanel.add(buttonPanel, "buttons");
        cardPanel.add(commandPanel, "command");

        GUITools.setBackground(new JComponent[]{addButton, removeButton, removeButton,
                sortButton, insertButton, startButton,
                removeLastButton, LogTF, chngUsrBtn}, new Color(153,153,153));

        for (JButton button: new JButton[] {addButton, insertButton, removeButton})
        {
            button.addActionListener((e) ->
                    card.show(cardPanel, "command"));
        }

        commandFrame.getCANCELButton().addActionListener((e) ->
            card.show(cardPanel, "buttons"));

        GUITools.setBackground(new JComponent[]{buttonUsrPanel, elemPanel, elemScroll,
                usrPanel, mainPanel, userLogoPanel, buttonPanel}, new Color(0x0084DB));
        loginTextField.setEditable(false);
        card.show(cardPanel, "buttons");
    }
}
