package stage1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import common.*;
import common.api.*;

/**
 * runnable that sends file info in http request 
 */
public class FileInfoSender implements Runnable
{
	private FileInfo fi;
	private static final ObjectMapper jsonMapper = new ObjectMapper();
	private static final XmlMapper xmlMapper = new XmlMapper();

	public FileInfoSender(FileInfo fi) {
		this.fi = fi;
	}

	@Override
	public void run() {
		try {
			if (Main.mediaTypeArg.equals(HttpUtils.xmlContentType)) {
				HttpUtils.sendPost(Main.serverUrlArg, xmlMapper.writeValueAsString(fi), HttpUtils.xmlContentType);
			} else {
				HttpUtils.sendPost(Main.serverUrlArg, jsonMapper.writeValueAsString(fi), HttpUtils.jsonContentType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
