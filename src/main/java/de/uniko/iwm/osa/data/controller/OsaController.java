package de.uniko.iwm.osa.data.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.uniko.iwm.osa.data.form.OsaDbPages;
import de.uniko.iwm.osa.data.form.OsaDbQuestitems;
import de.uniko.iwm.osa.data.form.UploadItem;
import de.uniko.iwm.osa.data.service.OsaBdQuestitemsService;
import de.uniko.iwm.osa.data.service.OsaDbPagesService;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;
import de.uniko.iwm.osa.qtiinterpreter.Compile;

@Controller
@RequestMapping("/index")
public class OsaController {

	// @Autowired
	// private ContactService contactService;

	@Autowired
	private OsaBdQuestitemsService qiService;

	@Autowired
	private OsaDbQuestsService qService;

	@Autowired
	private OsaDbPagesService pService;

	// @Value("classpath:imageTemplate.xslt") InputStream imageTemplate;
	// @Value("classpath:SampleQTI2_1.zip") InputStream qtiStream;
	// @Value("classpath:Interessen_18.12.2012.zip") InputStream qtiStream;

	final String image_base = "new_images";

	@RequestMapping(method = RequestMethod.GET)
	public String listContacts(Model model) {

		// map.put("contact", new Contact());
		// map.put("contactList", contactService.listContact());

		// Context initCtx;
		// try {
		// initCtx = new InitialContext();
		//
		// Context envCtx = (Context) initCtx.lookup("java:comp/env");
		//
		// DataSource ds = (DataSource) envCtx.lookup("jdbc/Local");
		// Connection conn = ds.getConnection();
		//
		// Statement stmt = conn.createStatement();
		// ResultSet rs = stmt.executeQuery("select name from pages");
		//
		// int count = 0;
		// while (rs.next()) {
		// count++;
		// System.out.println(count + "\t: " + rs.getString(1));
		// }
		//
		// } catch (NamingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		System.out.println("q Size  : " + qService.listOsaDbQuests().size());
		System.out.println("qi Size : "
				+ qiService.listOsaDbQuestitems().size());
		System.out.println("p Size  : " + pService.listOsaDbPages().size());
		System.out.println("p [3]  : "
				+ pService.getOsaDbPagesById(new Integer(3)));

		OsaDbQuestitems qi = qiService.listOsaDbQuestitems().get(0);
		OsaDbPages p = pService.getOsaDbPagesById(qi.getPagesid()).get(0);

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

			Compile comp = new Compile(qtiInput, image_base, qService);
			comp.run();
		}

		return "osadbform";
	}
}
