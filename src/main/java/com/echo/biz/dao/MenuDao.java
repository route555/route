package com.echo.biz.dao;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.Menu;
import com.echo.framework.dao.AbstractDao;

@Repository("MenuDao")
public class MenuDao extends AbstractDao<Menu> {
	public MenuDao() {
		super(Menu.class);
	}
}
