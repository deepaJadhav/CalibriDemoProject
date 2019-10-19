package com.report.currencyexchange.controller;
import java.io.IOException;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.report.currencyexchange.scheduler.FxTaskScheduler;
import com.report.currencyexchange.service.FxRatesService;

@RestController
public class ReportController {
	@Autowired
	FxTaskScheduler fxtaskScheduler;
	
	@GetMapping("/test/reports")
    public String getFxDetailList() throws MessagingException, IOException {
		fxtaskScheduler.sendFxDataMail();
       	return "tested";
    }
    

}
