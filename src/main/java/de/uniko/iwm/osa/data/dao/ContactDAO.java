package de.uniko.iwm.osa.data.dao;

import java.util.List;

import de.uniko.iwm.osa.data.model.Contact;


public interface ContactDAO {
	
	public void addContact(Contact contact);
	public List<Contact> listContact();
	public void removeContact(Integer id);
}
