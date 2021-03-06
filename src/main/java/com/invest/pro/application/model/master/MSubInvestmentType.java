package com.invest.pro.application.model.master;// default package
// Generated Sep 27, 2015 7:52:02 PM by Hibernate Tools 3.4.0.CR1

/**
 * MSubInvestmentType generated by hbm2java
 */
public class MSubInvestmentType implements java.io.Serializable {

	private Long id;
	private String investmentCd;
	private String investmentName;
	private Boolean active;

	public MSubInvestmentType() {
	}

	public MSubInvestmentType(String investmentCd, String investmentName) {
		this.investmentCd = investmentCd;
		this.investmentName = investmentName;
	}

	public MSubInvestmentType(String investmentCd, String investmentName,
			Boolean active) {
		this.investmentCd = investmentCd;
		this.investmentName = investmentName;
		this.active = active;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvestmentCd() {
		return this.investmentCd;
	}

	public void setInvestmentCd(String investmentCd) {
		this.investmentCd = investmentCd;
	}

	public String getInvestmentName() {
		return this.investmentName;
	}

	public void setInvestmentName(String investmentName) {
		this.investmentName = investmentName;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
