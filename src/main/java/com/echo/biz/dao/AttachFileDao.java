package com.echo.biz.dao;

import org.springframework.stereotype.Repository;

import com.echo.biz.domain.AttachFile;
import com.echo.framework.dao.AbstractDao;

@Repository("AttachFileDao")
public class AttachFileDao extends AbstractDao<AttachFile> {
	public AttachFileDao() {
		super(AttachFile.class);
	}

}
