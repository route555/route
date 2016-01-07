package com.echo.biz.dto;

public class SaleDemandDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;
	private String cntrctCd;
	private Integer dmndSeqNo;
	private String dmndDate;
	private String dmndCd;
	private String rmlrkDesc;
	private long supplyAmt;
	private long taxAmt;
	private String examCfrmYn;
	private String memoDesc;
	private String billIssueYn;
	private String billIssueDt;
	private String dpstYn;
	private String dpstDt;

	public String getCntrctCd() {
		return cntrctCd;
	}

	public void setCntrctCd(String cntrctCd) {
		this.cntrctCd = cntrctCd;
	}

	public Integer getDmndSeqNo() {
		return dmndSeqNo;
	}

	public void setDmndSeqNo(Integer dmndSeqNo) {
		this.dmndSeqNo = dmndSeqNo;
	}

	public String getDmndDate() {
		return dmndDate;
	}

	public void setDmndDate(String dmndDate) {
		this.dmndDate = dmndDate;
	}

	public String getDmndCd() {
		return dmndCd;
	}

	public void setDmndCd(String dmndCd) {
		this.dmndCd = dmndCd;
	}

	public String getRmlrkDesc() {
		return rmlrkDesc;
	}

	public void setRmlrkDesc(String rmlrkDesc) {
		this.rmlrkDesc = rmlrkDesc;
	}

	public long getSupplyAmt() {
		return supplyAmt;
	}

	public void setSupplyAmt(long supplyAmt) {
		this.supplyAmt = supplyAmt;
	}

	public long getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(long taxAmt) {
		this.taxAmt = taxAmt;
	}

	public String getExamCfrmYn() {
		return examCfrmYn;
	}

	public void setExamCfrmYn(String examCfrmYn) {
		this.examCfrmYn = examCfrmYn;
	}

	public String getMemoDesc() {
		return memoDesc;
	}

	public void setMemoDesc(String memoDesc) {
		this.memoDesc = memoDesc;
	}

	public String getBillIssueYn() {
		return billIssueYn;
	}

	public void setBillIssueYn(String billIssueYn) {
		this.billIssueYn = billIssueYn;
	}

	public String getBillIssueDt() {
		return billIssueDt;
	}

	public void setBillIssueDt(String billIssueDt) {
		this.billIssueDt = billIssueDt;
	}

	public String getDpstYn() {
		return dpstYn;
	}

	public void setDpstYn(String dpstYn) {
		this.dpstYn = dpstYn;
	}

	public String getDpstDt() {
		return dpstDt;
	}

	public void setDpstDt(String dpstDt) {
		this.dpstDt = dpstDt;
	}

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}
}
