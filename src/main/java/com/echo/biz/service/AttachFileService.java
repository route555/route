package com.echo.biz.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.AttachFileDao;
import com.echo.biz.domain.AttachFile;
import com.echo.biz.dto.AttachFileDto;
import com.echo.biz.dto.PersonDto;
import com.echo.framework.domain.FileUploadMessage;
import com.echo.framework.dto.FileUploadDto;
import com.echo.framework.exception.EchoException;
import com.echo.framework.service.AbstractService;
import com.echo.framework.util.FileUtil;

@Service("AttachFileService")
public class AttachFileService extends AbstractService<AttachFile, AttachFileDto> {
	private static Logger log = LoggerFactory.getLogger(AttachFileService.class);

	@Autowired
	private AttachFileDao attachFileDao;

	public AttachFileService() {
		super(AttachFile.class, AttachFileDto.class);
	}

	@Override
	public AttachFileDto insert(AttachFileDto dto, String getField, String setField) throws Exception {
		fileAttach(dto);

		super.insert(dto, getField, setField);

		return dto;
	}

	public void fileAttach(AttachFileDto attachFileDto) throws Exception {

		if ((attachFileDto.getAtchtFile() != null) && (attachFileDto.getAtchtFile().isEmpty() == false)) {

			FileUploadMessage result = FileUtil.uploadFile(attachFileDto.getAtchtFile(), "default");

			if (result.isSuccess() != true) {
				throw new EchoException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, result.getParams());
			}
			
			FileUploadDto file = result.getFileUploadList().get(0);
			
			attachFileDto.setAtchtFlNm(file.getFileName());
			attachFileDto.setAtchtFlOrgnlNm(file.getOriginalFileName());
			attachFileDto.setAtchtFlPathNm(file.getPhysicalFilePath());
			


		}
	}

	@PostConstruct
	public void setDao() {
		setDao(attachFileDao);
	}

	protected void validation(String method, PersonDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
