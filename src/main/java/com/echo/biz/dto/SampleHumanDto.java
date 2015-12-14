package com.echo.biz.dto;

import java.util.Date;

public class SampleHumanDto extends BasePmsDto {

	

	private static final long serialVersionUID = 1L;
	
	private Integer humanId;
	private String division;
	private String name;
	private String job;
	private String phone;
	private String email;
	private String bank;
	private String salary;
	private Date createTime;
	
	
	public Integer getHumanId() {
		return humanId;
	}
	public void setHumanId(Integer humanId) {
		this.humanId = humanId;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
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
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
