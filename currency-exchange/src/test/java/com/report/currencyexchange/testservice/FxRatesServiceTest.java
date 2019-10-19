package com.report.currencyexchange.testservice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.report.currencyexchange.exception.FxDetailsNotFetchException;
import com.report.currencyexchange.model.FxDetails;
import com.report.currencyexchange.service.FxRatesService;
import com.report.currencyexchange.serviceImpl.FxRatesServiceImpl;

@RunWith(SpringRunner.class)
public class FxRatesServiceTest {
	
    @MockBean
    private FxRatesServiceImpl fxRatesMockService;
    
    private FxRatesService fxRatesServiceImpl;
    private FxDetails fxdetails;
    private RestTemplate restTemplate;
    
    @Before
    public void Setup() {
     MockitoAnnotations.initMocks(this);
	 fxdetails=new FxDetails();
     fxRatesServiceImpl=new FxRatesServiceImpl();
     restTemplate=new RestTemplate();
	 when(fxRatesMockService.getFxRates(Matchers.anyString())).thenReturn(fxdetails);
    }

	@Test
	public void getFxRatesReturnsFxDetailsObject() {
		assertEquals(fxRatesMockService.getFxRates("FOREX.JPY"),fxdetails);
	}
	
	@Test(expected=FxDetailsNotFetchException.class)
	public void getFxRatesReturnsExceptionWhenCountryCodeNull() {
		fxRatesServiceImpl.getFxRates(null);
	}


}
