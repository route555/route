package com.echo.biz.domain;

import com.echo.biz.dto.GroupCodeDto;
import com.echo.framework.domain.BaseDomain;

public class GroupCode extends BaseDomain {

	private static final long serialVersionUID = 1L;

	private String grpCd;
	private String grpCdNm;
	private String grpCdDesc;

	public GroupCode() {

	}

	public GroupCode(GroupCodeDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.grpCd = dto.getGrpCd();
		this.grpCdNm = dto.getGrpCdNm();
		this.grpCdDesc = dto.getGrpCdDesc();

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
