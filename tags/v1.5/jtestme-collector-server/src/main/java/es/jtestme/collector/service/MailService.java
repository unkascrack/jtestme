package es.jtestme.collector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private transient MailSender mailTemplate;

    @Autowired
    private transient ConfigurationService configuration;

    public void sendMessage(String mailFrom, String subject, String mailTo, String message) {
        org.springframework.mail.SimpleMailMessage mailMessage = new org.springframework.mail.SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setSubject(subject);
        mailMessage.setTo(mailTo);
        mailMessage.setText(message);
        mailTemplate.send(mailMessage);
    }

    public void sendMessage(String[] mailTo, String subject, String message) {
        org.springframework.mail.SimpleMailMessage mailMessage = new org.springframework.mail.SimpleMailMessage();
        mailMessage.setFrom(configuration.getMailFrom());
        mailMessage.setSubject(new StringBuilder().append(configuration.getSubjectPrefix()).append(" ").append(subject)
                .toString().trim());
        mailMessage.setTo(mailTo);
        mailMessage.setText(message);
        mailTemplate.send(mailMessage);
    }
}
