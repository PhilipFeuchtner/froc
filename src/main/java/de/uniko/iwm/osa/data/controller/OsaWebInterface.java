package de.uniko.iwm.osa.data.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import de.uniko.iwm.osa.data.model.OsaPage;
import de.uniko.iwm.osa.data.model.UploadItem;
import de.uniko.iwm.osa.qtiinterpreter.Builder;
import de.uniko.iwm.osa.questsitemTree.QTree;
import de.uniko.iwm.osa.utils.OsaConfigExtractor;

@Controller
@RequestMapping("/index")
public class OsaWebInterface {

	static Logger log = Logger.getLogger(OsaWebInterface.class.getName());

	// @Autowired
	// private DataSource osaConfiguration;

	@Autowired
	Builder builder;

	@Autowired
	private QTree qtree;
	private @Value("${MAGIC_START_PAGES}") int MAGIC_START_PAGES;

	private OsaPage osaPage;

	private String osa_name = "psychosa";

	@Autowired
	private String OsaFileBase;

	final String image_base = "new_images";
	private @Value("${CYQUEST_DBCONFIG}")
	String CYQUEST_PHP_CONFIG_FILE;
	private @Value("${DATABASE_PORT}")
	String MAGIC_DB_PORT;

	private String TESTOSA = "psychosa";

	@Resource(name = "keyword2cyquest")
	private HashMap<String, Integer> questionType2CyquestQuestionType;

	@RequestMapping(method = RequestMethod.GET)
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

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(UploadItem uploadItem, BindingResult result)
			throws IOException {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("osaPage", osaPage);
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
			osaPage = builder.run(qtiInput, base);
		}
		
		qtree.scanDatabase(MAGIC_START_PAGES);
		
		modelAndView.setViewName("osa-status-ok");
		return modelAndView;
	}
}
