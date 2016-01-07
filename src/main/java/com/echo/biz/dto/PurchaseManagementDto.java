package com.echo.biz.dto;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseManagementDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;

	private String cntrctCd;
	private Integer dpstSeqNo;

	private List<PurchasePaymentDto> inData = new ArrayList<PurchasePaymentDto>();

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

	public List<PurchasePaymentDto> getInData() {
		return inData;
	}

	public void setInData(List<PurchasePaymentDto> inData) {
		this.inData = inData;
	}

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}
}
