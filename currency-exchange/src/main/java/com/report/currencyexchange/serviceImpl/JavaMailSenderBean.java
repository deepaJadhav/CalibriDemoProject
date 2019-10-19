package com.report.currencyexchange.serviceImpl;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailSenderBean {
	
	@Value("${fxDetails.adminEmail}")
	String adminEmail;
	
	@Value("${fxDetails.adminPassword}")
	String adminPassword;

	@Value("${fxDetails.mailHost}")
	String mailhost;

	
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	mailSender.setHost(mailhost);
	mailSender.setPort(587);//SendEmail//udqaikwjhjegvbcl
	mailSender.setUsername(adminEmail);//
	mailSender.setPassword(adminPassword);
	Properties props = mailSender.getJavaMailProperties();
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("SendEmail", "udqaikwjhjegvbcl");
		      }
		   });

	mailSender.setJavaMailProperties(props);
	return mailSender;
	}


}
