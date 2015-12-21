package com.echo.biz.domain;

import com.echo.biz.dto.UserDto;
import com.echo.framework.domain.BaseDomain;
import com.echo.framework.util.CryptUtil;

public class User extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4293738353401496107L;
	//private static final long serialVersionUID = 1L;

	private Integer userId;
	private Integer tenantId;
	private String loginId;
	private String loginPw;
	private String userName;
	private String phone;
	private String email;
	private String rightsType;
	private String isUse;
	
	public User() {

	}

	public User(UserDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.userId = dto.getUserId();
		this.tenantId = dto.getTenantId();
		this.loginId = dto.getLoginId();		
		this.loginPw = dto.getLoginPw();
		this.userName = dto.getUserName();
		this.phone = dto.getPhone();
		this.email = dto.getEmail();
		this.rightsType = dto.getRightsType();
		this.isUse = dto.getIsUse();

	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	
	

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getPasswdByCrypt() {
		try {
			return CryptUtil.encryptHmacSha256(loginPw);
		}
		catch (Exception e) {
			return loginPw;
		}
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRightsType() {
		return rightsType;
	}

	public void setRightsType(String rightsType) {
		this.rightsType = rightsType;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

}
