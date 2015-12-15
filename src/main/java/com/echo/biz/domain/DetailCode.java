package com.echo.biz.domain;

import com.echo.framework.domain.BaseDomain;

public class DetailCode extends BaseDomain {
	

	private static final long serialVersionUID = 1L;
	private String grpCd;
	private String dtlCd;
	private String dtlCdNm;
	private String dtlCdDesc;
	
	public DetailCode() {

	}

	public String getGrpCd() {
		return grpCd;
	}
	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}
	public String getDtlCd() {
		return dtlCd;
	}
	public void setDtlCd(String dtlCd) {
		this.dtlCd = dtlCd;
	}
	public String getDtlCdNm() {
		return dtlCdNm;
	}
	public void setDtlCdNm(String dtlCdNm) {
		this.dtlCdNm = dtlCdNm;
	}
	public String getDtlCdDesc() {
		return dtlCdDesc;
	}
	public void setDtlCdDesc(String dtlCdDesc) {
		this.dtlCdDesc = dtlCdDesc;
	}

	
	
	
	
}
