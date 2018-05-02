package com.echo.biz.controller.rest;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.echo.biz.service.UserService;
import com.echo.framework.controller.BaseController;

@Controller
@RequestMapping(value = "/excelUploadAjax")
public class ExcelUploadController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(ExcelUploadController.class);

	@RequestMapping(method = RequestMethod.POST)
    public String excelUploadAjax(MultipartHttpServletRequest request) throws Exception {
        MultipartFile excelFile =request.getFile("excelFile");
        log.debug("Excel File Upload");
        
        if(excelFile==null || excelFile.isEmpty()) {
            throw new RuntimeException("Please choose a excel file.");
        }
        
        File destFile = new File("D:\\"+excelFile.getOriginalFilename());
        try {
            excelFile.transferTo(destFile);
        } catch(IllegalStateException | IOException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        
        UserService.excelUpload(destFile);
        
        destFile.delete();
        
        return "/user";
    }
}
