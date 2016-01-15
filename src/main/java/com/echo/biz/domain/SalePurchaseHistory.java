package com.echo.biz.domain;

import com.echo.biz.dto.SalePurchaseHistoryDto;
import com.echo.framework.domain.BaseDomain;

public class SalePurchaseHistory extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	private String trSectCd;
	private Integer hstSeqNo;
	private String cntrctCd;
	private Integer salePchsSeqNo;
	private String prcsSectCd;
	private String prcsRsnDesc;

	
	public SalePurchaseHistory() {

	}

	public SalePurchaseHistory(SalePurchaseHistoryDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.trSectCd = dto.getTrSectCd();
		this.hstSeqNo = dto.getHstSeqNo();
		this.cntrctCd = dto.getCntrctCd();
		this.salePchsSeqNo = dto.getSalePchsSeqNo();
		this.prcsSectCd = dto.getPrcsSectCd();
		this.prcsRsnDesc = dto.getPrcsRsnDesc();

	}
	
	public String getTrSectCd() {
		return trSectCd;
	}

	public void setTrSectCd(String trSectCd) {
		this.trSectCd = trSectCd;
	}

	public Integer getHstSeqNo() {
		return hstSeqNo;
	}

	public void setHstSeqNo(Integer hstSeqNo) {
		this.hstSeqNo = hstSeqNo;
	}

	public String getCntrctCd() {
		return cntrctCd;
	}

	public void setCntrctCd(String cntrctCd) {
		this.cntrctCd = cntrctCd;
	}

	public Integer getSalePchsSeqNo() {
		return salePchsSeqNo;
	}

	public void setSalePchsSeqNo(Integer salePchsSeqNo) {
		this.salePchsSeqNo = salePchsSeqNo;
	}

	public String getPrcsSectCd() {
		return prcsSectCd;
	}

	public void setPrcsSectCd(String prcsSectCd) {
		this.prcsSectCd = prcsSectCd;
	}

	public String getPrcsRsnDesc() {
		return prcsRsnDesc;
	}

	public void setPrcsRsnDesc(String prcsRsnDesc) {
		this.prcsRsnDesc = prcsRsnDesc;
	}




	


}
