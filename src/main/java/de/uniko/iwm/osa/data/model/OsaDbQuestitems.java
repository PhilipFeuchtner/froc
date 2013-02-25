package de.uniko.iwm.osa.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="questitems")
public class OsaDbQuestitems {
//	--
//	-- Tabellenstruktur für Tabelle `questitems`
//	--
//
//	DROP TABLE IF EXISTS `questitems`;
//	CREATE TABLE IF NOT EXISTS `questitems` (
//	  `id` int(11) unsigned NOT NULL auto_increment,
//	  `pagesid` int(11) NOT NULL,
//	  `position` int(11) NOT NULL,
//	  `questhead` varchar(200) NOT NULL,
//	  `questsubhead` varchar(200) NOT NULL,
//	  `questdesc` text NOT NULL,
//	  `questtype` int(11) NOT NULL,
//	  `questparam` text NOT NULL,
//	  PRIMARY KEY  (`id`)
//	) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=104 ;

	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	
	// @Column(name="pagesid")
	private Integer pagesid;
	
	// @Column(name="position")
	private Integer position;
	
	// @Column(name="questhead")
	private String questhead;
	
	// @Column(name="questsubhead")
	private String questsubhead;
	
	// @Column(name="questdesc")
	private String questdesc;
	
	// @Column(name="questtype")
	private Integer questtype;
	
	// @Column(name="questparam")
	private String questparam;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPagesid() {
		return pagesid;
	}
	public void setPagesid(Integer pagesid) {
		this.pagesid = pagesid;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getQuesthead() {
		return questhead;
	}
	public void setQuesthead(String questhead) {
		this.questhead = questhead;
	}
	public String getQuestsubhead() {
		return questsubhead;
	}
	public void setQuestsubhead(String questsubhead) {
		this.questsubhead = questsubhead;
	}
	public String getQuestdesc() {
		return questdesc;
	}
	public void setQuestdesc(String questdesc) {
		this.questdesc = questdesc;
	}
	public Integer getQuesttype() {
		return questtype;
	}
	public void setQuesttype(Integer questtype) {
		this.questtype = questtype;
	}
	public String getQuestparam() {
		return questparam;
	}
	public void setQuestparam(String questparam) {
		this.questparam = questparam;
	}
	
	
}
