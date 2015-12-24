package com.echo.biz.domain;

import com.echo.biz.dto.PersonSkillDto;
import com.echo.framework.domain.BaseDomain;

public class PersonSkill extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private int prsnNo;
	private int skillSeqNo;
	private String skillSectCd;

	public PersonSkill() {

	}

	public PersonSkill(PersonSkillDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.prsnNo = dto.getPrsnNo();
		
		if(dto.getSkillSeqNo() != null){
			this.skillSeqNo = dto.getSkillSeqNo();
		}
		
		
		this.skillSectCd = dto.getSkillSectCd();
	}

	public int getPrsnNo() {
		return prsnNo;
	}

	public void setPrsnNo(int prsnNo) {
		this.prsnNo = prsnNo;
	}

	public int getSkillSeqNo() {
		return skillSeqNo;
	}

	public void setSkillSeqNo(int skillSeqNo) {
		this.skillSeqNo = skillSeqNo;
	}

	public String getSkillSectCd() {
		return skillSectCd;
	}

	public void setSkillSectCd(String skillSectCd) {
		this.skillSectCd = skillSectCd;
	}

}
