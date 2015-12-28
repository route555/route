package com.echo.biz.domain;

import com.echo.biz.dto.AttachFileDto;
import com.echo.framework.domain.BaseDomain;

public class AttachFile extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private String atchtFlNo;
	private String atchtFlPathNm;
	private String atchtFlNm;
	private String atchtFlOrgnlNm;

	public AttachFile() {

	}

	public AttachFile(AttachFileDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.atchtFlNo = dto.getAtchtFlNo();
		this.atchtFlPathNm = dto.getAtchtFlPathNm();
		this.atchtFlNm = dto.getAtchtFlNm();
		this.atchtFlOrgnlNm = dto.getAtchtFlOrgnlNm();
	}

	public String getAtchtFlNo() {
		return atchtFlNo;
	}

	public void setAtchtFlNo(String atchtFlNo) {
		this.atchtFlNo = atchtFlNo;
	}

	public String getAtchtFlPathNm() {
		return atchtFlPathNm;
	}

	public void setAtchtFlPathNm(String atchtFlPathNm) {
		this.atchtFlPathNm = atchtFlPathNm;
	}

	public String getAtchtFlNm() {
		return atchtFlNm;
	}

	public void setAtchtFlNm(String atchtFlNm) {
		this.atchtFlNm = atchtFlNm;
	}

	public String getAtchtFlOrgnlNm() {
		return atchtFlOrgnlNm;
	}

	public void setAtchtFlOrgnlNm(String atchtFlOrgnlNm) {
		this.atchtFlOrgnlNm = atchtFlOrgnlNm;
	}

}
