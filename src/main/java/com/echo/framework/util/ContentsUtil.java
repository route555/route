package com.echo.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ContentsUtil {
	private static Logger log = LoggerFactory.getLogger(ContentsUtil.class);
	private static Map<String, String> cmdMap = new HashMap<String, String>();

	public String getOutputValue(Process process) {
		StringBuffer out = new StringBuffer();
		StringBuffer err = new StringBuffer();

		InputStreamReader isr = null;
		InputStreamReader esr = null;

		try {
			isr = new InputStreamReader(process.getInputStream());

			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				out.append(line).append("\n");
			}

			esr = new InputStreamReader(process.getErrorStream());
			br = new BufferedReader(esr);
			while ((line = br.readLine()) != null) {
				err.append(line).append("\n");
			}

			if (err.length() > 0) {
				log.error("{}", err);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (isr != null) {
				try {
					isr.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (esr != null) {
				try {
					esr.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return out.toString();
	}

	private String getCmdRealPath(String cmd) throws URISyntaxException {
		String realPath = null;

		if ((realPath = cmdMap.get(cmd)) == null) {
			realPath = Paths.get(
					getClass().getClassLoader().getResource(cmd).toURI())
					.toString();

			/*
			 * set permission 0755
			 */
			new File(realPath).setExecutable(true, false);

			cmdMap.put(cmd, realPath);
		}

		return realPath;
	}

	public Map<String, String> getMovieMeta(String file, String thumbBasename)
			throws Exception {
		Map<String, String> metaMap = new HashMap<String, String>();

		Runtime runtime = Runtime.getRuntime();
		String out = null;

		/*
		 * movInfo
		 */
		String cmdMovInfo = getCmdRealPath(PropsUtil.getValue("script.movInfo",
				"script.movInfo4win"))
				+ " "
				+ PropsUtil.getValue("script.movInfo.params").replaceAll(
						"%FILE%", Paths.get(file).toString());
		Process process = runtime.exec(cmdMovInfo);
		process.waitFor();

		out = getOutputValue(process);

		log.debug("{}, {}", cmdMovInfo, out);

		String[] tmp = out.split(",");
		metaMap.put("duration", tmp[0].trim());
		metaMap.put("bitrate", tmp[1].trim());
		metaMap.put("videoResolution", tmp[2].trim());
		metaMap.put("videoRate", tmp[3].trim());
		metaMap.put("fps", tmp[4].trim());
		metaMap.put("audio", tmp[5].trim());

		/*
		 * makeThumb
		 */
		if (StringUtils.isEmpty(thumbBasename) == false) {
			String thumbOutputPath = FileUtil.getUploadPath("default");
			String thumbExt = PropsUtil.getValue("script.makeThumb.ext");
			String thumbFile = thumbOutputPath + "/" + thumbBasename + thumbExt;

			String cmdMakeThumb = getCmdRealPath(PropsUtil.getValue(
					"script.makeThumb", "script.makeThumb4win"))
					+ " "
					+ PropsUtil.getValue("script.makeThumb.params").replaceAll(
							"%FILE%", Paths.get(file).toString()).replaceAll(
							"%THUMB_FILE%", thumbFile);
			process = runtime.exec(cmdMakeThumb);
			process.waitFor();

			out = getOutputValue(process);

			log.debug("{}, {}", cmdMakeThumb, out);

			metaMap.put("thumbUrl", thumbFile);
			metaMap.put("thumbExt", thumbExt);
		}

		log.debug("movInfo, {}", metaMap);

		return metaMap;
	}
}
