package info.puton.practice.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by taoyang on 2016/8/7.
 */
public class MailUtil {

    public static boolean sendMail(String to, String cc , String title, String content){

        try {

            // get system properties
            final Properties props = new Properties();

            // setup mail server
            props.put("mail.smtp.host","smtp.163.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.user", "putoninfo@163.com");
            props.put("mail.password", "ty11235813");

            // create authenticator for validating
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };

            // create session with props and authenticator
            Session session = Session.getInstance(props, authenticator);
            // create message
            MimeMessage message = new MimeMessage(session);
            // setup sender
            InternetAddress from = new InternetAddress(props.getProperty("mail.user"),"Mailer");
            message.setFrom(from);
            // setup receiver & carbon copy
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            if(cc!=null){
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            }
            // setup title
            message.setSubject(MimeUtility.encodeText(title, MimeUtility.mimeCharset("UTF-8"), null));
            // setup content
            message.setContent(content, "text/html;charset=UTF-8");
            // send mail
            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean sendMail(String to, String title, String content) {
        return MailUtil.sendMail(to, null, title, content);
    }

    public static void main(String[] args) {

        MailUtil.sendMail("11457796@163.com","测试标题","测试内容");

    }

}
