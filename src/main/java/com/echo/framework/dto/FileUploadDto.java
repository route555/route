package com.echo.framework.dto;

/**
 * <pre>
 * 기능 : 업로드된 파일 정보 (entity)
 * </pre>
 */
public class FileUploadDto {

	/** 파일명 */
	private String fileName;

	/** 원본 파일명 */
	private String originalFileName;

	/** 확장자 */
	private String extension;

	/** 파일의 물리경로 */
	private String physicalFilePath;

	/** 파일 크기 */
	private Long fileSize;

	public FileUploadDto() {

	}

	/**
	 * @param fileName
	 * @param physicalFilePath
	 * @param fileSize
	 */
	public FileUploadDto(String fileName, String physicalFilePath, Long fileSize) {
		super();
		this.fileName = fileName;
		this.physicalFilePath = physicalFilePath;
		this.fileSize = fileSize;
	}

	/**
	 * @param fileName
	 * @param originalFileName
	 * @param extension
	 * @param physicalFilePath
	 * @param fileSize
	 */
	public FileUploadDto(String fileName, String originalFileName,
			String extension, String physicalFilePath, Long fileSize) {
		super();
		this.fileName = fileName;
		this.originalFileName = originalFileName;
		this.extension = extension;
		this.physicalFilePath = physicalFilePath;
		this.fileSize = fileSize;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {

		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	/**
	 * @return the physicalFilePath
	 */
	public String getPhysicalFilePath() {

		return physicalFilePath;
	}

	/**
	 * @param physicalFilePath
	 *            the physicalFilePath to set
	 */
	public void setPhysicalFilePath(String physicalFilePath) {

		this.physicalFilePath = physicalFilePath;
	}

	/**
	 * @return the fileSize
	 */
	public Long getFileSize() {

		return fileSize;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(Long fileSize) {

		this.fileSize = fileSize;
	}

	/**
	 * @return the originalFileName
	 */

	public String getOriginalFileName() {

		return originalFileName;
	}

	/**
	 * @param originalFileName
	 *            the originalFileName to set
	 */
	public void setOriginalFileName(String originalFileName) {

		this.originalFileName = originalFileName;
	}

	/**
	 * @return the extension
	 */

	public String getExtension() {

		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {

		this.extension = extension;
	}
}
