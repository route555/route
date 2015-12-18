package com.echo.biz.dto;

public class GroupCodeDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;

	private String grpCd;
	private String grpCdNm;
	private String grpCdDesc;

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

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}
}
