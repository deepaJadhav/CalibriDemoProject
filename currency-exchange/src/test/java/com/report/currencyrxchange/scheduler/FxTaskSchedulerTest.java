package com.report.currencyrxchange.scheduler;

import static
org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.report.currencyexchange.model.FxDetails;
import com.report.currencyexchange.scheduler.FxTaskScheduler;
import com.report.currencyexchange.serviceImpl.FxRatesServiceImpl;

@RunWith(SpringRunner.class)
public class FxTaskSchedulerTest {
	
    @MockBean
    private FxTaskScheduler fxTaskScheduler;
    
    @MockBean
    private FxRatesServiceImpl fxRatesMockService;


    List<FxDetails>fxdetailsList;
    String CountryCode;
    ByteArrayOutputStream emailAttachmentBody=new ByteArrayOutputStream();
    FxDetails fxDetails;
    @Before
    public void Setup() {
     MockitoAnnotations.initMocks(this);
     fxdetailsList=new ArrayList<FxDetails>();
     fxDetails=new FxDetails();
     fxDetails.setCode("FOREX:USD");
     CountryCode="FOREX:USD";
	 when(fxRatesMockService.getFxRates(CountryCode)).thenReturn(fxDetails);
	 
    }
    
    @Test
    public void TestgetFXData_ReturnFXDetailsList()
    {
    	
    	fxdetailsList.add(fxRatesMockService.getFxRates(CountryCode));
    	when(fxTaskScheduler.getFXData(CountryCode)).thenReturn(fxdetailsList);
        fxTaskScheduler.getFXData(CountryCode);
    	assertTrue(fxdetailsList.size()>0);
    }
    
	@Test
	public void testemailAttachmentNotNullwhencountryCodeNotNull() throws IOException {
		FxTaskScheduler fxTaskScheduler=new FxTaskScheduler();
		fxdetailsList.add(fxRatesMockService.getFxRates(CountryCode));
		emailAttachmentBody=fxTaskScheduler.getEmailAttachmentBodyStream(fxdetailsList);
		assertNotNull(emailAttachmentBody);
	}

}
