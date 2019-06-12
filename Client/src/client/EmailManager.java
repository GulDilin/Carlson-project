package client;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.rmi.AccessException;
import java.util.Properties;

public class EmailManager {

    final private static String user = "gouldilin@gmail.com";
    final private static String password = "pass";

    private static final String HOST="smtp.gmail.com";
    private static final String PORT="587"; //gmail port

    public static synchronized void sendEmail(String to,String subject, String text) throws AddressException {
        InternetAddress recipient = new InternetAddress(to);
        Properties prop = new Properties();
        prop.put("mail.smtp.host", HOST);
        prop.put("mail.smtp.port", PORT);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, recipient);
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
            System.out.println("Pasword was sent on email");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String getPassMessage(String login, String password){
        String message = "";
        message += "Thank you for registration\n";
        message += "\tYour login: " + login +
                "\tYour password: " + password;
        return message;
    }

    public static void main(String[] args) throws AddressException {
        sendEmail("zhenyagurin@gmail.com", "Test", "Test");
    }
}