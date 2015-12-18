package com.echo.biz.dto;

public class DetailCodeDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;
	
	private String grpCd;
	private String dtlCd;
	private String dtlCdNm;
	private String dtlCdDesc;

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

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}
}
