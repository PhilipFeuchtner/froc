package de.uniko.iwm.osa.data.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import de.uniko.iwm.osa.data.model.Cy_PageItem;
import de.uniko.iwm.osa.data.model.OsaItem;
import de.uniko.iwm.osa.data.model.UploadItem;
import de.uniko.iwm.osa.qtiinterpreter.Builder;
import de.uniko.iwm.osa.qtiinterpreter.Parse;
import de.uniko.iwm.osa.qtiinterpreter.QTree;
import de.uniko.iwm.osa.utils.OsaConfigExtractor;
import de.uniko.iwm.osa.utils.UnZip;

@Controller
public class OsaWebInterface {

	static Logger log = Logger.getLogger(OsaWebInterface.class.getName());

	// @Autowired
	// private DataSource osaConfiguration;
	@Value("classpath:/questtype_templates.zip")
	private Resource inputFile;

	@Autowired
	Builder builder;

	@Autowired
	private QTree qtree;

	private @Value("${MAGIC_START_PAGES}")
	int MAGIC_START_PAGES;
	int JUMPTOPAGE = 177;
	@Value("${IMSMANIFEST}")
	String IMSMANIFEST;

	private OsaItem oi;

	private String osa_name = "psychosa";

	@Autowired
	private String OsaFileBase;

	final String image_base = "new_images";
	private @Value("${CYQUEST_DBCONFIG}")
	String CYQUEST_PHP_CONFIG_FILE;
	private @Value("${DATABASE_PORT}")
	String MAGIC_DB_PORT;

	private String TESTOSA = "psychosa";

	@Autowired
	// ("keyword2cyquest")
	private HashMap<String, Integer> keyword2cyquest;

	@Value("${QTI_MEDIAFOLDER}")
	String QTI_MEDIAFOLDER;
	@Value("${CYQUEST_MEDIAFOLDER}")
	String CYQUEST_MEDIAFOLDER;
	
	//
	// froc 
	// header values
	//
	// Parameter beim Aufruf des Froc:
	//	1. Ein Zip per QTI / bzw. Aufruf - Link absolut (im OSA) - "x-path-to-qti"
	//	2. Name des OSAs - "x-name-of-osa"
	//	3. Für jedes Quizz: Start-PID - "x-qti-start-pid"
	//
	
	@Value("${FROC_PATH}")
	String FROC_PATH;
	@Value("${FROC_NAME}")
	String FROC_NAME;
	@Value("${FROC_PID}")
	String FROC_PID;
	
	//

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String contact(Model model) {

		// qtree.toDot();

		OsaConfigExtractor dbce = new OsaConfigExtractor(OsaFileBase,
				CYQUEST_PHP_CONFIG_FILE);
		if (dbce.extract(TESTOSA)) {
			// <!-- <jee:jndi-lookup id="dataSource"
			// jndi-name="java:comp/env/${JeeConnection}"
			// jdbc:mysql//localhost:3306/dbname
			log.info("Success: [(" + dbce.getDb_server() + ")("
					+ dbce.getDb_user() + ")(" + dbce.getDb_password() + ")]");
			log.info("Jdbc   : [jdbc:mysql//" + dbce.getDb_server() + ":"
					+ MAGIC_DB_PORT + "/" + osa_name + "]");
		} else
			log.info("Fail");

		UploadItem it = new UploadItem();
		it.setOsaList(dbce.getOsaNames());
		model.addAttribute(it);

		return "osadbform";
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public ModelAndView create(UploadItem uploadItem, BindingResult result)
			throws IOException {

		oi = new OsaItem();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("osaPage", oi);
		modelAndView.addObject("uploadItem", uploadItem);

		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				log.error("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			return new ModelAndView("osadbform");
		}

		// Some type of file processing...
		log.info("-------------------------------------------");
		log.info("Test upload: " + uploadItem.getName());
		log.info("Test upload: "
				+ uploadItem.getFileData().getOriginalFilename());
		log.info("-------------------------------------------");
		log.info("Osas Name: " + uploadItem.getOsaList().get(0));
		log.info("-------------------------------------------");

		OsaConfigExtractor dbce = new OsaConfigExtractor(OsaFileBase,
				CYQUEST_PHP_CONFIG_FILE);

		modelAndView.addObject("dataBaseConfig", dbce);

		if (dbce.extract(uploadItem.getOsaList().get(0))) {
			dbce.setJdbcString("jdbc:mysql//" + dbce.getDb_server() + ":"
					+ MAGIC_DB_PORT + "/" + osa_name);
		}

		if (!uploadItem.getFileData().isEmpty()) {
			InputStream qtiInput = uploadItem.getFileData().getInputStream();

			String base = FilenameUtils.concat(OsaFileBase, osa_name);

			ParseAndBuild pab = new ParseAndBuild(oi);

			if (pab.prepare(qtiInput, base)
					&& pab.parse(uploadItem.getPagesid()) && pab.build()
					&& pab.cleanUp(MAGIC_START_PAGES)) {
				modelAndView.setViewName("osa-status-ok");
				return modelAndView;
			}
		}

		modelAndView.setViewName("osa-status-fail");
		return modelAndView;

	}

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

			ParseAndBuild pab = new ParseAndBuild(oi);

			if (pab.prepare(qtiInput, base) && pab.parse("7000") && pab.build()
					&& pab.cleanUp(MAGIC_START_PAGES)) {
				// pass
			}
		} catch (IOException e) {
			oi.addErrorEntry(e.getMessage());
			e.printStackTrace();
		}

		return oi;
	}

	public class ParseAndBuild {
		Parse parser;
		// Builder builder;
		OsaItem oi;

		String source;

		boolean hasErrors;
		List<Cy_PageItem> generatedPages;

		public ParseAndBuild(OsaItem oi) {
			this.oi = oi;
			hasErrors = false;
		}

		public boolean prepare(InputStream zipFile, String base) {
			try {
				source = UnZip.unzipFile(zipFile);

				FileUtils
						.copyDirectory(
								new File(FilenameUtils.concat(source,
										QTI_MEDIAFOLDER)),
								new File(FilenameUtils.concat(base,
										CYQUEST_MEDIAFOLDER)));

				return true;
			} catch (IOException e) {
				hasErrors = true;

				oi.addErrorEntry(e.getMessage());
				e.printStackTrace();

				return false;
			}
		}

		public boolean parse(String pagesid) {
			parser = new Parse(source, keyword2cyquest, pagesid, oi);

			try {
				hasErrors = hasErrors || !parser.handleManifest(IMSMANIFEST);
				generatedPages = parser.getGenerated_pages();
			} catch (FileNotFoundException e) {
				oi.addErrorEntry(e.getMessage());
				e.printStackTrace();
				hasErrors = true;
			}

			return !hasErrors;
		}

		public boolean build() {
			// builder = new Builder();
			hasErrors = hasErrors || !builder.build(oi, generatedPages);

			return !hasErrors;
		}

		public boolean cleanUp(int startPage) {
			// QTree qtree = new QTree();

			int jtp = qtree.scanDatabase(startPage, oi);
			hasErrors = hasErrors
					|| !builder.setNavigation(generatedPages, jtp);

			return !hasErrors;
		}
	}
}
