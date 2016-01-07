package com.echo.biz.controller.rest;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.echo.biz.domain.AttachFile;
import com.echo.biz.dto.AttachFileDto;
import com.echo.biz.service.AttachFileService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/download")
public class DownloadController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(DownloadController.class);

	@Autowired
	protected AttachFileService attachFileService;
	
	
	@RequestMapping(value = "/{atchtFlNo}", method = RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response, @PathVariable("atchtFlNo") String atchtFlNo ) throws Exception {

		
		AttachFileDto dto = new AttachFileDto();
		dto.setAtchtFlNo(atchtFlNo);
		
		
		
		AttachFile attachFile=attachFileService.selectDto(dto);
		
		String fullPath = attachFile.getAtchtFlPathNm();
		
		File file = new File(fullPath);
		
		ModelAndView mv = new ModelAndView("download", "downloadFile", file);
		
		mv.addObject("orgName", attachFile.getAtchtFlOrgnlNm());

		return mv;
	}

	

}
