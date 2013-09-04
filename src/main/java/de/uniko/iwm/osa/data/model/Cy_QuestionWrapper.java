package de.uniko.iwm.osa.data.model;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import de.uniko.iwm.osa.qtiinterpreter.Parse;
import de.uniko.iwm.osa.qtiinterpreter.Parse.ItemConigurator;

/**
 * @author user
 * 
 *         wrapper class
 * 
 */
public class Cy_QuestionWrapper {

	static Logger log = Logger.getLogger(Parse.class.getName());

	OsaDbPages page = null;
	OsaDbQuestitems questsitem = null;

	List<OsaDbQuests> questList = new ArrayList<OsaDbQuests>();

	ItemConigurator ic = null;

	//

	private String MD5PREFIX = "MD5PREFIX %d";

	public Cy_QuestionWrapper(ItemConigurator ic, String pagesId) {
		this.ic = ic;

		page = new OsaDbPages();
		questsitem = new OsaDbQuestitems();

		initPage(ic, page);
		initQuestitems(ic, questsitem);
		
		page.setPid(pagesId);
	}

	public OsaDbPages getPage() {
		return page;
	}

	public OsaDbQuestitems getOsaDbQuestitems() {
		return questsitem;
	}

	public List<OsaDbQuests> getOsaDbQuest() {
		return questList;
	}

	// ----------------------------------------------------------------------

	public void addQuest(OsaDbQuests q) {
		questList.add(q);
	}
	
	public void setCyQuesttype(int t) {
		questsitem.setQuesttype(t);
	}
	
	public void setPageTitle(String title) {
		page.setName(title);
	}

	// ----------------------------------------------------------------------

	public boolean initPage(ItemConigurator ic, OsaDbPages page) {

		// md5 --------------------------------------------------

		String md5Text = String.format(MD5PREFIX, System.currentTimeMillis());

		try {
			MessageDigest messageDigest;

			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();
			messageDigest.update(md5Text.getBytes(Charset.forName("UTF8")));
			final String result = new String(Hex.encodeHex(messageDigest
					.digest()));
			log.info("md5 [" + md5Text + "] " + result);

			// --------------------------------------------------

			page.setMd5key(result);

		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
			
			return false;
		}

		// ------------------------------------------------------

		return true;
	}

	public boolean initQuestitems(ItemConigurator ic, OsaDbQuestitems quest) {

		//
		// title
		//

		quest.setQuesthead(ic.queryQuestTitle());

		//
		// set rubricBlock
		//

		quest.setQuestdesc(ic.queryQuestDescription());

		return true;
	}
}
