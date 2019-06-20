package GUI;

import client.Client;
import server.DataBaseManager;

import javax.swing.*;
import java.awt.*;
import static GUI.GUITools.*;

public class LogInWindow extends JFrame {

    public LogInWindow(Client client, ActionFrame actionFrame){
        Container       contentPane         = this.getContentPane();
        JPanel          mainPanel           = new JPanel();
        JPanel          choosePanel;
        JPanel          singInPanel;
        JPanel          registerPanel;
        String          languages[]         = {"Русский", "English"};
        JComboBox       langComboBox        = new JComboBox(languages);
        CardLayout      card                = new CardLayout();
        JTextField      loginSingField      = GUITools.createTField(15);
        JTextField      loginRegField       = GUITools.createTField(20);
        JTextField      emailField          = GUITools.createTField(20);
        JPasswordField  passwordField       = GUITools.createPField(15);
        SpringLayout    springLayout        = new SpringLayout();
        //Buttons on Sing in panel
        JButton         btnSOK              = createButton("OK");
        JButton         btnSCancel          = createButton("CANCEL");
        //Buttons on Reg panel
        JButton         btnROK              = createButton("OK");
        JButton         btnRCancel          = createButton("CANCEL");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnROK.addActionListener((e) ->
            {
                String password = DataBaseManager.getRandomPassword(4);
                if (client.hasLogin(loginRegField.getText())) {
                    JOptionPane.showMessageDialog(null, "Login already exist");
                }
                if(client.register(loginRegField.getText(), emailField.getText(), password)) {
                    JOptionPane.showMessageDialog(null, "Successful Registration" +
                            "\nYour password: " + password);
                    this.setVisible(false);
                    actionFrame.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Registration Error");
                }
            });

        btnSOK.addActionListener((e)->
            {
                if(client.singIn(loginSingField.getText(), passwordField.getText())){
                    JOptionPane.showMessageDialog(null, "Successful sing in");
                    this.setVisible(false);
                    actionFrame.setVisible(true);
                } else if (!client.hasLogin(loginSingField.getText())) {
                    JOptionPane.showMessageDialog(null, "No such user");
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Login or password");
                }
            });
        btnRCancel.addActionListener((e) -> card.show(mainPanel, "choose"));

        btnSCancel.addActionListener((e) -> card.show(mainPanel, "choose"));

        singInPanel = PanelsCreator.createSingInPanel(loginSingField, passwordField, btnSOK, btnSCancel);
        registerPanel = PanelsCreator.createRegPanel(loginRegField, emailField, btnROK, btnRCancel);
        //Buttons on Sing In and Registration panels
        JButton         singButton          = createButton("SING IN");
        singButton.addActionListener((e) -> card.show(mainPanel, "sing"));

        JButton         regButton           = createButton("REGISTER");
        regButton.addActionListener((e) -> card.show(mainPanel, "reg"));

        JButton         exitButton          = createButton("EXIT");
        exitButton.addActionListener((e) -> System.exit(0));

        GUITools.createRecommendedMargin(new JButton[] {singButton, regButton, exitButton});
        choosePanel = PanelsCreator.createChoosePanel(new JComponent[] {singButton, regButton, exitButton}, langComboBox);

        mainPanel.setLayout(card);
        mainPanel.add(choosePanel, "choose");
        mainPanel.add(singInPanel, "sing");
        mainPanel.add(registerPanel, "reg");

        card.show(mainPanel, "choose");
        contentPane.add(mainPanel);
        this.setPreferredSize(new Dimension(500, 400));
        pack();
    }
}
