package com.echo.biz.domain;

import com.echo.framework.domain.BaseDomain;

public class GroupCode extends BaseDomain {

	private static final long serialVersionUID = 1L;

	private String grpCd;
	private String grpCdNm;
	private String grpCdDesc;

	public GroupCode() {

	}

	public String getGrpCd() {
		return grpCd;
	}

	public void setGrpCd(String grpCd) {
		this.grpCd = grpCd;
	}

	public String getGrpCdNm() {
		return grpCdNm;
	}

	public void setGrpCdNm(String grpCdNm) {
		this.grpCdNm = grpCdNm;
	}

	public String getGrpCdDesc() {
		return grpCdDesc;
	}

	public void setGrpCdDesc(String grpCdDesc) {
		this.grpCdDesc = grpCdDesc;
	}

}
