package com.echo.biz.dto;

import org.springframework.web.multipart.MultipartFile;

public class AttachFileDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;
	private String atchtFlNo;
	private String atchtFlPathNm;
	private String atchtFlNm;
	private String atchtFlOrgnlNm;

	private MultipartFile atchtFile;
	
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

	public MultipartFile getAtchtFile() {
		return atchtFile;
	}

	public void setAtchtFile(MultipartFile atchtFile) {
		this.atchtFile = atchtFile;
	}

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}

}
