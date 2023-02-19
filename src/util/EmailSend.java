package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailSend {

    public static boolean sendEmail(String recepient) throws Exception {
        try {

            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.host", "smtp.gmail.com");

            String myAccountEmail = "mc.enterprises00@gmail.com";
            String password = "nawcicagvxvlsyrc";

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password);
                }
            });
            Message message = preporeMessage(session, myAccountEmail, recepient);
            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Email not send");
            return false;
        }
    }

    private static Message preporeMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Fire Warning !!!");
            message.setText("Fire Warning !!!");
            message.setFrom(new InternetAddress(myAccountEmail));
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
