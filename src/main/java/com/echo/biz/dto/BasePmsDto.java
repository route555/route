package com.echo.biz.dto;

import com.echo.framework.dto.BaseDto;
import com.echo.framework.type.CommonConst;
import com.echo.framework.util.ValidUtil.LENGTH;

@SuppressWarnings("serial")
public abstract class BasePmsDto extends BaseDto {
	/*
	 * common
	 */
	public static final String REGEX_PHONE = "[0][1-7][0-9]-?[0-9]{3,4}-?[0-9]{4}";
	public static final String REGEX_NUMBER = "[0-9]+";

	public static final String REGEX_PASSWD_NUMBER = ".*[0-9]+.*";
	public static final String REGEX_PASSWD_ALPHA = ".*[a-zA-Z]+.*";

	public static final int MIN_URL = 1;
	public static final int MAX_URL = 255;

	public static final int MIN_DESC = 0;
	public static final int MAX_DESC = 200;

	public static final int MIN_TAG = 0;
	public static final int MAX_TAG = 1000;

	/*
	 * business
	 */
	public static final int MIN_BUSINESSID = 0;
	public static final int MAX_BUSINESSID = Integer.MAX_VALUE;

	public static final int MIN_BUSINESSNAME = 1;
	public static final int MAX_BUSINESSNAME = 25;

	public static final int MIN_SOUNDVOLUME = 0;
	public static final int MAX_SOUNDVOLUME = 10;

	public static final int MIN_BRIGHTNESS = 0;
	public static final int MAX_BRIGHTNESS = 10;

	public static final int MIN_MININTERVAL = 0;
	public static final int MAX_MININTERVAL = 300;

	public static final int MAX_HOLIDAY_SIZE = 365;

	public static final String REGEX_HOLIDAY = "[0-9,]*";

	/*
	 * user
	 */
	public static final int MIN_USERID = 0;
	public static final int MAX_USERID = Integer.MAX_VALUE;

	public static final int MIN_LOGINID = 4;
	public static final int MAX_LOGINID = 12;

	public static final int MIN_PASSWD = 8;
	public static final int MAX_PASSWD = 12;

	public static final int MIN_USERNAME = 2;
	public static final int MAX_USERNAME = 10;

	public static final int MIN_EMAIL = 5;
	public static final int MAX_EMAIL = 37;

	public static final int MIN_RIGHTSTYPE = 8;
	public static final int MAX_RIGHTSTYPE = 8;

	public static final String REGEX_LOGINID = "[0-9a-zA-Z]+";
	public static final String REGEX_PASSWD = "[0-9a-zA-Z~!@#$%^&*()_+`\\-={}|\\[\\]:\\\\\";'<>?,./]+";
	public static final String REGEX_EMAIL = "[0-9a-zA-Z.]{5,}@[0-9a-zA-Z]+[.][0-9a-zA-Z.]+";
	public static final String REGEX_USERNAME = CommonConst.REGEX_SPECIAL_CHARS;
	public static final String REGEX_RIGHTSTYPE = "[RT0-9,]+";

	/*
	 * contents
	 */
	public static final int MIN_CONTENTSID = 0;
	public static final int MAX_CONTENTSID = Integer.MAX_VALUE;

	public static final int MIN_CONTENTSNAME = 0;
	public static final int MAX_CONTENTSNAME = 100;

	public static final int MIN_CONTENTSFILE = 1;
	public static final int MAX_CONTENTSFILE = 255;

	public static final int MIN_RESOLUTION = 7;
	public static final int MAX_RESOLUTION = 10;

	public static final int MIN_CONTENTSTXT = 0;
	public static final int MAX_CONTENTSTXT = 350;

	public static final int MIN_CONTENTSTXTMETA = 0;
	public static final int MAX_CONTENTSTXTMETA = 255;

	public static final int MIN_CHECKSUM = 1;
	public static final int MAX_CHECKSUM = 48;

	public static final String REGEX_CONTENTSTYPE = "CONT000[1-7]";
	public static final String REGEX_ORIENTATION = "[0-9]{1,8}";

	/*
	 * container
	 */
	public static final int MIN_CONTAINERID = 0;
	public static final int MAX_CONTAINERID = Integer.MAX_VALUE;

	public static final int MIN_CONTAINERNAME = 1;
	public static final int MAX_CONTAINERNAME = 100;

	public static final int MIN_CONTAINERDESC = 0;
	public static final int MAX_CONTAINERDESC = 65535;

	public static final int MIN_CONTAINEREXTRA = 0;
	public static final int MAX_CONTAINEREXTRA = 100;

	public static final int MIN_SCENARIO = 1;
	public static final int MAX_SCENARIO = 65535;

	/*
	 * device
	 */
	public static final int MIN_DEVICEID = 0;
	public static final int MAX_DEVICEID = Integer.MAX_VALUE;

	public static final int MIN_DEVICENAME = 1;
	public static final int MAX_DEVICENAME = 25;

	public static final int MIN_DEVICEMETA = 0;
	public static final int MAX_DEVICEMETA = 255;

	public static final int MIN_DEVICECNT = 1;
	public static final int MAX_DEVICECNT = 99999;

	public static final String REGEX_DEVICENAME = ".*[\\t~!@#$%^&*()_+`\\={}|\\[\\]:\\\\\";'<>?,./].*";
	public static final String REGEX_DEVICEIDS = "[0-9,]+";
	public static final String REGEX_DEVICETYPE = "[0-9]{1,8}";

	/*
	 * ad
	 */
	public static final int MIN_ADID = -1;
	public static final int MAX_ADID = Integer.MAX_VALUE;

	public static final int MIN_ADNAME = 1;
	public static final int MAX_ADNAME = 25;

	/*
	 * ad_schedule
	 */
	public static final int MIN_SCHEDULEID = 0;
	public static final int MAX_SCHEDULEID = Integer.MAX_VALUE;

	public static final int MIN_SCHEDULENAME = 0;
	public static final int MAX_SCHEDULENAME = 25;

	public static final int MIN_RETRYCNT = 0;
	public static final int MAX_RETRYCNT = 10;

	public static final String REGEX_SCHEDULETYPE = "SCH0000[1-5]";
	public static final String REGEX_CONTAINERIDS = "[0-9,]+";

	/*
	 * ctrl_history
	 */
	public static final int MIN_CTRLHISTORYID = 0;
	public static final int MAX_CTRLHISTORYID = Integer.MAX_VALUE;

	public static final int MIN_VERSION = 1;
	public static final int MAX_VERSION = 8;

	public static final int MIN_RESULT = 0;
	public static final int MAX_RESULT = 255;

	public static final String REGEX_CTRLTYPE = "CT0000[0-1][0-9]";
	public static final String REGEX_DELIVERY_STATE = "[NSC]";
	public static final String REGEX_ADSCHEDULEIDS = "[0-9,]+";

	/*
	 * version
	 */
	public static final int MIN_VERSIONID = 0;
	public static final int MAX_VERSIONID = Integer.MAX_VALUE;

	/*
	 * ctrl_device
	 */
	public static final String REGEX_OPCODES = "[OP0-9,]+";

	/*
	 * ibm
	 * 
	 * historyWall
	 */
	public static final int MIN_HISTORYWALLID = 0;
	public static final int MAX_HISTORYWALLID = Integer.MAX_VALUE;

	public static final int MAX_CATEGORY = 1;

	public static final int MIN_FILLALPHA = 0;
	public static final int MAX_FILLALPHA = 100;

	public static final String REGEX_COLOR = "#[0-9a-fA-F]{6}";
	public static final String REGEX_YEAR = "[1-2][0-9][0-9][0-9]";
	public static final String REGEX_MONTH = "[0-1][0-9]";

	/*
	 * era
	 */
	public static final int MIN_ERAID = 0;
	public static final int MAX_ERAID = Integer.MAX_VALUE;

	/*
	 * opsLayout
	 */
	public static final int MIN_OPSLAYOUTID = 0;
	public static final int MAX_OPSLAYOUTID = Integer.MAX_VALUE;

	/*
	 * opsTemplate
	 */
	public static final int MIN_OPSTEMPLATEID = 0;
	public static final int MAX_OPSTEMPLATEID = Integer.MAX_VALUE;

	public static final int MIN_TEMPLATE_TITLE = 0;
	public static final int MAX_TEMPLATE_TITLE = 255;

	public static final int MIN_IMAGE_URL = 0;
	public static final int MAX_IMAGE_URL = 255;

	/*
	 * opsMaster
	 */
	public static final int MIN_OPSMASTERID = 0;
	public static final int MAX_OPSMASTERID = Integer.MAX_VALUE;

	public static final int MIN_OPSMASTER_TITLE = 0;
	public static final int MAX_OPSMASTER_TITLE = 128;

	public static final int MIN_OPSMASTER_DESC = 0;
	public static final int MAX_OPSMASTER_DESC = 255;

	/*
	 * opsSub
	 */
	public static final int MIN_OPSSUBID = 0;
	public static final int MAX_OPSSUBID = Integer.MAX_VALUE;

	public static final int MIN_OPSSUBTITLE = 0;
	public static final int MAX_OPSSUBTITLE = 128;

	/*
	 * meeting
	 */
	public static final int MIN_MEETINGID = 0;
	public static final int MAX_MEETINGID = Integer.MAX_VALUE;

	public static final int MIN_MEETINGDESC = 1;
	public static final int MAX_MEETINGDESC = 1000;

	/*
	 * demozone
	 */
	public static final int MIN_PANEL_NM = 1;
	public static final int MAX_PANEL_NM = 20;
	
	public static final int MIN_ICON_NM = 1;
	public static final int MAX_ICON_NM = 50;
	
	public static final int MIN_CATEGORY_NM = 1;
	public static final int MAX_CATEGORY_NM = 50;
	
	static {
		/*
		 * NOTE
		 * 
		 * validMeta.put(key, new Object[] { min, max, lengthType, regex,
		 * isMatches});
		 */

		/*
		 * common
		 */
		validMeta.put("desc", new Object[] { MIN_DESC, MAX_DESC, LENGTH.STRING,
				null, null });
		validMeta.put("tag", new Object[] { MIN_TAG, MAX_TAG, LENGTH.STRING,
				null, null });

		/*
		 * business
		 
		validMeta.put(BusinessDto.KEY_BUSINESS_ID, new Object[] {
				MIN_BUSINESSID, MAX_BUSINESSID, LENGTH.INT, null, null });
		validMeta.put(BusinessDto.KEY_BUSINESS_NAME, new Object[] {
				MIN_BUSINESSNAME, MAX_BUSINESSNAME, LENGTH.INT, null, null });
		validMeta.put(BusinessDto.KEY_BUSINESS_DESC, new Object[] { MIN_DESC,
				MAX_DESC, LENGTH.INT, null, null });
		validMeta.put("openTime", new Object[] { null, null, null,
				REGEX_TIME_FORMAT, false });
		validMeta.put("closeTime", new Object[] { null, null, null,
				REGEX_TIME_FORMAT, false });
		validMeta.put("holiday", new Object[] { null, null, null,
				REGEX_HOLIDAY, false });
		validMeta.put("soundVolume", new Object[] { MIN_SOUNDVOLUME,
				MAX_SOUNDVOLUME, LENGTH.INT, null, null });
		validMeta.put("brightness", new Object[] { MIN_BRIGHTNESS,
				MAX_BRIGHTNESS, LENGTH.INT, null, null });
		validMeta.put("minInterval", new Object[] { MIN_MININTERVAL,
				MAX_MININTERVAL, LENGTH.INT, null, null });
				*/

		/*
		 * user
		 */
		validMeta.put("userId", new Object[] { MIN_USERID, MAX_USERID,
				LENGTH.INT, null, null });
		validMeta.put("loginId", new Object[] { MIN_LOGINID, MAX_LOGINID,
				LENGTH.STRING, REGEX_LOGINID, false });
		validMeta.put("passwd", new Object[] { MIN_PASSWD, MAX_PASSWD,
				LENGTH.STRING, REGEX_PASSWD, false });
		validMeta.put("userName", new Object[] { MIN_USERNAME, MAX_USERNAME,
				LENGTH.STRING, REGEX_USERNAME, true });
		validMeta.put("phone", new Object[] { null, null, null, REGEX_PHONE,
				false });
		validMeta.put("email", new Object[] { MIN_EMAIL, MAX_EMAIL,
				LENGTH.STRING, REGEX_EMAIL, false });
		validMeta.put("rightsType", new Object[] { MIN_RIGHTSTYPE,
				MAX_RIGHTSTYPE, LENGTH.STRING, REGEX_RIGHTSTYPE, false });

		/*
		 * contents
		 */
		validMeta.put("contentsId", new Object[] { MIN_CONTENTSID,
				MAX_CONTENTSID, LENGTH.INT, null, null });
		validMeta.put("contentsName", new Object[] { MIN_CONTENTSNAME,
				MAX_CONTENTSNAME, LENGTH.STRING, null, null });
		validMeta.put("contentsDesc", new Object[] { MIN_DESC, MAX_DESC,
				LENGTH.STRING, null, null });
		validMeta.put("contentsType", new Object[] { null, null, null,
				REGEX_CONTENTSTYPE, false });
		validMeta.put("contentsFile", new Object[] { MIN_CONTENTSFILE,
				MAX_CONTENTSFILE, LENGTH.STRING, null, null });
		validMeta.put("contentsTxt", new Object[] { MIN_CONTENTSTXT,
				MAX_CONTENTSTXT, LENGTH.STRING, null, null });
		validMeta.put("contentsTxtMeta", new Object[] { MIN_CONTENTSTXTMETA,
				MAX_CONTENTSTXTMETA, LENGTH.STRING, null, null });
		validMeta.put("checksum", new Object[] { MIN_CHECKSUM, MAX_CHECKSUM,
				LENGTH.STRING, null, null });
		validMeta.put("isPublic", new Object[] { null, null, null, CommonConst.REGEX_FLAG,
				false });
		validMeta.put("orientation", new Object[] { null, null, null,
				REGEX_ORIENTATION, false });

		/*
		 * container
		 */
		validMeta.put("containerId", new Object[] { MIN_CONTAINERID,
				MAX_CONTAINERID, LENGTH.INT, null, null });
		validMeta.put("containerName", new Object[] { MIN_CONTAINERNAME,
				MAX_CONTAINERNAME, LENGTH.STRING, null, null });
		validMeta.put("containerDesc", new Object[] { MIN_CONTAINERDESC,
				MAX_CONTAINERDESC, LENGTH.STRING, null, null });
		validMeta.put("containerExtra", new Object[] { MIN_CONTAINEREXTRA,
				MAX_CONTAINEREXTRA, LENGTH.STRING, null, null });
		validMeta.put("scenario", new Object[] { MIN_SCENARIO, MAX_SCENARIO,
				LENGTH.STRING, null, null });

		/*
		 * device
		 */
		validMeta.put("deviceId", new Object[] { MIN_DEVICEID, MAX_DEVICEID,
				LENGTH.INT, null, null });
		validMeta.put("deviceName", new Object[] { MIN_DEVICENAME,
				MAX_DEVICENAME, LENGTH.STRING, REGEX_DEVICENAME, true });
		validMeta.put("deviceDesc", new Object[] { MIN_DESC, MAX_DESC,
				LENGTH.STRING, null, null });
		validMeta.put("faultDateTime", new Object[] { null, null, null,
				CommonConst.REGEX_DATETIME_FORMAT, false });
		validMeta.put("deviceMeta", new Object[] { MIN_DEVICEMETA,
				MAX_DEVICEMETA, LENGTH.STRING, null, null });
		validMeta.put("deviceType", new Object[] { null, null, null,
				REGEX_DEVICETYPE, false });
		validMeta.put("deviceCnt", new Object[] { MIN_DEVICECNT, MAX_DEVICECNT,
				LENGTH.INT, null, null });
		validMeta.put("deviceIds", new Object[] { null, null, null,
				REGEX_DEVICEIDS, false });

		/*
		 * ad
		 */
		validMeta.put("adId", new Object[] { MIN_ADID, MAX_ADID, LENGTH.INT,
				null, null });
		validMeta.put("adName", new Object[] { MIN_ADNAME, MAX_ADNAME,
				LENGTH.STRING, null, null });
		validMeta.put("adDesc", new Object[] { MIN_DESC, MAX_DESC,
				LENGTH.STRING, null, null });
		validMeta.put("startDateTime", new Object[] { null, null, null,
				CommonConst.REGEX_DATETIME_FORMAT, false });
		validMeta.put("endDateTime", new Object[] { null, null, null,
				CommonConst.REGEX_DATETIME_FORMAT, false });

		/*
		 * ad_schedule
		 */
		validMeta.put("adScheduleId", new Object[] { MIN_SCHEDULEID,
				MAX_SCHEDULEID, LENGTH.INT, null, null });
		validMeta.put("scheduleName", new Object[] { MIN_SCHEDULENAME,
				MAX_SCHEDULENAME, LENGTH.STRING, null, null });
		validMeta.put("scheduleDesc", new Object[] { MIN_DESC, MAX_DESC,
				LENGTH.STRING, null, null });
		validMeta.put("containerIds", new Object[] { null, null, null,
				REGEX_CONTAINERIDS, false });
		validMeta.put("scheduleType", new Object[] { null, null, null,
				REGEX_SCHEDULETYPE, false });
		validMeta.put("isAutoDelivery", new Object[] { null, null, null,
				CommonConst.REGEX_FLAG, false });
		validMeta.put("retryCnt", new Object[] { MIN_RETRYCNT, MAX_RETRYCNT,
				LENGTH.INT, null, null });

		/*
		 * ctrl_history
		 */
		validMeta.put("ctrlHistoryId",
				new Object[] { MIN_CTRLHISTORYID, MAX_CTRLHISTORYID,
						LENGTH.INT, null /* regex */, null /* isMatches */});
		validMeta.put("ctrlType", new Object[] { null /* min */, null /* max */,
				null /* lengthType */, REGEX_CTRLTYPE, false /* isMatches */});
		validMeta.put("version", new Object[] { MIN_VERSION, MAX_VERSION,
				LENGTH.STRING, null /* regex */, null /* isMatches */});
		validMeta.put("result", new Object[] { MIN_RESULT, MAX_RESULT,
				LENGTH.STRING, null /* regex */, null /* isMatches */});
		validMeta.put("deliveryState", new Object[] { null /* min */,
				null /* max */, null /* lengthType */, REGEX_DELIVERY_STATE,
				false /* isMatches */});

		/*
		 * ctrl device
		 */
		validMeta.put("isUpdateVersion", new Object[] { null, null, null,
				CommonConst.REGEX_FLAG, false });
		validMeta.put("isUpdateSchedule", new Object[] { null, null, null,
				CommonConst.REGEX_FLAG, false });
		validMeta.put("updateContainerAdScheduleIds", new Object[] { null,
				null, null, REGEX_ADSCHEDULEIDS, false });
		validMeta.put("isFault", new Object[] { null, null, null, CommonConst.REGEX_FLAG,
				false });
		validMeta.put("isMatchSchedule", new Object[] { null, null, null,
				CommonConst.REGEX_FLAG, false });
		validMeta.put("deviceModes", new Object[] { null, null, null,
				REGEX_OPCODES, false });

		/*
		 * version
		 */
		validMeta.put("versionId", new Object[] { MIN_VERSIONID, MAX_VERSIONID,
				LENGTH.INT, null, null });
		validMeta.put("versionDesc", new Object[] { MIN_DESC, MAX_DESC,
				LENGTH.STRING, null, null });
		validMeta.put("releaseDateTime", new Object[] { null, null, null,
				CommonConst.REGEX_DATETIME_FORMAT, false });

		/*
		 * ibm
		 * 
		 * history
		 */
		validMeta.put("historyWallId", new Object[] { MIN_HISTORYWALLID,
				MAX_HISTORYWALLID, LENGTH.INT, null, null });
		validMeta.put("year", new Object[] { null, null, null, REGEX_YEAR,
				false });
		validMeta.put("month", new Object[] { null, null, null, REGEX_MONTH,
				false });
		validMeta.put("depth", new Object[] { CommonConst.MIN_DEFAULT_INT, CommonConst.MAX_DEFAULT_INT,
				LENGTH.INT, null, null });
		validMeta.put("isGlobal", new Object[] { null, null, null, CommonConst.REGEX_FLAG,
				false });
		validMeta.put("lineColor", new Object[] { null, null, null,
				REGEX_COLOR, false });
		validMeta.put("fillColor", new Object[] { null, null, null,
				REGEX_COLOR, false });
		validMeta.put("fillAlpha", new Object[] { MIN_FILLALPHA, MAX_FILLALPHA,
				LENGTH.INT, null, null });
		validMeta.put("important", new Object[] { CommonConst.MIN_DEFAULT_INT,
				CommonConst.MAX_DEFAULT_INT, LENGTH.INT, null, null });
		validMeta.put("category", new Object[] { CommonConst.MIN_DEFAULT_INT, MAX_CATEGORY,
				LENGTH.INT, null, null });
		validMeta.put("thumbUrl", new Object[] { MIN_URL, MAX_URL,
				LENGTH.STRING, null, null });

		/*
		 * era
		 */
		validMeta.put("eraId", new Object[] { MIN_ERAID, MAX_ERAID, LENGTH.INT,
				null, null });

		/*
		 * opsLayout
		 */
		validMeta.put("opsLayoutId", new Object[] { MIN_OPSLAYOUTID,
				MAX_OPSLAYOUTID, LENGTH.INT, null, null });

		/*
		 * opsTemplate
		 */
		validMeta.put("opsTemplateId", new Object[] { MIN_OPSTEMPLATEID,
				MAX_OPSTEMPLATEID, LENGTH.INT, null, null });
		validMeta.put("templateTitle", new Object[] { MIN_TEMPLATE_TITLE,
				MAX_TEMPLATE_TITLE, LENGTH.STRING, null, null });
		validMeta.put("imageUrl", new Object[] { MIN_IMAGE_URL, MAX_IMAGE_URL,
				LENGTH.STRING, null, null });

		/*
		 * opsMaster
		 */
		validMeta.put("opsMasterId", new Object[] { MIN_OPSMASTERID,
				MAX_OPSMASTERID, LENGTH.INT, null, null });
		validMeta.put("title", new Object[] { MIN_OPSMASTER_TITLE,
				MAX_OPSMASTER_TITLE, LENGTH.STRING, null, null });
		validMeta.put("description", new Object[] { MIN_OPSMASTER_DESC,
				MAX_OPSMASTER_DESC, LENGTH.STRING, null, null });
		validMeta.put("isApply", new Object[] { null, null, null, CommonConst.REGEX_FLAG,
				false });

		/*
		 * opsSub
		 */
		validMeta.put("opsSubId", new Object[] { MIN_OPSSUBID, MAX_OPSSUBID,
				LENGTH.INT, null, null });
		validMeta.put("opsSubTitle", new Object[] { MIN_OPSSUBTITLE,
				MAX_OPSSUBTITLE, LENGTH.STRING, null, null });

		/*
		 * meeting
		 */
		validMeta.put("meetingId", new Object[] { MIN_MEETINGID, MAX_MEETINGID,
				LENGTH.INT, null, null });
		validMeta.put("meetingDesc", new Object[] { MIN_MEETINGDESC,
				MAX_MEETINGDESC, LENGTH.STRING, null, null });
		
		/*
		 * demoZone
		 */
		//validMeta.put("categoryId", new Object[] { MIN_MEETINGID, MAX_MEETINGID,
		//		LENGTH.INT, null, null });
		validMeta.put("panelNm", new Object[] { MIN_PANEL_NM,
				MAX_PANEL_NM, LENGTH.STRING, null, null });
		validMeta.put("iconNm", new Object[] { MIN_ICON_NM,
				MAX_ICON_NM, LENGTH.STRING, null, null });
		validMeta.put("categoryNm", new Object[] { MIN_CATEGORY_NM,
				MAX_CATEGORY_NM, LENGTH.STRING, null, null });		
		
	}
}
