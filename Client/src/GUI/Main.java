package GUI;

import client.Client;

public class Main {
    public static void main(String[] args) {
        String langs[] = {"Русский", "English"};
        Boolean isLogin = Boolean.valueOf(false);
        try {
//            Boolean isLogin = false;
//            Client client = new Client("localhost", 1489);
//            javax.swing.SwingUtilities.invokeLater(() ->{new LogInWindow(client);});

            ActionFrame actionFrame = new ActionFrame(langs, isLogin);
            LogInWindow logInFrame = new LogInWindow(actionFrame, isLogin);
            logInFrame.setVisible(true);
            actionFrame.setLocationRelativeTo(null);

            logInFrame.setLocationRelativeTo(null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
