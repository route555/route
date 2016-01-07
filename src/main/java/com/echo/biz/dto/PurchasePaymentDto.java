package com.echo.biz.dto;

public class PurchasePaymentDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;
	private String cntrctCd;
	private Integer dpstSeqNo;
	private String dpstDt;
	private String dpstCd;
	private String rmlrkDesc;
	private long supplyAmt;
	private long taxAmt;
	private String memoDesc;
	private String billIssueYn;
	private String billIssueDt;
	private String dpstYn;
	private String dpstPrcsDt;

	public String getCntrctCd() {
		return cntrctCd;
	}

	public void setCntrctCd(String cntrctCd) {
		this.cntrctCd = cntrctCd;
	}

	public Integer getDpstSeqNo() {
		return dpstSeqNo;
	}

	public void setDpstSeqNo(Integer dpstSeqNo) {
		this.dpstSeqNo = dpstSeqNo;
	}

	public String getDpstDt() {
		return dpstDt;
	}

	public void setDpstDt(String dpstDt) {
		this.dpstDt = dpstDt;
	}

	public String getDpstCd() {
		return dpstCd;
	}

	public void setDpstCd(String dpstCd) {
		this.dpstCd = dpstCd;
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

	public String getDpstPrcsDt() {
		return dpstPrcsDt;
	}

	public void setDpstPrcsDt(String dpstPrcsDt) {
		this.dpstPrcsDt = dpstPrcsDt;
	}

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}
}
