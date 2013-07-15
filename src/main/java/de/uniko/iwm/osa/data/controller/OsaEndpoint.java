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
import de.uniko.iwm.osa.questsitemTree.QTree;

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
	int JUMPTOPAGE = 177;
	private String osa_name = "psychosa";
	
	@RequestMapping("/upload")
	public @ResponseBody OsaItem getResponse(@RequestHeader Map<String,Object> headers) throws IOException {

		// OsaItem oi = new OsaItem("Everyone is happy!");
		OsaItem oi = new OsaItem();
		
		for (String key : headers.keySet()) {
			log.info(key + " -> " +  headers.get(key));
		}
		
		log.info("Input: " + inputFile.contentLength());
		
		
		InputStream qtiInput =  inputFile.getInputStream();

		String base = FilenameUtils.concat(OsaFileBase, osa_name);
		
		if (builder.run(qtiInput, base, oi, JUMPTOPAGE, pagesId)) {
			qtree.scanDatabase(MAGIC_START_PAGES, oi);
		}
		
		return oi;
	}
}
