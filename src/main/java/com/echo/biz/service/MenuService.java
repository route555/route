package com.echo.biz.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.biz.dao.MenuDao;
import com.echo.biz.domain.Menu;
import com.echo.biz.dto.MenuDto;
import com.echo.biz.dto.SampleHumanDto;
import com.echo.framework.service.AbstractService;

@Service("MenuService")
public class MenuService extends AbstractService<Menu, MenuDto> {
	private static Logger log = LoggerFactory.getLogger(MenuService.class);

	@Autowired
	private MenuDao menuDao;

	public MenuService() {
		super(Menu.class, MenuDto.class);
	}

	@PostConstruct
	public void setDao() {
		setDao(menuDao);
	}

	protected void validation(String method, SampleHumanDto dto) throws Exception {
		if (METHOD_POST.equals(method) == true) {

		}
	}
}
