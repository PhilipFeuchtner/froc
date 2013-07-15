package de.uniko.iwm.osa.data.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.uniko.iwm.osa.data.model.OsaItem;
import de.uniko.iwm.osa.qtiinterpreter.Builder;
import de.uniko.iwm.osa.qtiinterpreter.QTree;

@Controller
public class OsaEndpoint {
	static Logger log = Logger.getLogger(OsaEndpoint.class.getName());

	@Value("classpath:/questtype_templates.zip")
	private Resource inputFile;

	String pagesId = "6000";

	@Autowired
	private String OsaFileBase;

	@Autowired
	private QTree qtree;

	@Autowired
	Builder builder;

	private @Value("${MAGIC_START_PAGES}")
	int MAGIC_START_PAGES;
	private String osa_name = "psychosa";

	@RequestMapping("/upload")
	public @ResponseBody
	OsaItem getResponse(@RequestHeader Map<String, Object> headers) {

		// OsaItem oi = new OsaItem("Everyone is happy!");
		OsaItem oi = new OsaItem();

		for (String key : headers.keySet()) {
			log.info(key + " -> " + headers.get(key));
		}

		InputStream qtiInput;
		try {
			qtiInput = inputFile.getInputStream();

			String base = FilenameUtils.concat(OsaFileBase, osa_name);

			int jumpToPage = qtree.scanDatabase(MAGIC_START_PAGES, oi);

			if (builder.run(qtiInput, base, oi, jumpToPage, pagesId)) {
				log.info("Upfdated: " + inputFile.getFilename());
			} else {
				String text = "Update failed.";
				log.error(text);
				oi.addErrorEntry(text);
			}
		} catch (IOException e) {
			oi.addErrorEntry(e.getMessage());
			e.printStackTrace();
		}
		
		return oi;
	}
}
