package GUI;

import client.Client;
import com.jcraft.jsch.JSchException;
import server.DataBaseManager;

import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import static GUI.GUITools.*;

public class LogInWindow extends JFrame {

    private String      login               = "";
    private String      password            = "";
    private Client      client;
    private int         port;
    private Boolean     isLogin;
    private CardLayout  card;
    private JPanel      mainPanel;
    private JPanel          choosePanel;
    private JPanel          singInPanel;
    private JPanel          registerPanel;
    private JLabel          portLogLabel;
    private JLabel          portLabel;
    private JLabel          SloginLabel;
    private JLabel          RloginLabel;
    private JLabel          passwordLabel;
    private Locale currLocale;
    private ResourceBundle labelsBundle;
    private ResourceBundle messageBundle;
    private ResourceBundle errorsBundle;
    private     JPanel          portPanel;
    private     JCheckBox       isTunnelCheckBox;
    private     JButton         btnPOK;
    private     JButton         btnReg;
    private     JButton         btnCExit;
    private     JButton         btnPExit;
    private     JButton         btnSOK;
    private     JButton         btnSCancel;
    private     JButton         btnROK;
    private     JButton         btnRCancel;
    private     JButton         btnSing;
    private     Locale          ruLocale;


    public void updateText(){
        labelsBundle    = ResourceBundle.getBundle("Labels", currLocale);
        messageBundle    = ResourceBundle.getBundle("Messages", currLocale);
        errorsBundle    = ResourceBundle.getBundle("Errors", currLocale);
        btnSing.setText(labelsBundle.getString("singin"));
        btnReg.setText(labelsBundle.getString("register"));
        btnCExit.setText(labelsBundle.getString("exit"));
        btnPExit.setText(labelsBundle.getString("exit"));
        btnRCancel.setText(labelsBundle.getString("cancel"));
        btnSCancel.setText(labelsBundle.getString("cancel"));
        SloginLabel.setText(labelsBundle.getString("login"));
        RloginLabel.setText(labelsBundle.getString("login"));
        passwordLabel.setText(labelsBundle.getString("pass"));
        portLabel.setText(messageBundle.getString("insert_port"));
        isTunnelCheckBox.setText(labelsBundle.getString("tunnel"));
        revalidate();
        repaint();
    }

    public void updatePT(){
        labelsBundle    = ResourceBundle.getBundle("Labels", currLocale);
        messageBundle    = ResourceBundle.getBundle("Messages", currLocale);
        errorsBundle    = ResourceBundle.getBundle("Errors", currLocale);
        portLabel.setText(messageBundle.getString("insert_port"));
        btnPExit.setText(labelsBundle.getString("exit"));

    }


    public LogInWindow(ActionFrame actionFrame, Boolean isLogin){
        this.isLogin = isLogin;
        actionFrame.setLogInWindow(this);
        ruLocale                            = new Locale("ru", "RU");
        this.currLocale                     = ruLocale;
        Container       contentPane         = this.getContentPane();
        mainPanel                           = new JPanel();
        SloginLabel                         = new JLabel("LOGIN");
        RloginLabel                         = new JLabel("LOGIN");
        passwordLabel                       = new JLabel("PASSWORD");

        isTunnelCheckBox                    = GUITools.createCheckBox("TUNNEL");
        String          languages[]         = {"Русский", "English"};
        JComboBox       SlangComboBox       = new JComboBox(languages);
        JComboBox       PlangComboBox       = new JComboBox(languages);

        card                = new CardLayout();
        JTextField      loginSingField      = GUITools.createTField(15);
        JTextField      loginRegField       = GUITools.createTField(20);
        JTextField      emailField          = GUITools.createTField(20);
        JTextField      portField           = GUITools.createTField(6);
        JPasswordField  passwordField       = GUITools.createPField(15);
        SpringLayout    springLayout        = new SpringLayout();
        //Buttons on port Panel
        btnPOK              = createButton("OK");
        btnPExit            = createButton("EXIT");
        //Buttons on choose panel
        btnSing          = createButton("SING IN");
        btnReg           = createButton("REGISTER");
        btnCExit          = createButton("EXIT");
        //Buttons on Sing in panel

        btnSOK              = createButton("OK");
        btnSCancel          = createButton("CANCEL");
        //Buttons on Reg panel
        btnROK              = createButton("OK");
        btnRCancel          = createButton("CANCEL");
        portLabel           = createLabel("insert_port");

        SloginLabel.setFont(new Font("Impact", Font.PLAIN, 18));
        passwordLabel.setFont(new Font("Impact", Font.PLAIN, 18));

        updateText();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //-----------------------------------------------
        //Add actions to lang switch buttons
        for (JComboBox box: new JComboBox[]{SlangComboBox, PlangComboBox }) {
            ActionListener langSwitch = e -> {
                switch (box.getSelectedIndex()) {
                    case 0:
                        currLocale = ruLocale;
                        break;
                    case 1:
                        currLocale = Locale.ENGLISH;
                        break;
                }
                PlangComboBox.setSelectedIndex(box.getSelectedIndex());
                SlangComboBox.setSelectedIndex(box.getSelectedIndex());
                updateText();
            };
            box.addActionListener(langSwitch);
        }

        //-----------------------------------------------
        //Add actions to OK button on Reg Panel
        btnROK.addActionListener((e) ->
            {
                this.password   = DataBaseManager.getRandomPassword(4);
                this.login      = loginRegField.getText();
                if (client.hasLogin(login)) {
                    JOptionPane.showMessageDialog(null, errorsBundle.getString("log_err"));
                }
                if(client.register(this.login, emailField.getText(), password)) {
                    JOptionPane.showMessageDialog(null, messageBundle.getString("reg_suc")
                            + password);
                    this.isLogin = Boolean.valueOf(true);
                    actionFrame.showCollection();
                    this.setVisible(false);
                    actionFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, errorsBundle.getString("reg_err"));
                }
            });

        //-----------------------------------------------
        //Add actions to OK button on Sing Panel
        ActionListener SOKListener = (e)->
        {
            this.login       = loginSingField.getText();
            this.password    = passwordField.getText();
            if(client.singIn(login, password)){
                actionFrame.getLoginLabel().setText(labelsBundle.getString("login") + ": " + login);
                this.isLogin = Boolean.valueOf(true);
                actionFrame.showCollection();
                this.setVisible(false);
                actionFrame.setVisible(true);
            } else if (!client.hasLogin(loginSingField.getText())) {
                JOptionPane.showMessageDialog(null, errorsBundle.getString("no_such_user"));
            } else {
                JOptionPane.showMessageDialog(null, errorsBundle.getString("wr_pass"));
            }
        };
        btnSOK.addActionListener(SOKListener);
        passwordField.addActionListener(SOKListener);

        loginRegField.addActionListener((e) -> emailField.requestFocus());
        loginSingField.addActionListener((e) -> passwordField.requestFocus());
        //Buttons returns to choose panel
        btnRCancel.addActionListener((e)    -> card.show(mainPanel, "choose"));
        btnSCancel.addActionListener((e)    -> card.show(mainPanel, "choose"));
        //Buttons on Sing In and Registration panels
        btnSing.addActionListener((e)       -> {card.show(mainPanel, "sing");
            loginSingField.requestFocus();});
        btnReg.addActionListener((e)        -> {card.show(mainPanel, "reg");
            loginRegField.requestFocus();});
        //After singing in
        actionFrame.getChngUsrBtn().addActionListener((e) -> {
            this.setVisible(true);
            actionFrame.setVisible(false);
        });
        //Exit buttons
        btnCExit.addActionListener((e) -> System.exit(0));
        btnPExit.addActionListener((e) -> System.exit(0));
        //Create panels
        singInPanel     = PanelsCreator.createSingInPanel(
                loginSingField, passwordField,
                btnSOK, btnSCancel,
                SloginLabel, passwordLabel);
        registerPanel   = PanelsCreator.createRegPanel(loginRegField, emailField, btnROK, btnRCancel, RloginLabel);
        GUITools.createRecommendedMargin(new JButton[] {btnSing, btnReg, btnCExit});
        choosePanel = PanelsCreator.createChoosePanel(new JComponent[] {btnSing, btnReg, btnCExit}, SlangComboBox);

        portLogLabel = createLabel(" ");
        portLogLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        portLogLabel.setForeground(new Color(0x8F4247));
        portPanel   = PanelsCreator.createChoosePanel(new JComponent[]{
                portLabel, portField,
                isTunnelCheckBox, portLogLabel,
                btnPOK, btnPExit}, PlangComboBox);

        ActionListener POKAction = (e) -> {
            portLogLabel.setText(messageBundle.getString("try"));
            revalidate();
            repaint();
            SwingUtilities.invokeLater(
                    () -> {
                        try {
                            port = Integer.valueOf(portField.getText());
                            boolean isTunnel = isTunnelCheckBox.isSelected();
                            client = new Client("localhost", port, isTunnel);
                            Client.ClientThread thread = client.createClientThread();
                            actionFrame.setClientThread(thread);
                            card.show(mainPanel, "choose");
                        } catch (NumberFormatException nullex){
                            portLogLabel.setText(errorsBundle.getString("no_num"));
                        } catch (SQLException ex) {
                            portLogLabel.setText(errorsBundle.getString("db_err"));
                            if (client != null)
                                client.stop();
                            ex.printStackTrace();
                        } catch (IOException ioex) {
                            portLogLabel.setText(errorsBundle.getString("serv_con_err"));
                            if (client != null)
                                client.stop();
                            ioex.printStackTrace();
                        } catch (JSchException jex) {
                            portLogLabel.setText(errorsBundle.getString("ssh_err"));
                            if (client != null)
                                client.stop();
                            jex.printStackTrace();
                        } catch (Exception ne){
                            ne.printStackTrace();
                        }
                    }
            );

        };
        btnPOK.addActionListener(POKAction);
        portField.addActionListener(POKAction);

        mainPanel.setLayout(card);
        mainPanel.add(portPanel, "port");
        mainPanel.add(choosePanel, "choose");
        mainPanel.add(singInPanel, "sing");
        mainPanel.add(registerPanel, "reg");

        card.show(mainPanel, "port");
        contentPane.add(mainPanel);
        this.setPreferredSize(new Dimension(500, 400));
        pack();
    }

    public void connectServer(){
        card.show(mainPanel, "port");
    }

    public JLabel getPortLogLabel() {
        return portLogLabel;
    }
}
