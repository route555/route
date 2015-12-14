package com.echo.framework.dto;

import java.util.HashMap;
import java.util.Map;

import com.echo.framework.util.ValidUtil;
import com.echo.framework.util.ValidUtil.MANDATORY;

public class CodeDto extends BaseDto {
	private static final long serialVersionUID = 3886945721013498293L;

	private Integer codeId;
	private String codeType;
	private String code;
	private String codeDesc;

	public CodeDto() {
	};

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
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

	@Override
	public void validate(String method) throws Exception {
		Map<String, Object[]> valueMap = new HashMap<String, Object[]>();

		if ("POST".equals(method)) {
			valueMap.put("codeType", new Object[] { codeType, MANDATORY.TRUE });
			valueMap.put("code", new Object[] { code, MANDATORY.TRUE });
			valueMap.put("codeDesc", new Object[] { codeDesc, MANDATORY.TRUE });
		}
		else if ("PUT".equals(method)) {
			valueMap.put("codeType", new Object[] { codeType, MANDATORY.FALSE });
			valueMap.put("code", new Object[] { code, MANDATORY.FALSE });
			valueMap.put("codeDesc", new Object[] { codeDesc, MANDATORY.FALSE });
		}
		else if ("GET".equals(method)) {
			;
		}
		else if ("DELETE".equals(method)) {
			;
		}

		ValidUtil.isValid(validMeta, valueMap);
	}
}
