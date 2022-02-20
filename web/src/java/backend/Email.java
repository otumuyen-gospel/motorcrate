/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.io.File;
import java.time.LocalDate;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


/**
 *
 * @author user1
 */
public class Email {
      public static String status = "";
    public void doSend(String host, String password, String username,String port,
            String from, String to, String subject, String html,String facebook,String instagram,String twitter){
        
          Properties props = new Properties();
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.smtp.host", host);
          props.put("mail.smtp.port", port);
          props.put("mail.smtp.ssl.trust", host);

          // Get the Session object.
          Session session = Session.getInstance(props,
             new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(username, password);
                }
            });

          try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

               // Set From: header field of the header.
               message.setFrom(new InternetAddress(from));

               // Set To: header field of the header.
               message.setRecipients(Message.RecipientType.TO,
                  InternetAddress.parse(to));

               // Set Subject: header field
               message.setSubject(subject);
               
               String socialMedia = "<center><nav>"+
                       "<a href='https://"+facebook+"'>facebook</a><br/><br/>"+
                       "<a href='https://"+instagram+"'>instagram</a><br/><br/>"+
                       "<a href='https://"+twitter+"'>twitter</a><br/><br/></nav></center>";
               // Send the actual HTML message, as big as you like
               String body  = "<!DOCTYPE html>\n" +
                            "<!--\n" +
                            "To change this license header, choose License Headers in Project Properties.\n" +
                            "To change this template file, choose Tools | Templates\n" +
                            "and open the template in the editor.\n" +
                            "-->\n" +
                            "<html>\n" +
                            "    <head>\n" +
                            "        <meta charset=\"UTF-8\">\n" +
                            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "        <style>\n" +
                            "            body , header h2{\n" +
                            "                margin: 0px;\n" +
                            "                font-family: verdana;\n" +
                            "            }\n" +
                            "            header h2 img{\n" +
                            "                height: 48px; \n" +
                            "                width: 90px;\n" +
                            "            }\n" +
                            "            header{\n" +
                            "                background-color:#ccc;\n" +
                            "                color:#fff; \n" +
                            "                text-align: center; \n" +
                            "                padding: 10px;\n" +
                            "            }\n" +
                            "            main{\n" +
                            "                color:#000;\n" +
                            "                font-size: 13px;\n" +
                            "                background-color: #fff;\n" +
                            "                text-align: center;\n" +
                            "                margin-top: 30px;\n" +
                            "            }\n"  +
                            "            footer{\n" +
                            "                color:#000;\n" +
                            "                background-color: #fff; \n" +
                            "                font-size: 13px;\n" +
                            "                text-align: center;\n" +
                            "                padding: 10px;\n" +
                            "            }\n" +
                            "        </style>\n" +
                            "    </head>\n" +
                            "    <body>\n" +
                            "        <header>\n" +
                            "            <h2><img src=\"cid:logo\"></h2>\n" +
                            "        </header>\n" +
                            "        <main>\n" +
                            "            \n" +html+
                            "        </main>\n" +
                            "        <footer>\n" +
                            "            <p>&copy; copyright "+LocalDate.now().getYear()
                            + " motor crate<br/><br/>"+from+"</p>\n" +socialMedia+
                            "        </footer>\n" +
                            "    </body>\n" +
                            "</html>\n" +
                            "";
               
               MimeMultipart multipart = new MimeMultipart("related");

              // first part (the html)
              BodyPart messageBodyPart = new MimeBodyPart();
              messageBodyPart.setContent(body, "text/html");
              // add it
              multipart.addBodyPart(messageBodyPart);

              // second part (the image)
              messageBodyPart = new MimeBodyPart();
              File file = new File(getClass().getClassLoader().getResource("logo.png").getFile());
              DataSource fds = new FileDataSource(file.getAbsolutePath());

              messageBodyPart.setDataHandler(new DataHandler(fds));
              messageBodyPart.setHeader("Content-ID", "<logo>");

              // add image to the multipart
              multipart.addBodyPart(messageBodyPart);

              // put everything together
              message.setContent(multipart);

               // Send message
               Transport.send(message);

               status = "Sent message successfully....";

          } catch (Exception e) {
               status = e.getMessage();
          }

           
    }
    
    public void Contact(String host, String password, String username,String port,
            String from, String to, String subject, String text){
        
          Properties props = new Properties();
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.smtp.host", host);
          props.put("mail.smtp.port", port);
          props.put("mail.smtp.ssl.trust", host);

          // Get the Session object.
          Session session = Session.getInstance(props,
             new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(username, password);
                }
            });

          try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

               // Set From: header field of the header.
               message.setFrom(new InternetAddress(from));

               // Set To: header field of the header.
               message.setRecipients(Message.RecipientType.TO,
                  InternetAddress.parse(to));

               // Set Subject: header field
               message.setSubject(subject);

               
               message.setContent(text,"text/plain");

               // Send message
               Transport.send(message);

               status = "Sent message successfully....";

          } catch (Exception e) {
               status = e.getMessage();
          }

           
    }
    
}
