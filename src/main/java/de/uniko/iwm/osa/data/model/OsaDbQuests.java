package de.uniko.iwm.osa.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="quests")
public class OsaDbQuests {
//	--
//	-- Tabellenstruktur für Tabelle `quests`
//	--
//
//	DROP TABLE IF EXISTS `quests`;
//	CREATE TABLE IF NOT EXISTS `quests` (
//	  `id` int(11) NOT NULL auto_increment,
//	  `questid` int(11) NOT NULL,
//	  `position` int(11) NOT NULL,
//	  `shownum` varchar(5) NOT NULL default '0',
//	  `showdesc` text NOT NULL,
//	  `typevalues` text NOT NULL,
//	  PRIMARY KEY  (`id`)
//	) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=345 ;

	@Id
	@Column(name="id")
	@GeneratedValue
	private Integer id;
	
	@Column(name="questid")
	private Integer questid = 0;
	
	@Column(name="position")
	private Integer position = 0;
	
	@Column(name="shownum")
	private String shownum = "Default";
	
	@Column(name="showdesc")
	private String showdesc = "Default";
	
	@Column(name="typevalues")
	private String typevalues = "Default";
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestid() {
		return questid;
	}

	public void setQuestid(Integer questid) {
		this.questid = questid;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getShownum() {
		return shownum;
	}

	public void setShownum(String shownum) {
		this.shownum = shownum;
	}

	public String getShowdesc() {
		return showdesc;
	}

	public void setShowdesc(String showdesc) {
		this.showdesc = showdesc;
	}

	public String getTypevalues() {
		return typevalues;
	}

	public void setTypevalues(String typevalues) {
		this.typevalues = typevalues;
	}
	
	@Override 
	public String toString() {
		return String.format("Quest: [%3d][%2d][%2d]", id, questid, position);
	}
}
