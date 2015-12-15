package com.echo.biz.domain;

import java.util.Date;

import com.echo.biz.dto.SampleHumanDto;
import com.echo.framework.domain.BaseDomain;

public class SampleHuman extends BaseDomain {
	

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
	
	public SampleHuman() {
	}
	
	
	public SampleHuman(SampleHumanDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.humanId = dto.getHumanId();
		this.division = dto.getDivision();
		this.name = dto.getName();
		this.job = dto.getJob();
		this.phone = dto.getPhone();
		this.email = dto.getEmail();
		this.bank = dto.getBank();
		this.salary = dto.getSalary();
		
	}
	
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

}
