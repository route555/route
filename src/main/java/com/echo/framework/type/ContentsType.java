package com.echo.framework.type;

import java.util.HashMap;
import java.util.Map;

import com.echo.framework.util.FileUtil;
import com.echo.framework.util.StringUtils;

public enum ContentsType {
	IMG("CONT0001"),

	MOV("CONT0002"),

	TXT("CONT0003"),

	ZIP("CONT0004"),

	SND("CONT0005"),

	PPT("CONT0006"),

	SWF("CONT0007"),

	;

	private String code;

	private ContentsType(String code) {
		this.code = code;
	}

	public String code() {
		return code;
	}

	public boolean isEquals(String other) {
		return code.equals(other);
	}

	private static Map<String, ContentsType> extMap = null;

	public static ContentsType getContentsType(String file) {
		ContentsType contentsType = TXT;

		if (StringUtils.isEmpty(file) == false) {
			String ext = FileUtil.getExtension(file);

			if (extMap == null) {
				extMap = new HashMap<String, ContentsType>();
				extMap.put("mp4|avi|wmv", MOV);
				extMap.put("jpg|jpeg|png|bmp|pdf", IMG);
				extMap.put("zip", ZIP);
				extMap.put("mp3", SND);
				extMap.put("ppt|pptx", PPT);
			}

			for (String extRegex : extMap.keySet()) {
				if (ext.matches(extRegex) == true) {
					contentsType = extMap.get(extRegex);
					break;
				}
			}
		}

		return contentsType;
	}
}