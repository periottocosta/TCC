package controle;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailApp {

    public String Email(String email, String mensg) {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
         */
        // String senhaGerada,  String login ,
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("programador233@gmail.com", "programador4");
            }
        });
        session.setDebug(true);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("programador233@gmail.com")); //Remetente
            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(email);
            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Trust Imobiliaria");//Assunto
            message.setText(mensg);
            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);

            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
