package com.echo.biz.dto;

public class SalePurchaseHistoryDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;

	private String trSectCd;
	private Integer hstSeqNo;
	private String cntrctCd;
	private Integer salePchsSeqNo;
	private String prcsSectCd;
	private String prcsRsnDesc;


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

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}

}
