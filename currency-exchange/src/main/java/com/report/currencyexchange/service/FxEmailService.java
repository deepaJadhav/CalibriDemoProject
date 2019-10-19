package com.report.currencyexchange.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

public interface FxEmailService {
	public boolean emailSend(ByteArrayOutputStream emailAttachment, String emailAddress,String subject,String emailText,boolean attachment)throws MessagingException ;
}