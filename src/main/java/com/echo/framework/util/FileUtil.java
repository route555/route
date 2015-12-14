package com.echo.framework.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.echo.framework.domain.FileUploadMessage;
import com.echo.framework.dto.FileUploadDto;
import com.echo.framework.exception.EchoException;

public class FileUtil {
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	private static final String MaxFileSize = "maxFileSize";
	private static final String CreateZeroSizeFileYn = "createZeroSizeFileYn";
	private static final String AllowFileExtension = "allowFileExtension";
	private static final String DenyFileExtension = "denyFileExtension";
	private static final String UploadPath = "uploadPath";
	private static final String UploadSubDatePath = "uploadSubDatePath";

	public static boolean deleteFile(String physicalFilePath)
			throws RuntimeException {
		File file = new File(physicalFilePath);
		boolean isSuccess = false;
		if (file.exists()) {
			isSuccess = file.delete();
		}
		return isSuccess;
	}

	private static String getFileManagePolicy(String propertyCode,
			String policyCode) {
		String policy = PropsUtil.getValue(propertyCode + "." + policyCode);

		if (policy == null) {
			log.warn("{}.{} does not exist", new Object[] { propertyCode,
					policyCode });

			propertyCode = "default";
			policy = PropsUtil.getValue(propertyCode + "." + policyCode);
		}

		if (policy == null) {
			log.error("{}.{} does not exist", new Object[] { propertyCode,
					policyCode });
		}
		else {
			log.debug("{}.{}={}", new Object[] { propertyCode, policyCode,
					policy });
		}

		return policy;
	}

	public static FileUploadMessage validate(MultipartFile file,
			String propertyCode) {
		if (file == null || file.isEmpty() == true) {
			log.error("file is null");
			return new FileUploadMessage(FileUploadMessage.CODE_FILE_NOT_FOUND);
		}

		long maxFileSize = Long.parseLong(getFileManagePolicy(propertyCode,
				MaxFileSize));
		String createZeroSizeFileYn = getFileManagePolicy(propertyCode,
				CreateZeroSizeFileYn);
		String allowFileExtension = getFileManagePolicy(propertyCode,
				AllowFileExtension);
		String denyFileExtension = getFileManagePolicy(propertyCode,
				DenyFileExtension);

		long fileSize = file.getSize();
		String fileName = file.getOriginalFilename();
		String fileExtension = fileName
				.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

		if ((fileSize > maxFileSize) && (maxFileSize != -1L)) {
			log.error("{}.{}={}, file.size={}", new Object[] { propertyCode,
					MaxFileSize, maxFileSize, fileSize });

			return new FileUploadMessage(new String[] {
					FileUploadMessage.CODE_EXCEED_MAX_FILE_SIZE,
					String.valueOf(fileName), String.valueOf(maxFileSize),
					String.valueOf(fileSize) });
		}
		else if (fileSize == 0 && !createZeroSizeFileYn.equalsIgnoreCase("Y")) {
			log.error("{}.{}={}, file.size={}", new Object[] { propertyCode,
					CreateZeroSizeFileYn, createZeroSizeFileYn, fileSize });

			return new FileUploadMessage(new String[] {
					FileUploadMessage.CODE_CANNOT_UPLOAD_ZERO_SIZE, fileName });
		}
		else if ((StringUtils.isEmpty(allowFileExtension) == false)
				&& (fileExtension.matches(allowFileExtension) == false)) {
			log.error("{}.{}={}, file.name={}", new Object[] { propertyCode,
					AllowFileExtension, allowFileExtension, fileName });

			return new FileUploadMessage(new String[] {
					FileUploadMessage.CODE_NOT_ALLOWED_EXTENSION,
					allowFileExtension });
		}
		else if ((StringUtils.isEmpty(denyFileExtension) == false)
				&& (fileExtension.matches(denyFileExtension) == true)) {
			log.error("{}.{}={}, file.name={}", new Object[] { propertyCode,
					DenyFileExtension, denyFileExtension, fileName });

			return new FileUploadMessage(new String[] {
					FileUploadMessage.CODE_NOT_ALLOWED_EXTENSION, fileName });
		}

		return new FileUploadMessage(FileUploadMessage.CODE_OK);
	}

	public static String getUploadPath(String propertyCode) {
		StringBuffer uploadPath = new StringBuffer();
		String tmp = null;

		tmp = getFileManagePolicy(propertyCode, UploadPath);
		if (StringUtils.isEmpty(tmp) == false) {
			uploadPath.append(tmp);
		}

		tmp = getFileManagePolicy(propertyCode, UploadSubDatePath);
		if (StringUtils.isEmpty(tmp) == false) {
			try {
				String date = new SimpleDateFormat(tmp,
						DateUtils.DEFAULT_LOCALE).format(new Date());

				if ((uploadPath.length() > 0)
						&& (uploadPath.charAt(uploadPath.length() - 1) != '/')) {
					uploadPath.append("/");
				}

				uploadPath.append(date);
			}
			catch (Exception e) {
				log.error("invalid property, {}.{}={}", new Object[] {
						propertyCode, UploadSubDatePath, tmp, e });
			}
		}

		return uploadPath.toString();
	}

	public static FileUploadMessage uploadFile(MultipartFile file,
			String propertyCode) throws Exception {
		FileUploadMessage fileValidationMsg = validate(file, propertyCode);
		if (!fileValidationMsg.isSuccess()) {
			return fileValidationMsg;
		}

		String uploadPath = getUploadPath(propertyCode);

		File saveFolder = new File(uploadPath);
		if (!saveFolder.exists()) {
			saveFolder.mkdirs();
		}

		String originalFileName = file.getOriginalFilename();
		String extension = getExtension(originalFileName);
		long fileSize = file.getSize();
		String physicalFilePath = null;
		String uuid = UUID.randomUUID().toString();

		if (StringUtils.isEmpty(originalFileName) != true) {
			physicalFilePath = uploadPath + "/" + uuid + "." + extension;

			try {
				file.transferTo(new File(physicalFilePath));
			}
			catch (IOException e) {
				log.error("copy {} to {}", new Object[] { originalFileName,
						physicalFilePath, e });
				return new FileUploadMessage(new String[] {
						FileUploadMessage.CODE_UPLOAD, originalFileName });
			}
		}
		else {
			log.warn("{} is empty", originalFileName);

			return new FileUploadMessage(new String[] {
					FileUploadMessage.CODE_UPLOAD, originalFileName });
		}

		log.info("copy {} to {}", originalFileName, physicalFilePath);

		List<FileUploadDto> fileList = new ArrayList<FileUploadDto>();
		fileList.add(new FileUploadDto(originalFileName, originalFileName,
				extension, physicalFilePath, fileSize));
		return new FileUploadMessage(FileUploadMessage.CODE_OK, fileList);
	}

	public static FileUploadMessage uploadFile(
			MultipartHttpServletRequest multipartRequest, String propertyCode)
			throws Exception {
		final Map<String, MultipartFile> files = multipartRequest.getFileMap();
		List<FileUploadDto> fileInfoList = null;

		if (files != null && files.size() > 0) {
			fileInfoList = new ArrayList<FileUploadDto>();

			for (MultipartFile file : files.values()) {
				FileUploadMessage fileUploadMessage = uploadFile(file,
						propertyCode);
				if ((fileUploadMessage.isSuccess() == true)
						&& (fileUploadMessage.getFileUploadList() != null)) {
					FileUploadDto fileUploadDto = fileUploadMessage
							.getFileUploadList().get(0);

					fileInfoList.add(fileUploadDto);
				}
			}
		}

		return new FileUploadMessage(FileUploadMessage.CODE_OK, fileInfoList);
	}

	public static String upload4BinFile(MultipartFile multipartFile,
			String propertyCode, String pathPrefix, int retryCnt)
			throws Exception {
		FileUploadMessage fileUploadMessage = FileUtil.uploadFile(
				multipartFile, propertyCode);

		if (fileUploadMessage.isSuccess() == false) {
			throw new EchoException(HttpServletResponse.SC_BAD_REQUEST,
					fileUploadMessage.getParams());
		}

		FileUploadDto fileUploadDto = fileUploadMessage.getFileUploadList()
				.get(0);
		String binFilePath = EchoUtil.getFSPath4Bin(pathPrefix, new Date(),
				fileUploadDto.getOriginalFileName());

		if ("Y".equals(PropsUtil.getValue("file.upload.real").toUpperCase())) {
			if (FtpUtil.uploadFile(fileUploadDto.getPhysicalFilePath(),
					binFilePath, retryCnt) == true) {
				log.info("contents file upload success, src={}, es={}",
						fileUploadDto.getPhysicalFilePath(), binFilePath);
			}
		}

		return FtpUtil.getFtpUrl(binFilePath);
	}

	public static String getExtension(String fileName) {
		if (fileName == null)
			fileName = "";

		int index = fileName.lastIndexOf(".");
		String ext = index == -1 ? "" : fileName.substring(index + 1);

		return ext.toLowerCase();
	}
}
