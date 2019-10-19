package com.report.currencyexchange.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.tomcat.util.json.JSONParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.report.currencyexchange.exception.FxDetailsNotFetchException;
import com.report.currencyexchange.model.FxDetails;
import com.report.currencyexchange.service.FxRatesService;

@Service
public class FxRatesServiceImpl implements FxRatesService {
	
	@Value("${fxDetails.apiToken}")
	String apiToken;
	
	@Value("${fxDetails.URI}")
	String fxDetailsURI;
	
	private static final Logger logger = LoggerFactory.getLogger(FxRatesServiceImpl.class);
	
	@Override
	public FxDetails getFxRates(String countryCode) throws FxDetailsNotFetchException{
		// TODO Auto-generated method stub
		if(countryCode==null||countryCode.isEmpty())
			throw new FxDetailsNotFetchException("Country code is null");
			
		Map<String, String> vars = new HashMap<>();
		vars.put("fxCountry", countryCode);
		vars.put("apiToken", apiToken);
		RestTemplate restTemplate = new RestTemplate();
		FxDetails fxDetails=null;
		try {
			
			//ResponseEntity<FxDetails> resentity=restTemplate.getForObject(fxDetailsURI, FxDetails.class, vars);
			ResponseEntity<FxDetails> resentity=restTemplate.getForEntity(fxDetailsURI, FxDetails.class, vars);
            if(resentity!=null&&resentity.getStatusCode().equals(HttpStatus.OK))
            fxDetails=resentity.getBody();
            logger.info("response entity status"+resentity.getStatusCode());
                	
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage().toString());
			throw ex;
		}
		return fxDetails;
	}
	
	//ResponseEntity<String> resentity=restTemplate.getForEntity("https://eodhistoricaldata.com/api/real-time/{fxCountry}?api_token={apiToken}&fmt=json",

}
