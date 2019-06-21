package GUI;

import CarlsonProject.UserHandler;
import CarlsonProject.WindowsArrayList;
import CarlsonProject.commands.*;
import CarlsonProject.plot.NoSuchColorException;
import client.Client;
import com.jcraft.jsch.IO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.SocketException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ActionFrame extends JFrame {
    private Locale      ruLocale;
    private JTextField  LogTF;
    private JComboBox   langComboBox;
    private JButton     chngUsrBtn;
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
    private JLabel      loginLabel;
    private JPanel      scrollPanel;
    private LayoutManager   layout;
    private CardLayout      card ;
    private CommandFrame    commandFrame;
    private Client.ClientThread     clientThread;
    private Boolean     isLogin;
    private String      cmndName = "";
    int     collectionSize;
    boolean isSorted;
    private LogInWindow logInWindow;
    private Locale      currLocale;
    private ResourceBundle labelsBundle;
    private ResourceBundle errorsBundle;
    private ResourceBundle messageBundle;

    public void updateText(){
        labelsBundle    = ResourceBundle.getBundle("Labels", currLocale);
        messageBundle   = ResourceBundle.getBundle("Messages", currLocale);
        errorsBundle    = ResourceBundle.getBundle("Errors", currLocale);
        addButton.setText(labelsBundle.getString("add"));
        removeButton.setText(labelsBundle.getString("remove"));
        removeLastButton.setText(labelsBundle.getString("removelast"));
        insertButton.setText(labelsBundle.getString("insert"));
        sortButton.setText(labelsBundle.getString("sort"));
        chngUsrBtn.setText(labelsBundle.getString("chngusr"));
        startButton.setText(labelsBundle.getString("start"));
        elemLabel.setText(labelsBundle.getString("elems"));
        buttonLabel.setText(labelsBundle.getString("funcs"));
    }

    public ActionFrame(String [] langs, boolean isLogin){
        //Set buttons names
        addButton.setName("add");
        removeLastButton.setName("remove_last");
        insertButton.setName("insert");
        startButton.setName("start");
        chngUsrBtn.setName("chngus");
        removeButton.setName("remove");
        this.collectionSize = 0;
        ruLocale        = new Locale("ru", "RU");
        this.currLocale = ruLocale;
        labelsBundle    = ResourceBundle.getBundle("Labels", currLocale);
        updateText();
        this.isSorted   = false;
        this.isLogin    = isLogin;
        commandFrame    = new CommandFrame();
        commandPanel    = commandFrame.getCommandPanel();
        card            = new CardLayout();

        scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                doCommand(new ExitCommand());
                System.exit(0);
            }
        });

        for (String lang: langs){
            langComboBox.addItem(lang);
        }

        setSize(800, 500);
        setContentPane(mainPanel);

        cardPanel.remove(buttonPanel);
        cardPanel.setLayout(card);
        cardPanel.add(buttonPanel, "buttons");
        cardPanel.add(commandPanel, "command");

        GUITools.setBackground(new JComponent[]{
                addButton, removeButton, removeButton,
                sortButton, insertButton, startButton,
                removeLastButton, LogTF, chngUsrBtn,
                scrollPanel, langComboBox}, new Color(153,153,153));
//-------------------------------------------------
        //Change locate
        langComboBox.addActionListener(e -> {
            switch ((String)langComboBox.getSelectedItem()){
                case "Русский":
                    currLocale = ruLocale;
                    break;
                case "English":
                    currLocale = Locale.ENGLISH;
                    break;
            }
            updateText();
        });
//--------------------------------------------------
//Add funcs for buttons
        sortButton.addActionListener(e -> isSorted = true);
        for (JButton button: new JButton[] {removeLastButton, startButton}) {
            button.addActionListener(e -> {
                Command command = null;
                System.out.println("button name: " + button.getName());
                switch (button.getName()){
                    case "remove_last":
                        command = new RemoveLastCommand();
                        break;
                    case "start":
                        break;
//                        default:
//                            return;
                }
                if (command != null)
                try {
                    clientThread.doCommand(command);
                    card.show(cardPanel, "buttons");
                    showCollection();
//                    LogTF.setText("Execute ");
                } catch (SocketException soex) {
                    soex.printStackTrace();
                    LogTF.setText(errorsBundle.getString("serv_con_err"));
                    this.setVisible(false);
                    logInWindow.setVisible(true);
                    logInWindow.connectServer();

                } catch (IOException ex) {
                    ex.printStackTrace();
                    LogTF.setText("Command Execute Error");
                } else {
                    LogTF.setText("Command Execute Error");
                }
            });
        }
        for (JButton button: new JButton[] {addButton, insertButton, removeButton})
        {
            JLabel logLabel = commandFrame.getLogLabel();
            commandFrame.getColorTF().requestFocus();
            logLabel.setText(" ");
            button.addActionListener((e) -> {

                cmndName = button.getName().toLowerCase();
                if (button.getName().equals("insert")){
                    commandFrame.setIndexVisible(true);
                } else {
                    commandFrame.setIndexVisible(false);
                }
                card.show(cardPanel, "command");

            });
        }
//--------------------------------------------------
//Creating command func
        JButton btnCmndOK = commandFrame.getOKButton();
        ActionListener comndOK = (e) -> {
            Command command = null;
            JTextField ColorTF = commandFrame.getColorTF();
            JTextField HCTF = commandFrame.getHCTF();
            JTextField OCTF = commandFrame.getOCTF();
            JTextField RCTF = commandFrame.getRCTF();
            JTextField SCTF = commandFrame.getSCTF();

            JTextField IndexTF = commandFrame.getIndexTF();
            JLabel logLabel = commandFrame.getLogLabel();
            logLabel.setText(" ");

            String colorName = ColorTF.getText();
            double HC;
            double SC;
            double OC;
            double RC;
            int index;
            try {
                CarlsonProject.plot.Color color = WindowsArrayList.getColor(colorName);
            } catch (NoSuchColorException ex){
                logLabel.setText(errorsBundle.getString("no_color"));
                return;
            }

            try{
                HC = Double.parseDouble(HCTF.getText());
                SC = Double.parseDouble(SCTF.getText());
                OC = Double.parseDouble(OCTF.getText());
                RC = Double.parseDouble(RCTF.getText());
            } catch (NumberFormatException | NullPointerException ex){
                logLabel.setText(errorsBundle.getString("double_err"));
                return;
            }
            String line = UserHandler.fromUserStringToJSONString("c:" + colorName +
                    " SC:" + SC +
                    " OC:" + OC +
                    " HC:" + HC +
                    " RC:" + RC);
            switch (cmndName){
                case "add":
                    command = new AddCommand(line);
                    break;
                case "remove":
                    command = new RemoveCommand(line);
                    break;
                case "insert":
                    try {
                        index = Integer.parseInt(IndexTF.getText());
                        if (index > (collectionSize-1)){
                            logLabel.setText(errorsBundle.getString("no_color"));
                            return;
                        }
                    } catch (NumberFormatException | NullPointerException ex){
                        logLabel.setText(errorsBundle.getString("ind_err"));
                        return;
                    }
                    command = new InsertCommand(index + " " + line);
                    break;
            }
            try {
                clientThread.doCommand(command);
                card.show(cardPanel, "buttons");
                showCollection();
//                LogTF.setText("Element Added");
            } catch (SocketException soex){
                soex.printStackTrace();
                LogTF.setText(errorsBundle.getString("serv_con_err"));
                this.setVisible(false);
                logInWindow.setVisible(true);
                logInWindow.connectServer();

            } catch (IOException ex){
                ex.printStackTrace();
                LogTF.setText(errorsBundle.getString("cmnd_err"));
            } catch (NullPointerException ex){
                ex.printStackTrace();
            }
        };
        commandFrame.getRCTF().addActionListener(comndOK);
        btnCmndOK.addActionListener(comndOK);


        for (JButton button: new JButton[] {addButton, insertButton, removeButton, startButton})
        {
            button.addActionListener((e) ->
                showCollection());
        }


        commandFrame.getCANCELButton().addActionListener((e) ->
            card.show(cardPanel, "buttons"));

        GUITools.setBackground(new JComponent[]{buttonUsrPanel, elemPanel, elemScroll,
                usrPanel, mainPanel, userLogoPanel, buttonPanel}, new Color(0x0084DB));
        card.show(cardPanel, "buttons");
    }
//--------------------------------------------------
//    RCTF.addActionListener(cmndOK);

    public void showCollection(){

        try{
            collectionSize = 0;
            scrollPanel.removeAll();
            System.out.println(clientThread.toString());
            WindowsArrayList windows = clientThread.getWindows();
            System.out.println(windows.toArray());
            if (isSorted)
                windows.sort();
            for (CarlsonProject.plot.Window window: windows.toArray()){
                System.out.println(window.toString());
                scrollPanel.add(GUITools.createElement(window));
                collectionSize++;
            }
        } catch (NullPointerException ex) { ex.printStackTrace();}
    }

    private void doCommand(Command command){
        try {
            clientThread.doCommand(command);
        } catch (IOException ex) {
            LogTF.setText(errorsBundle.getString("cmnd_err"));
        } catch (NullPointerException ex) {

        }
    }
    public JButton getChngUsrBtn() {
        return chngUsrBtn;
    }

    public JLabel getLoginLabel() {
        return loginLabel;
    }

    public void setClientThread(Client.ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    public JPanel getCommandPanel() {
        return commandPanel;
    }

    public void setLogInWindow(LogInWindow logInWindow) {
        this.logInWindow = logInWindow;
    }
}
