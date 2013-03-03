package de.uniko.iwm.osa.data.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.uniko.iwm.osa.data.dao.ConfigDAOImpl;
import de.uniko.iwm.osa.data.model.Config;
import de.uniko.iwm.osa.data.model.OsaDbPages;
import de.uniko.iwm.osa.data.model.OsaDbQuestitems;
import de.uniko.iwm.osa.data.model.UploadItem;
import de.uniko.iwm.osa.data.service.OsaDbQuestitemsService;
import de.uniko.iwm.osa.data.service.OsaDbPagesService;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import de.uniko.iwm.osa.qtiinterpreter.Builder;
import de.uniko.iwm.osa.questsitemTree.QTree;

@Controller
@RequestMapping("/index")
public class OsaController {

	@Autowired
	private DataSource osaConfiguration;
	
	@Autowired
	Builder builder;
	
	@Autowired
	private QTree qtree;

	final String image_base = "new_images";

	@RequestMapping(method = RequestMethod.GET)
	public String listContacts(Model model) {
		
		qtree.toDot();
		

		model.addAttribute(new UploadItem());

		ConfigDAOImpl cDAO = new ConfigDAOImpl();
		cDAO.setDataSource(osaConfiguration);
		
		/* -------------------------------------- */
		
		System.out.println("Now select and list all configs");
		List<Config> list = cDAO.selectAll();
		for (Config myC : list) {
			System.out.println(myC);
		}

		System.out.println("Now select and list all psychosa");
		list = cDAO.select("psychosa");
		for (Config myC : list) {
			System.out.println(myC);
		}
		
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

			builder.run(qtiInput);
		}

		return "osadbform";
	}
}
