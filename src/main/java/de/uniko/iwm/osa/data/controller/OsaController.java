package de.uniko.iwm.osa.data.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.uniko.iwm.osa.data.model.OsaPage;
import de.uniko.iwm.osa.data.model.UploadItem;
import de.uniko.iwm.osa.qtiinterpreter.Builder;
import de.uniko.iwm.osa.questsitemTree.QTree;
import de.uniko.iwm.osa.utils.OsaConfigExtractor;

@Controller
@RequestMapping("/index")
public class OsaController {

	// @Autowired
	// private DataSource osaConfiguration;

	@Autowired
	Builder builder;

	@Autowired
	private QTree qtree;

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

	@RequestMapping(method = RequestMethod.GET)
	public String contact(Model model) {

		// qtree.toDot();

		OsaConfigExtractor dbce = new OsaConfigExtractor(OsaFileBase,
				CYQUEST_PHP_CONFIG_FILE);
		if (dbce.extract(TESTOSA)) {
			// <!-- <jee:jndi-lookup id="dataSource"
			// jndi-name="java:comp/env/${JeeConnection}"
			// jdbc:mysql//localhost:3306/dbname
			System.out.println("Success: [(" + dbce.getDb_server() + ")("
					+ dbce.getDb_user() + ")(" + dbce.getDb_password() + ")]");
			System.out.println("Jdbc   : [jdbc:mysql//" + dbce.getDb_server()
					+ ":" + MAGIC_DB_PORT + "/" + osa_name + "]");
		} else
			System.out.println("Fail");

		UploadItem it = new UploadItem();
		it.setOsaList(dbce.getOsaNames());
		model.addAttribute(it);

		return "osadbform";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(UploadItem uploadItem, BindingResult result)
			throws IOException {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
			return "osadbform";
		}

		// Some type of file processing...
		System.err.println("-------------------------------------------");
		System.err.println("Test upload: " + uploadItem.getName());
		System.err.println("Test upload: "
				+ uploadItem.getFileData().getOriginalFilename());
		System.err.println("-------------------------------------------");

		if (!uploadItem.getFileData().isEmpty()) {
			InputStream qtiInput = uploadItem.getFileData().getInputStream();

			osaPage = builder.run(qtiInput);
		}

		return "osadbform";
	}
}
