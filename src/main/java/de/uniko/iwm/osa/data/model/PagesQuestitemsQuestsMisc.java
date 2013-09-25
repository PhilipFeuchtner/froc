package de.uniko.iwm.osa.data.model;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

public class PagesQuestitemsQuestsMisc {

	static Logger log = Logger.getLogger(PagesQuestitemsQuestsMisc.class.getName());

	// question elements
	
	OsaDbPages page = null;
	OsaDbQuestitems questitem = null;
	OsaDbQuests quest = null;

	// misc

	private int m_itemPerPage = 1;

	// -----------------------------------------------------------

	public PagesQuestitemsQuestsMisc() {
		page = new OsaDbPages();
		questitem = new OsaDbQuestitems();
		quest = new OsaDbQuests();

		setMd5();
	}

	public PagesQuestitemsQuestsMisc(OsaDbPages page, OsaDbQuestitems questitem) {
		this.page = page;
		this.questitem = questitem;
		quest = new OsaDbQuests();

		// setMd5();
	}

	// -----------------------------------------------------------

	private void setMd5() {
		String MD5PREFIX = "MD5PREFIX %d";

		String md5Text = String.format(MD5PREFIX, System.currentTimeMillis());

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();
			messageDigest.update(md5Text.getBytes(Charset.forName("UTF8")));
			String md5key = new String(Hex.encodeHex(messageDigest.digest()));

			page.setMd5key(md5key);

			log.info("md5 [" + md5Text + "] " + md5key);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		}
	}

	// -----------------------------------------------------------

	public int getM_itemPerPage() {
		return m_itemPerPage;
	}

	public void setM_itemPerPage(int m_itemPerPage) {
		this.m_itemPerPage = m_itemPerPage;
	}

	// -----------------------------------------------------------

	public String getP_pid() {
		return page.getPid();
	}

	public void setP_pid(String pid) {
		page.setPid(pid);
	}

	public String getP_md5key() {
		return page.getMd5key();
	}

	public void setP_md5key(String md5key) {
		page.setMd5key(md5key);
	}

	public int getP_display() {
		return page.getDisplay();
	}

	public void setP_display(int display) {
		page.setDisplay(display);
	}

	public int getP_login() {
		return page.getLogin();
	}

	public void setP_login(int login) {
		this.page.setLogin(login);
	}

	public int getP_sessionpos() {
		return page.getSessionpos();
	}

	public void setP_sessionpos(int sessionpos) {
		page.setSessionpos(sessionpos);
	}

	public int getP_back() {
		return page.getBack();
	}

	public void setP_back(int back) {
		page.setBack(back);
	}

	public int getP_forward() {
		return page.getForward();
	}

	public void setP_forward(int forward) {
		page.setForward(forward);
	}

	public String getP_forwardform() {
		return page.getForwardform();
	}

	public void setP_forwardform(String forwardform) {
		page.setForwardform(forwardform);
	}

	public String getP_name() {
		return page.getName();
	}

	public void setP_name(String name) {
		page.setName(name);
	}

	public int getP_closed() {
		return page.getClosed();
	}

	public void setP_closed(int closed) {
		page.setClosed(closed);
	}

	public int getQi_pagesid() {
		return questitem.getPagesid();
	}

	public void setQi_pagesid(int pagesid) {
		questitem.setPagesid(pagesid);
	}

	public int getQi_position() {
		return questitem.getPosition();
	}

	public void setQi_position(int position) {
		this.questitem.setPosition(position);
	}

	public String getQi_questhead() {
		return questitem.getQuesthead();
	}

	public void setQi_questhead(String questhead) {
		questitem.setQuesthead(questhead);
	}

	public String getQi_questsubhead() {
		return questitem.getQuestsubhead();
	}

	public void setQi_questsubhead(String questsubhead) {
		questitem.setQuestsubhead(questsubhead);
	}

	public String getQi_questdesc() {
		return questitem.getQuestdesc();
	}

	public void setQi_questdesc(String questdesc) {
		questitem.setQuestdesc(questdesc);
	}

	public int getQi_questtype() {
		return questitem.getQuesttype();
	}

	public void setQi_questtype(int questtype) {
		questitem.setQuesttype(questtype);
	}

	public String getQi_questparam() {
		return questitem.getQuestparam();
	}

	public void setQi_questparam(String questparam) {
		this.questitem.setQuestparam(questparam);
	}

	public int getQ_questid() {
		return quest.getQuestid();
	}

	public void setQ_questid(int questid) {
		quest.setQuestid(questid);
	}

	public int getQ_position() {
		return quest.getPosition();
	}

	public void setQ_position(int position) {
		quest.setPosition(position);
	}

	public String getQ_shownum() {
		return quest.getShownum();
	}

	public void setQ_shownum(String shownum) {
		this.quest.setShownum(shownum);
	}

	public String getQ_showdesc() {
		return quest.getShowdesc();
	}

	public void setQ_showdesc(String showdesc) {
		this.quest.setShowdesc(showdesc);
	}

	public String getQ_typevalues() {
		return quest.getTypevalues();
	}

	public void setQ_typevalues(String typevalues) {
		quest.setTypevalues(typevalues);
	}

	// --------------------------------------------------------------

	public OsaDbPages getPages() {
		return page;
	}

	public OsaDbQuestitems getQuestitems() {
		return questitem;
	}

	public OsaDbQuests getQuests() {
		return quest;
	}

	// --------------------------------------------------------------

	public PagesQuestitemsQuestsMisc getCopy() {
		return new PagesQuestitemsQuestsMisc(page, questitem);
	}

	public boolean isValidPages() {
		return true;
	}

	public boolean isValidQuestitems() {
		return true;
	}

	public boolean isValidQuests() {
		return true;
	}
}
