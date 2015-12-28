package com.echo.biz.domain;

import com.echo.biz.dto.PersonDto;
import com.echo.framework.domain.BaseDomain;

public class Person extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private Integer prsnNo;
	private String prsnNm;
	private String juminNo1;
	private String juminNo2;
	private String sexCd;
	private String acdmcCd;
	private String workStartDt;
	private String certCd;
	private String rsdncAddr;
	private String cntrctSectCd;
	private String lawRgtNo1;
	private String lawRgtNo2;
	private String bizRgtNo;
	private String lawBizNm;
	private String ceoNm;
	private String prsnEmailAddr;
	private String telNo;
	private String hpNo;
	private String trBankNm;
	private String trAcctNo;
	private String trDpstrNm;
	private String payDayCd;
	private String memoDesc;
	private String prflAtchtFlNo;

	private String skillSectCd;
	private String fileName;
	
	
	public Person() {

	}

	public Person(PersonDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.prsnNo = dto.getPrsnNo();
		this.prsnNm = dto.getPrsnNm();
		this.juminNo1 = dto.getJuminNo1();
		this.juminNo2 = dto.getJuminNo2();
		this.sexCd = dto.getSexCd();
		this.acdmcCd = dto.getAcdmcCd();
		this.workStartDt = dto.getWorkStartDt();
		this.certCd = dto.getCertCd();
		this.rsdncAddr = dto.getRsdncAddr();
		this.cntrctSectCd = dto.getCntrctSectCd();
		this.lawRgtNo1 = dto.getLawRgtNo1();
		this.lawRgtNo2 = dto.getLawRgtNo2();
		this.bizRgtNo = dto.getBizRgtNo();
		this.lawBizNm = dto.getLawBizNm();
		this.ceoNm = dto.getCeoNm();
		this.prsnEmailAddr = dto.getPrsnEmailAddr();
		this.telNo = dto.getTelNo();
		this.hpNo = dto.getHpNo();
		this.trBankNm = dto.getTrBankNm();
		this.trAcctNo = dto.getTrAcctNo();
		this.trDpstrNm = dto.getTrDpstrNm();
		this.payDayCd = dto.getPayDayCd();
		this.memoDesc = dto.getMemoDesc();
		this.prflAtchtFlNo = dto.getPrflAtchtFlNo();
	}

	public Integer getPrsnNo() {
		return prsnNo;
	}

	public void setPrsnNo(Integer prsnNo) {
		this.prsnNo = prsnNo;
	}

	public String getPrsnNm() {
		return prsnNm;
	}

	public void setPrsnNm(String prsnNm) {
		this.prsnNm = prsnNm;
	}

	public String getJuminNo1() {
		return juminNo1;
	}

	public void setJuminNo1(String juminNo1) {
		this.juminNo1 = juminNo1;
	}

	public String getJuminNo2() {
		return juminNo2;
	}

	public void setJuminNo2(String juminNo2) {
		this.juminNo2 = juminNo2;
	}

	public String getSexCd() {
		return sexCd;
	}

	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}

	public String getAcdmcCd() {
		return acdmcCd;
	}

	public void setAcdmcCd(String acdmcCd) {
		this.acdmcCd = acdmcCd;
	}

	public String getWorkStartDt() {
		return workStartDt;
	}

	public void setWorkStartDt(String workStartDt) {
		this.workStartDt = workStartDt;
	}

	public String getCertCd() {
		return certCd;
	}

	public void setCertCd(String certCd) {
		this.certCd = certCd;
	}

	public String getRsdncAddr() {
		return rsdncAddr;
	}

	public void setRsdncAddr(String rsdncAddr) {
		this.rsdncAddr = rsdncAddr;
	}

	public String getCntrctSectCd() {
		return cntrctSectCd;
	}

	public void setCntrctSectCd(String cntrctSectCd) {
		this.cntrctSectCd = cntrctSectCd;
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

	public String getLawBizNm() {
		return lawBizNm;
	}

	public void setLawBizNm(String lawBizNm) {
		this.lawBizNm = lawBizNm;
	}

	public String getCeoNm() {
		return ceoNm;
	}

	public void setCeoNm(String ceoNm) {
		this.ceoNm = ceoNm;
	}

	public String getPrsnEmailAddr() {
		return prsnEmailAddr;
	}

	public void setPrsnEmailAddr(String prsnEmailAddr) {
		this.prsnEmailAddr = prsnEmailAddr;
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

	public String getTrBankNm() {
		return trBankNm;
	}

	public void setTrBankNm(String trBankNm) {
		this.trBankNm = trBankNm;
	}

	public String getTrAcctNo() {
		return trAcctNo;
	}

	public void setTrAcctNo(String trAcctNo) {
		this.trAcctNo = trAcctNo;
	}

	public String getTrDpstrNm() {
		return trDpstrNm;
	}

	public void setTrDpstrNm(String trDpstrNm) {
		this.trDpstrNm = trDpstrNm;
	}

	public String getPayDayCd() {
		return payDayCd;
	}

	public void setPayDayCd(String payDayCd) {
		this.payDayCd = payDayCd;
	}

	public String getMemoDesc() {
		return memoDesc;
	}

	public void setMemoDesc(String memoDesc) {
		this.memoDesc = memoDesc;
	}

	public String getPrflAtchtFlNo() {
		return prflAtchtFlNo;
	}

	public void setPrflAtchtFlNo(String prflAtchtFlNo) {
		this.prflAtchtFlNo = prflAtchtFlNo;
	}

	public String getSkillSectCd() {
		return skillSectCd;
	}

	public void setSkillSectCd(String skillSectCd) {
		this.skillSectCd = skillSectCd;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


}
