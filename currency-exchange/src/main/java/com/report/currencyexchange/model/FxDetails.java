package com.report.currencyexchange.model;

import java.time.LocalDate;

public class FxDetails {
	private String code;
	private LocalDate timestamp;
	private int gmtoffset ;
	private double open;
	private double high;
	private double low;
	private double close;
	private int volume;
	private double previousClose;
	private double change;
	private double change_p ;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDate getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}
	public int getGmtoffset() {
		return gmtoffset;
	}
	public void setGmtoffset(int gmtoffset) {
		this.gmtoffset = gmtoffset;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public double getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(double previousClose) {
		this.previousClose = previousClose;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getChange_p() {
		return change_p;
	}
	public void setChange_p(double change_p) {
		this.change_p = change_p;
	}
	
}
