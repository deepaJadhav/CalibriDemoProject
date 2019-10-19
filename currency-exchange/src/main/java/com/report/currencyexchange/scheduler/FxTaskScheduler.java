package com.report.currencyexchange.scheduler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.report.currencyexchange.exception.FxDetailsNotFetchException;
import com.report.currencyexchange.model.FxDetails;
import com.report.currencyexchange.service.FxEmailService;
import com.report.currencyexchange.service.FxRatesService;
import com.report.currencyexchange.serviceImpl.FxRatesServiceImpl;

@Component
public class FxTaskScheduler {
	@Autowired
	FxRatesService fxService;
	
	@Autowired
	FxEmailService emailService;

	@Value("${fxDetails.fxCountryList}")
	String fxCountries;
	
	@Value("${fxDetails.clientEmail}")
	String clinetEmail;
	
	@Value("${fxDetails.clientEmailSubject}")
	String clientEmailSubject;
	
	@Value("${fxDetails.clientEmailText}")
	String clientEmailText;
	
	@Value("${fxDetails.clientEmailFailureText}")
	String clientEmailFailureText;
	
	@Value("${fxDetails.adminEmail}")
	String adminEmail;
	
	
	  private static final Logger logger = LoggerFactory.getLogger(FxTaskScheduler.class);


	     @Scheduled(cron = "${fxDetails.cornExpression}")
         public void sendFxDataMail() throws MessagingException, IOException {
		 ByteArrayOutputStream emailAttachmentStream=new ByteArrayOutputStream();
		 try
		 {
			 List<FxDetails> fxdetailsList=getFXData(fxCountries);
			 emailAttachmentStream=getEmailAttachmentBodyStream(fxdetailsList);
			 emailService.emailSend(emailAttachmentStream, clinetEmail, clientEmailSubject, clientEmailText, true);
			 emailAttachmentStream.close();
		 }
		 catch(Exception ex)
		 {
			  logger.error("Exception sending email"+ex.getMessage());
			  String failurMessage=clientEmailFailureText+"\n"+ex.toString();
			  emailService.emailSend(emailAttachmentStream, clinetEmail, clientEmailSubject, failurMessage, false);
			  emailService.emailSend(emailAttachmentStream, adminEmail, clientEmailSubject, failurMessage, false);
		 }
    }
    
	     public List<FxDetails> getFXData(String countrycodes)throws FxDetailsNotFetchException
	     {
	     
	        List<FxDetails> fxdetailsList=new ArrayList<FxDetails>();
	  	    String[] fxContryList=fxCountries.split(",");
 	        for(String countryCode:fxContryList) {
 	    	   FxDetails fxDetails=fxService.getFxRates(countryCode);
 	    	   if(fxDetails!=null) {
 	    		  fxdetailsList.add(fxDetails);
 	    	   }
 	        }
 	        return fxdetailsList;

	     }
	     public ByteArrayOutputStream getEmailAttachmentBodyStream(List<FxDetails> fxdetailsList)throws IOException{
	         StringBuffer email=new StringBuffer();
	         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	         email.append("FOREX,VALUE");
	         email.append("\n");
	         for(FxDetails fxdetails:fxdetailsList)
	         {
	 	           email.append(fxdetails.getCode()).append(",");
		           email.append(fxdetails.getClose()).append("\n");
	         }
	         outputStream.write(email.toString().getBytes());
	         return outputStream;
	     }
	/*public ByteArrayOutputStream getEmailAttachmentStream()throws IOException,FxDetailsNotFetchException
	{
        StringBuffer email=new StringBuffer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        email.append("FOREX,VALUE");
        email.append("\n");
 	    String[] fxContryList=fxCountries.split(",");
 	        for(String countryCode:fxContryList) {
 	    	   FxDetails fxDetails=fxService.getFxRates(countryCode);
 	    	   if(fxDetails!=null) {
 	           email.append(fxDetails.getCode()).append(",");
	           email.append(fxDetails.getClose()).append("\n");
 	          }
 	    }
        outputStream.write(email.toString().getBytes());
        return outputStream;
	 }*/

}
