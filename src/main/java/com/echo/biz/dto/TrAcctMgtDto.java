package com.echo.biz.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class TrAcctMgtDto extends BasePmsDto {
	private static final long serialVersionUID = 1L;
	private String trAcctCd;
	private String trAcctNm;
	private String ceoNm;
	private String bizGrndAddr;
	private String mnShpAddr;
	private String lawRgtNo1;
	private String lawRgtNo2;
	private String bizRgtNo;
	private String bizStatuNm;
	private String bizDtlNm;
	private String opnDt;
	private String dpstExpctDayDesc;
	private String emoDesc;
	private String rgtTm;
	private String rgtrId;
	private String uptTm;
	private String uptrId;
	
	private String chgrSeqNo;
	private String lawRgtNo;
	private String chgSectNm;
	private String chgrNm;
	private String pstnNm;
    private String  telNo;
    private String  hpNo;
    private String  emailAddr;
    
    private String bizRgtNo1;
    private String bizRgtNo2;
    private String bizRgtNo3;
    private String chgSectCd;
    
    private List trAcctChgrList;
    
    
	public TrAcctMgtDto(String chgSectCd) {
    	this.chgSectCd = chgSectCd;
    }
    
    public List getTrAcctChgrList() {
		List list = new ArrayList();
		
		TrAcctMgtDto dto = new TrAcctMgtDto(getChgSectCd());
		
		list.add(dto);
    	
		return list;
	}



	public void setTrAcctChgrList(List trAcctChgrList) {
		this.trAcctChgrList = trAcctChgrList;
	}
    
    

	public String getChgSectCd() {
		return chgSectCd;
	}



	public void setChgSectCd(String chgSectCd) {
		this.chgSectCd = chgSectCd;
	}



	public String getBizRgtNo1() {
		return bizRgtNo1;
	}


	public void setBizRgtNo1(String bizRgtNo1) {
		this.bizRgtNo1 = bizRgtNo1;
	}


	public String getBizRgtNo2() {
		return bizRgtNo2;
	}


	public void setBizRgtNo2(String bizRgtNo2) {
		this.bizRgtNo2 = bizRgtNo2;
	}


	public String getBizRgtNo3() {
		return bizRgtNo3;
	}


	public void setBizRgtNo3(String bizRgtNo3) {
		this.bizRgtNo3 = bizRgtNo3;
	}


	public String getTrAcctCd() {
		return trAcctCd;
	}


	public void setTrAcctCd(String trAcctCd) {
		this.trAcctCd = trAcctCd;
	}


	public String getTrAcctNm() {
		return trAcctNm;
	}


	public void setTrAcctNm(String trAcctNm) {
		this.trAcctNm = trAcctNm;
	}


	public String getCeoNm() {
		return ceoNm;
	}


	public void setCeoNm(String ceoNm) {
		this.ceoNm = ceoNm;
	}


	public String getBizGrndAddr() {
		return bizGrndAddr;
	}


	public void setBizGrndAddr(String bizGrndAddr) {
		this.bizGrndAddr = bizGrndAddr;
	}


	public String getMnShpAddr() {
		return mnShpAddr;
	}


	public void setMnShpAddr(String mnShpAddr) {
		this.mnShpAddr = mnShpAddr;
	}


	public String getLawRgtNo1() {
		return lawRgtNo1;
	}


	public void setLawRgtNo1(String lawRgtNo1) {
		this.lawRgtNo1 = lawRgtNo1;
	}


	public String getLawRgtNo2() {
		return lawRgtNo2;
	}


	public void setLawRgtNo2(String lawRgtNo2) {
		this.lawRgtNo2 = lawRgtNo2;
	}


	public String getBizRgtNo() {
		return bizRgtNo;
	}


	public void setBizRgtNo(String bizRgtNo) {
		this.bizRgtNo = bizRgtNo;
	}


	public String getBizStatuNm() {
		return bizStatuNm;
	}


	public void setBizStatuNm(String bizStatuNm) {
		this.bizStatuNm = bizStatuNm;
	}


	public String getBizDtlNm() {
		return bizDtlNm;
	}


	public void setBizDtlNm(String bizDtlNm) {
		this.bizDtlNm = bizDtlNm;
	}


	public String getOpnDt() {
		return opnDt;
	}


	public void setOpnDt(String opnDt) {
		this.opnDt = opnDt;
	}


	public String getDpstExpctDayDesc() {
		return dpstExpctDayDesc;
	}


	public void setDpstExpctDayDesc(String dpstExpctDayDesc) {
		this.dpstExpctDayDesc = dpstExpctDayDesc;
	}


	public String getEmoDesc() {
		return emoDesc;
	}


	public void setEmoDesc(String emoDesc) {
		this.emoDesc = emoDesc;
	}


	public String getRgtTm() {
		return rgtTm;
	}


	public void setRgtTm(String rgtTm) {
		this.rgtTm = rgtTm;
	}


	public String getRgtrId() {
		return rgtrId;
	}


	public void setRgtrId(String rgtrId) {
		this.rgtrId = rgtrId;
	}


	public String getUptTm() {
		return uptTm;
	}


	public void setUptTm(String uptTm) {
		this.uptTm = uptTm;
	}


	public String getUptrId() {
		return uptrId;
	}


	public void setUptrId(String uptrId) {
		this.uptrId = uptrId;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	


	public String getChgrSeqNo() {
		return chgrSeqNo;
	}


	public void setChgrSeqNo(String chgrSeqNo) {
		this.chgrSeqNo = chgrSeqNo;
	}


	public String getLawRgtNo() {
		return lawRgtNo;
	}


	public void setLawRgtNo(String lawRgtNo) {
		this.lawRgtNo = lawRgtNo;
	}


	public String getChgSectNm() {
		return chgSectNm;
	}


	public void setChgSectNm(String chgSectNm) {
		this.chgSectNm = chgSectNm;
	}


	public String getChgrNm() {
		return chgrNm;
	}


	public void setChgrNm(String chgrNm) {
		this.chgrNm = chgrNm;
	}


	public String getPstnNm() {
		return pstnNm;
	}


	public void setPstnNm(String pstnNm) {
		this.pstnNm = pstnNm;
	}


	public String getTelNo() {
		return telNo;
	}


	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}


	public String getHpNo() {
		return hpNo;
	}


	public void setHpNo(String hpNo) {
		this.hpNo = hpNo;
	}


	public String getEmailAddr() {
		return emailAddr;
	}


	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}


	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
