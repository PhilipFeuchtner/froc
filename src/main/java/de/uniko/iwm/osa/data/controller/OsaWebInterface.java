package de.uniko.iwm.osa.data.controller;

import java.io.File;
import java.io.FileInputStream;
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
import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaItem;
import de.uniko.iwm.osa.data.model.UploadItem;
import de.uniko.iwm.osa.data.service.OsaDbPagesService;
import de.uniko.iwm.osa.data.service.QtiBuilderServive;
import de.uniko.iwm.osa.data.service.QtiTreeService;
import de.uniko.iwm.osa.qtiinterpreter.Parse;
import de.uniko.iwm.osa.utils.OsaConfigExtractor;
import de.uniko.iwm.osa.utils.UnZip;

@Controller
public class OsaWebInterface {

	static Logger log = Logger.getLogger(OsaWebInterface.class.getName());

	// @Autowired
	// private DataSource osaConfiguration;
	// @Value("classpath:/questtype_templates.zip")
	// private Resource inputFile;

	/**
	 * Builder-Component
	 */
	@Autowired
	QtiBuilderServive builder;

	/**
	 * QTree find & removes unused questions
	 */
	@Autowired
	private QtiTreeService qtree;

	/**
	 * Pagesservice stores pages in ds
	 */
	@Autowired
	OsaDbPagesService pagesService;

	// private @Value("${MAGIC_START_PAGES}")
	// int MAGIC_START_PAGES;
	// int JUMPTOPAGE = 177;
	@Value("${IMSMANIFEST}")
	String IMSMANIFEST;

	private OsaItem oi;

	/**
	 * 
	 */
	@Autowired
	private String OsaFileBase;

	private @Value("${CYQUEST_DBCONFIG}")
	String CYQUEST_PHP_CONFIG_FILE;
	private @Value("${DATABASE_PORT}")
	String MAGIC_DB_PORT;

	/**
	 * 
	 */
	@Autowired
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
	// 1. Ein Zip per QTI / bzw. Aufruf - Link absolut (im OSA) -
	// "x-path-to-qti"
	// 2. Name des OSAs - "x-name-of-osa"
	// 3. Für jedes Quizz: Start-PID - "x-qti-start-pid"
	//

	@Value("${FROC_PATH}")
	String FROC_PATH;
	@Value("${FROC_NAME}")
	String FROC_NAME;
	@Value("${FROC_PID}")
	String FROC_PID;

	//

	/**
	 * debug interface
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String contact(Model model) {

		OsaConfigExtractor dbce = new OsaConfigExtractor(OsaFileBase,
				CYQUEST_PHP_CONFIG_FILE);

		UploadItem it = new UploadItem();
		it.setOsaList(dbce.getOsaNames());
		model.addAttribute(it);

		return "osadbform";
	}

	/**
	 * debug interface
	 * 
	 * @param uploadItem
	 * @param result
	 * @return
	 * @throws IOException
	 */
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

		String osa_name = uploadItem.getOsaList().get(0);

		// Some type of file processing...
		log.info("-------------------------------------------");
		log.info("Test upload: " + uploadItem.getName());
		log.info("Test upload: "
				+ uploadItem.getFileData().getOriginalFilename());
		log.info("-------------------------------------------");
		log.info("Osas Name: " + osa_name);
		log.info("-------------------------------------------");

		OsaConfigExtractor dbce = new OsaConfigExtractor(OsaFileBase,
				CYQUEST_PHP_CONFIG_FILE);

		modelAndView.addObject("dataBaseConfig", dbce);

		if (dbce.extract(uploadItem.getOsaList().get(0))) {
			dbce.setJdbcString("jdbc:mysql//" + dbce.getDb_server() + ":"
					+ MAGIC_DB_PORT + "/" + osa_name);
		}

		if (!uploadItem.getFileData().isEmpty()) {

			/**
			 * select id by pid
			 */
			List<OsaDbPages> pagesByPid = pagesService
					.getOsaDbPagesByPid(uploadItem.getPagesid());
			int startPage = 0;
			
			if (pagesByPid.isEmpty()) {
				oi.addErrorEntry("Missing page: " + uploadItem.getPagesid());

			} else {
				startPage = pagesByPid.get(0).getId();

				InputStream qtiInput = uploadItem.getFileData()
						.getInputStream();

				String base = FilenameUtils.concat(OsaFileBase, osa_name);

				ParseAndBuild pab = new ParseAndBuild(oi, uploadItem.getPagesid());

				if (pab.prepare(qtiInput, base)
						&& pab.parse() && pab.build()
						&& pab.cleanUp(startPage)) {
					modelAndView.setViewName("osa-status-ok");
					return modelAndView;
				}
			}
		}

		modelAndView.setViewName("osa-status-fail");
		return modelAndView;

	}

	/**
	 * main entry point
	 * 
	 * 
	 * @param headers
	 *            requestheader, see froc-path, froc-name, forc-pid, autowired
	 * @return returns an osaitem oi
	 */
	/**
	 * @param headers
	 * @return
	 */
	@RequestMapping("/upload")
	public @ResponseBody
	OsaItem getResponse(@RequestHeader Map<String, Object> headers) {

		OsaItem oi = new OsaItem();

		/*
		 * // // debug //
		 * 
		 * headers.put(FROC_NAME, "psychosa"); headers.put(FROC_PATH,
		 * "/home/user/iwm/osa/questtype_templates.zip"); headers.put(FROC_PID,
		 * "5101");
		 * 
		 * // debug end
		 */

		if (!headers.containsKey(FROC_NAME) || !headers.containsKey(FROC_PATH)
				|| !headers.containsKey(FROC_PID)) {
			oi.addErrorEntry("Missing header-key.");
			return oi;
		}

		/**
		 * select id by pid
		 */
		List<OsaDbPages> pagesByPid = pagesService
				.getOsaDbPagesByPid((String) headers.get(FROC_PID));
		int startPage;
		if (pagesByPid.isEmpty()) {
			oi.addErrorEntry("Missing page: " + headers.get(FROC_PID));
			return oi;
		} else {
			startPage = pagesByPid.get(0).getId();
		}

		try {
			InputStream qtiInput = new FileInputStream(new File(
					(String) headers.get(FROC_PATH)));

			String base = FilenameUtils.concat(OsaFileBase,
					(String) headers.get(FROC_NAME));

			ParseAndBuild pab = new ParseAndBuild(oi, (String) headers.get(FROC_PID));

			/**
			 * do the work:
			 * 
			 * first unzip qti-file second parse qti third update ds fourth set
			 * navigation
			 * 
			 * if anything goes wrong, the following steps will not executed
			 * short-cut logic
			 */
			@SuppressWarnings(value = "unused")
			boolean success = pab.prepare(qtiInput, base)
					&& pab.parse() && pab.build()
					&& pab.cleanUp(startPage);
		} catch (IOException e) {
			oi.addErrorEntry(e.getMessage());
			e.printStackTrace();
		}

		return oi;
	}

	/*
	 * helper class
	 */
	public class ParseAndBuild {
		Parse parser;
		OsaItem oi;

		String source;

		boolean hasErrors;
		List<Cy_PageItem> generatedPages;
		
		String pagesId;

		/**
		 * @param oi
		 *            osaitem as returnvalue
		 */
		public ParseAndBuild(OsaItem oi, String pagesId) {
			this.oi = oi;
			this.pagesId = pagesId;
			
			hasErrors = false;
		}

		/**
		 * prepare first step
		 * 
		 * @param zipFile
		 *            qti-file to parse
		 * @param base
		 *            osa-base, path to osa
		 * @return true/fale: success-value
		 */
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

		/**
		 * parses qti-file, but donot alter the ds
		 * 
		 * @param pagesid
		 *            pid of the first question
		 * @return success/failure
		 */
		public boolean parse() {
			parser = new Parse(source, CYQUEST_MEDIAFOLDER, keyword2cyquest, pagesId, oi);

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

		/**
		 * writes pages, questitem & quest to ds
		 * 
		 * @return success/failure
		 */
		public boolean build() {
			hasErrors = hasErrors || !builder.build(oi, generatedPages);

			return !hasErrors;
		}

		/**
		 * remove unused entries from ds and generate navigation
		 * 
		 * @param startPage
		 * @return
		 */
		public boolean cleanUp(int startPage) {

			int jtp = qtree.scanDatabase(startPage, oi);
			String firstMd5 = qtree.getFirstMd5();

			hasErrors = hasErrors
					|| !builder.setNavigation(generatedPages, jtp, firstMd5, pagesId);

			return !hasErrors;
		}
	}
}
