package com.echo.biz.domain;

import com.echo.biz.dto.MenuDto;
import com.echo.framework.domain.BaseDomain;

public class Menu extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private Integer menuId;
	private String menuName;
	private Integer menuOrder;
	private String menuDesc;
	private String useYn;

	public Menu() {

	}

	public Menu(MenuDto dto) throws Exception {
		/*
		 * for regUserId, updUserId
		 */
		super(dto);

		this.menuId = dto.getMenuId();
		this.menuName = dto.getMenuName();
		this.menuOrder = dto.getMenuOrder();
		this.menuDesc = dto.getMenuDesc();
		this.useYn = dto.getUseYn();

	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

}
