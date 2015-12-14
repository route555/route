package com.echo.framework.domain;

import java.util.List;

import com.echo.framework.dto.FileUploadDto;

/**
 * <pre>
 * 기능 : 파일 업로드 처리 결과 메시지
 * </pre>
 */
public class FileUploadMessage {
	public static final String CODE_OK = "error.file.ok";
	public static final String CODE_EXCEED_MAX_FILE_SIZE = "error.file.maxFileSizeExceeded";
	public static final String CODE_FILE_NOT_FOUND = "error.file.not.found";
	public static final String CODE_CANNOT_UPLOAD_ZERO_SIZE = "error.file.cannotUploadZeroSizeFile";
	public static final String CODE_NOT_ALLOWED_EXTENSION = "error.file.notAllowedFileExtension";
	public static final String CODE_UPLOAD = "error.file.upload";

	/** 메시지 코드 */
	private String messageCode;

	/** 메시지 파라미터 */
	private Object[] params;

	/** 파일업로드 결과 엔터티 목록 */
	List<FileUploadDto> fileUploadList;

	public FileUploadMessage(String messageCode) {
		this.messageCode = messageCode;
		this.params = new Object[] { messageCode };
	}

	public FileUploadMessage(Object[] params) {
		this.messageCode = (String) params[0];
		this.params = params.clone();
	}

	public FileUploadMessage(String messageCode,
			List<FileUploadDto> fileUploadList) {
		this.messageCode = messageCode;
		this.params = new Object[] { messageCode };
		this.fileUploadList = fileUploadList;
	}

	/**
	 * @param messageCode
	 * @param params
	 * @param fileUploadList
	 */
	public FileUploadMessage(Object[] params, List<FileUploadDto> fileUploadList) {
		this.messageCode = (String) params[0];
		this.params = params.clone();
		this.fileUploadList = fileUploadList;
	}

	/**
	 * @return the messageCode
	 */
	public String getMessageCode() {

		return messageCode;
	}

	/**
	 * @param messageCode
	 *            the messageCode to set
	 */
	public void setMessageCode(String messageCode) {

		this.messageCode = messageCode;
	}

	/**
	 * @return the params
	 */
	public Object[] getParams() {

		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Object[] params) {

		this.params = params.clone();
	}

	/**
	 * @return the fileUploadList
	 */
	public List<FileUploadDto> getFileUploadList() {

		return fileUploadList;
	}

	/**
	 * @param fileUploadList
	 *            the fileUploadList to set
	 */
	public void setFileUploadList(List<FileUploadDto> fileUploadList) {

		this.fileUploadList = fileUploadList;
	}

	public boolean isSuccess() {
		return CODE_OK.equals(messageCode);
	}

	/**
	 * jsp view에 메시지 파라미터를 전달하기 위한 메소드 파라미터들을 |로 연결시켜 준다.
	 * 
	 * @return 메시지 파라미터 목록을 '|'로 묶은 문자열
	 */
	public String getArguments() {
		if (params == null) {
			return null;
		}

		StringBuffer paramsWithDelimiter = new StringBuffer();
		for (Object param : params) {
			paramsWithDelimiter.append(param).append("|");
		}

		String arguments = paramsWithDelimiter.substring(0, paramsWithDelimiter
				.length() - 1);

		return arguments;
	}
}
