package com.report.currencyexchange.testservice;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.report.currencyexchange.exception.FxDetailsNotFetchException;
import com.report.currencyexchange.exception.InvalidEmailInputException;
import com.report.currencyexchange.service.FxEmailService;
import com.report.currencyexchange.serviceImpl.EmailServiceImpl;
import com.report.currencyexchange.serviceImpl.FxRatesServiceImpl;

@RunWith(SpringRunner.class)
public class EmailServiceTest {
    @MockBean
    private FxEmailService mockemailService;
    
    private EmailServiceImpl emailService;
    
    @SuppressWarnings("deprecation")
	@Before
    public void Setup() throws MessagingException 
    {
        MockitoAnnotations.initMocks(this);
        emailService=new EmailServiceImpl();
   	    when(mockemailService.emailSend(Matchers.any(),Matchers.anyString(),Matchers.anyString(),Matchers.anyString(),Matchers.anyBoolean())).thenReturn(true);

    }

	@Test(expected=InvalidEmailInputException.class)
	public void testIfEmailAnyInputNull_ReturnException() throws MessagingException,InvalidEmailInputException {
		emailService.emailSend(null, null, null, null,false);
	}

	@Test
	public void testEmailSendReturnsTrue() throws MessagingException {
		boolean sendEmail=mockemailService.emailSend(null, "deepa123jadhav@yahoo.com", "This is Test Email Body", "Test Email",false);
		assertTrue(sendEmail);
	}

	

}
