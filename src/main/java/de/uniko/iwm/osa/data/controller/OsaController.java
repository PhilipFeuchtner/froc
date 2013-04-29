package de.uniko.iwm.osa.data.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.OsaPage;
import de.uniko.iwm.osa.data.model.UploadItem;
import de.uniko.iwm.osa.data.service.OsaDbQuestitemsService;
import de.uniko.iwm.osa.data.service.OsaDbPagesService;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import de.uniko.iwm.osa.qtiinterpreter.Builder;
import de.uniko.iwm.osa.qtiinterpreter.DbConfigExtractor;
import de.uniko.iwm.osa.questsitemTree.QTree;

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
	private @Value("${CYQUEST_DBCONFIG}") String CYQUEST_PHP_CONFIG_FILE;

	@RequestMapping(method = RequestMethod.GET)
	public String contact(Model model) {

		// qtree.toDot();
		String[] basePathParts  = {OsaFileBase, osa_name, CYQUEST_PHP_CONFIG_FILE};  
		String osaBase = generateBasePath(basePathParts);
		System.out.println("Osa FB: "+ osaBase);
		
		DbConfigExtractor dbce = new DbConfigExtractor();
		if (dbce.extract(osaBase))
			System.out.println("Success: [(" + dbce.getDb_server() + ")(" + dbce.getDb_user() + ")("+ dbce.getDb_password() +")]");
		else
			System.out.println("Fail");
		
		model.addAttribute(new UploadItem());

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
	
	private String generateBasePath(String[] args) {
		String dummy = StringUtils.join(args, "/");
		
		return FilenameUtils.normalize(dummy);
	}
}
