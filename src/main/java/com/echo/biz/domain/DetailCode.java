package com.echo.biz.domain;

import com.echo.biz.dto.DetailCodeDto;
import com.echo.framework.domain.BaseDomain;

public class DetailCode extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private String grpCd;
	private String dtlCd;
	private String dtlCdNm;
	private String dtlCdDesc;

	public DetailCode() {

	}

	public DetailCode(DetailCodeDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.grpCd = dto.getGrpCd();
		this.dtlCd = dto.getDtlCd();
		this.dtlCdNm = dto.getDtlCdNm();
		this.dtlCdDesc = dto.getDtlCdDesc();
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
