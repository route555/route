package com.echo.biz.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleManagementDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;

	private String cntrctCd;
	private Integer dmndSeqNo;
	private String billIssueDt;
	
	private List<SaleDemandDto> inData = new ArrayList<SaleDemandDto>();

	public List<SaleDemandDto> getInData() {
		return inData;
	}

	public void setInData(List<SaleDemandDto> inData) {
		this.inData = inData;
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

	public String getBillIssueDt() {
		return billIssueDt;
	}

	public void setBillIssueDt(String billIssueDt) {
		this.billIssueDt = billIssueDt;
	}

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}
}
