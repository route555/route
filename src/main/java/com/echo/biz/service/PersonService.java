package com.echo.biz.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.echo.biz.dao.PersonDao;
import com.echo.biz.domain.Person;
import com.echo.biz.dto.AttachFileDto;
import com.echo.biz.dto.PersonDto;
import com.echo.biz.dto.PersonSkillDto;
import com.echo.framework.domain.EchoCookie;
import com.echo.framework.dto.BaseDto;
import com.echo.framework.service.AbstractService;
import com.echo.framework.type.ContentsType;
import com.echo.framework.util.StringUtils;

@Service("PersonService")
public class PersonService extends AbstractService<Person, PersonDto> {
	private static Logger log = LoggerFactory.getLogger(PersonService.class);

	@Autowired
	private PersonDao personDao;

	@Autowired
	private PersonSkillService personSkillService;

	@Autowired
	private AttachFileService attachFileService;

	public PersonService() {
		super(Person.class, PersonDto.class);
	}

	@Transactional
	@Override
	public int updateDto(PersonDto dto) throws Exception {

		int cnt = super.updateDto(dto);

		log.debug("@@@ person updateDto" + dto);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prsnNo", dto.getPrsnNo());
		personSkillService.deletePersonSkillByPrsnNo(param);

		if (dto.getSkillSectCd() != null) {
			for (String skillSectCd : dto.getSkillSectCd()) {
				PersonSkillDto personSkillDto = new PersonSkillDto();
				personSkillDto.setPrsnNo(dto.getPrsnNo());
				personSkillDto.setSkillSectCd(skillSectCd);

				personSkillService.insertDto(personSkillDto);
			}
		}

		return cnt;
	}

	@Transactional
	@Override
	public PersonDto insert(PersonDto dto, String getField, String setField) throws Exception {
		
		if (dto.getPrflAtchtFile() != null) {
			EchoCookie echoCookie = dto.getEchoCookie();
			AttachFileDto attachFileDto = new AttachFileDto();
			attachFileDto.setEchoCookie(echoCookie);

			MultipartFile f = dto.getPrflAtchtFile();
			if (StringUtils.isEmpty(f.getOriginalFilename()) == false) {
				attachFileDto.setAtchtFile(f);
				attachFileService.insert(attachFileDto, "getAtchtFlNo", "setAtchtFlNo");
			}
			
			dto.setPrflAtchtFlNo(attachFileDto.getAtchtFlNo());
		}
		
		dto = super.insert(dto, getField, setField);

		log.debug("@@@ person personDto" + dto);

		for (String skillSectCd : dto.getSkillSectCd()) {
			PersonSkillDto personSkillDto = new PersonSkillDto();
			personSkillDto.setPrsnNo(dto.getPrsnNo());
			personSkillDto.setSkillSectCd(skillSectCd);
			personSkillService.insertDto(personSkillDto);
		}

		

		

		return dto;
	}

	@Transactional
	@Override
	public int deleteDto(PersonDto dto) throws Exception {

		int s = super.deleteDto(dto);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prsnNo", dto.getPrsnNo());
		personSkillService.deletePersonSkillByPrsnNo(param);

		return s;
	}

	@PostConstruct
	public void setDao() {
		setDao(personDao);
	}

	protected void validation(String method, PersonDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
