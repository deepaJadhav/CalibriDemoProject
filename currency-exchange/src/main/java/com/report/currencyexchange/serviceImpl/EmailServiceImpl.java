package com.report.currencyexchange.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.report.currencyexchange.exception.InvalidEmailInputException;
import com.report.currencyexchange.service.FxEmailService;

@Service
public class EmailServiceImpl implements FxEmailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${fxDetails.adminEmail}")
	String adminEmail;


	@Override
	public boolean emailSend(ByteArrayOutputStream emailAttachment, String emailAddress,String subject,String emailText,boolean attachment) throws MessagingException{
		// TODO Auto-generated method stub
		if(emailAddress==null||adminEmail==null||emailAddress.isEmpty()||adminEmail.isEmpty())
			throw new InvalidEmailInputException("Email Adress is Empty");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        LocalDateTime localDate=LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter
                .ofPattern("yyyyMMdd_HHmm");
        String fileName="obsval"+localDate.format(dateFormatter)+".csv";
        helper.setSubject(subject);
        helper.setFrom(adminEmail);
        helper.setTo(emailAddress);
        helper.setText(emailText, false);
        System.out.println("read"+emailAddress);
        if(attachment) {
        	if(emailAttachment==null)
    			throw new InvalidEmailInputException("emailAttachment is Empty");
		    byte[] bytes = emailAttachment.toByteArray();
		    DataSource dataSource = new ByteArrayDataSource(bytes, "application/csv");
		    helper.addAttachment(fileName, dataSource);
        }
        try {
          javaMailSender.send(message);
          System.out.println("emailsend");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
       	return true;
	}
	

}
