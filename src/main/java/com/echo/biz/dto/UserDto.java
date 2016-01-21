package com.echo.biz.dto;

public class UserDto extends BasePmsDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3341017409195034972L;
	//private static final long serialVersionUID = 1L;

	private Integer userId;
	private Integer tenantId;
	private String loginId;
	private String loginPw;
	private String userName;
	private String phone;
	private String email;
	private String rightsType;
	private String allowMenu;
	private String isUse;

	
	
	public String getAllowMenu() {
		return allowMenu;
	}

	public void setAllowMenu(String allowMenu) {
		this.allowMenu = allowMenu;
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

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}
}
