package com.report.currencyexchange.service;

import java.util.List;

import com.report.currencyexchange.model.FxDetails;

public interface FxRatesService {
        public FxDetails getFxRates(String countryCode);

        
}
