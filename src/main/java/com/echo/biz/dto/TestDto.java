package com.echo.biz.dto;

public class TestDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;

	private String cntrctCd;
	private Integer dmndSeqNo;
	private String trAcctNm;
	private String prjtNm;
	private String workStartDt;
	private String workEndDt;
	private String dmndCd;
	private String dmndDate;
	private String rmlrkDesc;
	private String examNeedYn;
	private String examCfrmYn;
	private String chgrNm;
	private String telNo;
	private String billIssueDt;
	private String dpstExpctDayCd;
	private String dpstDt;
	private Integer supplyAmt;
	private Integer taxAmt;
	private Integer totalAmt;
	private Integer rnum;

	

	public Integer getRnum() {
		return rnum;
	}

	public void setRnum(Integer rnum) {
		this.rnum = rnum;
	}

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

	public String getTrAcctNm() {
		return trAcctNm;
	}

	public void setTrAcctNm(String trAcctNm) {
		this.trAcctNm = trAcctNm;
	}

	public String getPrjtNm() {
		return prjtNm;
	}

	public void setPrjtNm(String prjtNm) {
		this.prjtNm = prjtNm;
	}

	public String getWorkStartDt() {
		return workStartDt;
	}

	public void setWorkStartDt(String workStartDt) {
		this.workStartDt = workStartDt;
	}

	public String getWorkEndDt() {
		return workEndDt;
	}

	public void setWorkEndDt(String workEndDt) {
		this.workEndDt = workEndDt;
	}

	public String getDmndCd() {
		return dmndCd;
	}

	public void setDmndCd(String dmndCd) {
		this.dmndCd = dmndCd;
	}

	public String getDmndDate() {
		return dmndDate;
	}

	public void setDmndDate(String dmndDate) {
		this.dmndDate = dmndDate;
	}

	public String getRmlrkDesc() {
		return rmlrkDesc;
	}

	public void setRmlrkDesc(String rmlrkDesc) {
		this.rmlrkDesc = rmlrkDesc;
	}

	public String getExamNeedYn() {
		return examNeedYn;
	}

	public void setExamNeedYn(String examNeedYn) {
		this.examNeedYn = examNeedYn;
	}

	public String getExamCfrmYn() {
		return examCfrmYn;
	}

	public void setExamCfrmYn(String examCfrmYn) {
		this.examCfrmYn = examCfrmYn;
	}

	public String getChgrNm() {
		return chgrNm;
	}

	public void setChgrNm(String chgrNm) {
		this.chgrNm = chgrNm;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getBillIssueDt() {
		return billIssueDt;
	}

	public void setBillIssueDt(String billIssueDt) {
		this.billIssueDt = billIssueDt;
	}

	public String getDpstExpctDayCd() {
		return dpstExpctDayCd;
	}

	public void setDpstExpctDayCd(String dpstExpctDayCd) {
		this.dpstExpctDayCd = dpstExpctDayCd;
	}

	public String getDpstDt() {
		return dpstDt;
	}

	public void setDpstDt(String dpstDt) {
		this.dpstDt = dpstDt;
	}

	public Integer getSupplyAmt() {
		return supplyAmt;
	}

	public void setSupplyAmt(Integer supplyAmt) {
		this.supplyAmt = supplyAmt;
	}

	public Integer getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(Integer taxAmt) {
		this.taxAmt = taxAmt;
	}

	public Integer getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Integer totalAmt) {
		this.totalAmt = totalAmt;
	}

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}
}
