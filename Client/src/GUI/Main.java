package GUI;

import client.Client;

public class Main {
    public static void main(String[] args) {
        String langs[] = {"Русский", "English"};
        try {
//            Boolean isLogin = false;
            Client client = new Client("localhost", Integer.valueOf(args[0]));
//            javax.swing.SwingUtilities.invokeLater(() ->{new LogInWindow(client);});

            ActionFrame actionFrame = new ActionFrame(langs);
            LogInWindow logInFrame = new LogInWindow(client, actionFrame);
            logInFrame.setVisible(true);
            actionFrame.setLocationRelativeTo(null);

            logInFrame.setLocationRelativeTo(null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
