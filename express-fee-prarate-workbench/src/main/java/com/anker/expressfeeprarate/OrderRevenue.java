package com.anker.expressfeeprarate;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class OrderRevenue implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	private java.lang.String date_month;
	private java.lang.String marketplace;
	private java.lang.String bg_ptd;
	private double amount;

	private double rate3;

	private double fee;

	public OrderRevenue() {
	}

	public java.lang.String getDate_month() {
		return this.date_month;
	}

	public void setDate_month(java.lang.String date_month) {
		this.date_month = date_month;
	}

	public java.lang.String getMarketplace() {
		return this.marketplace;
	}

	public void setMarketplace(java.lang.String marketplace) {
		this.marketplace = marketplace;
	}

	public java.lang.String getBg_ptd() {
		return this.bg_ptd;
	}

	public void setBg_ptd(java.lang.String bg_ptd) {
		this.bg_ptd = bg_ptd;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getRate3() {
		return this.rate3;
	}

	public void setRate3(double rate3) {
		this.rate3 = rate3;
	}

	public double getFee() {
		return this.fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public OrderRevenue(java.lang.String date_month,
			java.lang.String marketplace, java.lang.String bg_ptd,
			double amount, double rate3, double fee) {
		this.date_month = date_month;
		this.marketplace = marketplace;
		this.bg_ptd = bg_ptd;
		this.amount = amount;
		this.rate3 = rate3;
		this.fee = fee;
	}

}