package com.echo.framework.util;

import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThumbnailUtil {
	private static Logger log = LoggerFactory.getLogger(ThumbnailUtil.class);

	public static final int DEFAULT_WIDTH = 128;
	public static final int DEFAULT_HEIGHT = 128;

	public static String generateThumbnail(String srcFile, int width,
			int height, String dstFile) throws Exception {
		Thumbnails.of(srcFile).size(width, height).toFile(dstFile);
		return dstFile;
	}

	public static String generateThumbnail(String srcFile) throws Exception {
		String thumbBasename = UUID.randomUUID().toString();
		String thumbOutputPath = FileUtil.getUploadPath("default");
		String thumbExt = PropsUtil.getValue("script.makeThumb.ext");
		String thumbFile = thumbOutputPath + "/" + thumbBasename + thumbExt;

		Integer width = DEFAULT_WIDTH;
		Integer height = DEFAULT_HEIGHT;

		try {
			String resolution = PropsUtil.getValue("thumb.resolution");
			String[] wh = resolution.split("x");
			width = Integer.parseInt(wh[0]);
			height = Integer.parseInt(wh[1]);
		}
		catch (Exception e) {
			log.error("{}", e.getMessage(), e);
		}

		return generateThumbnail(srcFile, width, height, thumbFile);
	}
}
