package com.echo.framework.domain;

import com.echo.framework.dto.CodeDto;

public class Code extends BaseDomain {
	private static final long serialVersionUID = -4583649239751475494L;

	private Integer codeId;
	private String codeType;
	private String code;
	private String codeDesc;

	public Code() {

	}

	public Code(CodeDto dto) {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.codeId = dto.getCodeId();
		this.codeType = dto.getCodeType();
		this.code = dto.getCode();
		this.codeDesc = dto.getCodeDesc();
	}

	public int getCodeId() {
		return codeId;
	}

	public void setCodeId(int codeId) {
		this.codeId = codeId;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
}
