package com.echo.framework.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.framework.util.CryptUtil;

public class Auth extends BaseDomain {
	private static final long serialVersionUID = 6357004070110948453L;

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(Auth.class);

	/* user */
	private int userId;
	private int businessId;
	private int serviceId;
	private String loginId;
	private String passwd;
	private String userName;
	private String phone;
	private String email;
	private String mobile;
	private String dashboardType;

	/* rights */
	private int rightsId;
	private String rightsName;
	private String rightsType;

	/* device */
	private int deviceId;
	private String serial;
	private String macAddr;
	private String addrExInfo;
	private String deviceState;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getPasswdByCrypt() {
		try {
			return CryptUtil.encryptHmacSha256(passwd);
		}
		catch (Exception e) {
			return passwd;
		}
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDashboardType() {
		return dashboardType;
	}

	public void setDashboardType(String dashboardType) {
		this.dashboardType = dashboardType;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getRightsId() {
		return rightsId;
	}

	public void setRightsId(int rightsId) {
		this.rightsId = rightsId;
	}

	public String getRightsType() {
		return rightsType;
	}

	public void setRightsType(String rightsType) {
		this.rightsType = rightsType;
	}

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getRightsName() {
		return rightsName;
	}

	public void setRightsName(String rightsName) {
		this.rightsName = rightsName;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getAddrExInfo() {
		return addrExInfo;
	}

	public void setAddrExInfo(String addrExInfo) {
		this.addrExInfo = addrExInfo;
	}

	public String getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}
}