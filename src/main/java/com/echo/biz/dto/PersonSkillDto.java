package com.echo.biz.dto;

public class PersonSkillDto extends BasePmsDto {

	private static final long serialVersionUID = 1L;
	private Integer prsnNo;
	private Integer skillSeqNo;
	private String skillSectCd;

	public Integer getPrsnNo() {
		return prsnNo;
	}

	public void setPrsnNo(Integer prsnNo) {
		this.prsnNo = prsnNo;
	}

	public Integer getSkillSeqNo() {
		return skillSeqNo;
	}

	public void setSkillSeqNo(Integer skillSeqNo) {
		this.skillSeqNo = skillSeqNo;
	}

	public String getSkillSectCd() {
		return skillSectCd;
	}

	public void setSkillSectCd(String skillSectCd) {
		this.skillSectCd = skillSectCd;
	}

	@Override
	public void validate(String method) throws Exception {
		// TODO Auto-generated method stub

	}

}
