package com.echo.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.echo.framework.type.CommonConst;

public class FtpUtil {
	private static Logger log = LoggerFactory.getLogger(FtpUtil.class);

	private static String isUpload = CommonConst.FLAG_Y;
	private static String server;
	private static int port;
	private static String username;
	private static String password;
	private static String prefix;

	private static String cdn_endpoint = "http://s34dy61z6n.c-cdn.tcloudbiz.com";

	static {
		try {
			isUpload = PropsUtil.getValue("ftp.isUpload");
			server = PropsUtil.getValue("ftp.server");
			port = PropsUtil.getIntValue("ftp.port");
			username = PropsUtil.getValue("ftp.username");
			password = PropsUtil.getValue("ftp.password");
			prefix = PropsUtil.getValue("ftp.path.prefix");

			cdn_endpoint = PropsUtil.getValue("ftp.cdn.end.point");
		}
		catch (Exception e) {
			log.error("e={}", e.getMessage(), e);
		}

		log.info("server={}:{}, username={}, password={}, cdnEndPoint={}",
				new Object[] { server, port, username, password, cdn_endpoint });
	}

	public static boolean uploadFile(String srcFile, String dstFile) {
		if (CommonConst.FLAG_N.equals(isUpload) == true) {
			log.info("skip upload, isUpload={}, src={}, dst={}", new Object[] {
					isUpload, srcFile, dstFile });
			return true;
		}

		if (StringUtils.isEmpty(prefix) == false) {
			if (prefix.endsWith("/") == true) {
				dstFile = prefix + dstFile;
			}
			else {
				dstFile = prefix + "/" + dstFile;
			}
		}

		boolean isSucc = false;
		final FTPClient ftp = new FTPClient();
		ftp.setListHiddenFiles(false);

		// suppress login details
		ftp.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out), true));

		try {
			/*
			 * connect
			 */
			ftp.connect(server, port);
			log.debug("connected to {}:{}", server, port);

			// After connection attempt, you should check the reply code to
			// verify
			// success.
			if (FTPReply.isPositiveCompletion(ftp.getReplyCode()) == false) {
				log.error("server refused connection.");
				return isSucc;
			}

			/*
			 * login
			 */
			if (ftp.login(username, password) == false) {
				log.error("server refused connection.");
				ftp.logout();
				return isSucc;
			}
			else {
				log.debug("remote system is " + ftp.getSystemType());
			}

			/*
			 * set env
			 */
			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			// Use passive mode as default because most of us are
			// behind firewalls these days.
			// ftp.enterLocalActiveMode();
			ftp.enterLocalPassiveMode();
			ftp.setUseEPSVwithIPv4(false);

			/*
			 * mkdir
			 */
			String[] dirs = dstFile.split("[/]");
			StringBuffer dir = new StringBuffer();
			for (int i = 0; i < (dirs.length - 1); i++) {
				dir.append(dirs[i]).append("/");

				FTPFile[] ftpFile = ftp.listDirectories(dir.toString());
				if ((ftpFile == null) || (ftpFile.length == 0)) {
					ftp.mkd(dir.toString());
				}
			}

			/*
			 * upload
			 */
			InputStream input = new FileInputStream(srcFile);
			ftp.storeFile(dstFile, input);
			input.close();

			/*
			 * noop
			 */
			ftp.noop(); // check that control connection is working OK

			/*
			 * logout
			 */
			ftp.logout();

			isSucc = true;
		}
		catch (FTPConnectionClosedException e) {
			log.error("server closed connection, e={}", e.getMessage(), e);
		}
		catch (Exception e) {
			log.error("internal server error, e={}", e.getMessage(), e);
		}
		finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				}
				catch (IOException f) {
					// do nothing
				}
			}
		}

		return isSucc;
	}

	public static boolean uploadFile(String srcFile, String dstFile,
			int retryCnt) {
		for (int i = 0; i < retryCnt; i++) {
			if (uploadFile(srcFile, dstFile) == true) {
				return true;
			}

			log.error("retryCnt={}, srcFile={}, dstFile={}", new Object[] {
					retryCnt, srcFile, dstFile });

			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				log.error("{}", e.getMessage(), e);
			}
		}

		return false;
	}

	public static String getFtpUrl(String dstFile) {
		if (StringUtils.isEmpty(prefix) == false) {
			if (prefix.endsWith("/") == true) {
				dstFile = prefix + dstFile;
			}
			else {
				dstFile = prefix + "/" + dstFile;
			}
		}

		return "http://" + server + "/" + dstFile;
	}

	public static String getCdnUrl(String dstFile) {
		if (StringUtils.isEmpty(prefix) == false) {
			if (prefix.endsWith("/") == true) {
				dstFile = prefix + dstFile;
			}
			else {
				dstFile = prefix + "/" + dstFile;
			}
		}

		return cdn_endpoint + "/" + dstFile;
	}
}
