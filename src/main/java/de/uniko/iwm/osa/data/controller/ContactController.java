package de.uniko.iwm.osa.data.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.uniko.iwm.osa.data.model.Contact;
import de.uniko.iwm.osa.data.service.ContactService;
import de.uniko.iwm.osa.data.service.OsaBdQuestitemsService;
import de.uniko.iwm.osa.data.service.OsaDbQuestsService;

@Controller
public class ContactController {

	@Autowired
	private ContactService contactService;

	@Autowired
	private OsaBdQuestitemsService qiService;
	
	@Autowired
	private OsaDbQuestsService qService;
	
	@RequestMapping("/index-1")
	public String listContacts(Map<String, Object> map) {

		map.put("contact", new Contact());
		map.put("contactList", contactService.listContact());
		
		System.out.println("q Size  : " + qService.listOsaDbQuests().size());
		System.out.println("qi Size : " + qiService.listOsaDbQuestitems().size());
		
		return "contact";
	}

	@RequestMapping(value = "/add-1", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("contact")
	Contact contact, BindingResult result) {

		contactService.addContact(contact);

		return "redirect:/index";
	}

	@RequestMapping("/delete-1/{contactId}")
	public String deleteContact(@PathVariable("contactId")
	Integer contactId) {

		contactService.removeContact(contactId);

		return "redirect:/index";
	}
}
